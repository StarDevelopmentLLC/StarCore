package com.stardevllc.starevents;

import com.stardevllc.starlib.event.bus.IEventBus;
import com.stardevllc.starlib.event.bus.ReflectionEventBus;
import com.stardevllc.starlib.reflection.ReflectionHelper;
import com.stardevllc.starlib.reflection.URLClassLoaderAccess;
import com.stardevllc.starlib.tuple.either.Either;
import com.stardevllc.starlib.tuple.either.ImmutableEither;
import com.stardevllc.starlib.tuple.pair.ImmutablePair;
import com.stardevllc.starlib.values.observable.ObservableBoolean;
import javassist.*;
import javassist.bytecode.*;
import javassist.bytecode.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.event.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * Main class for StarEvents
 */
@SuppressWarnings("SameParameterValue")
public final class StarEvents {
    private StarEvents() {
    }
    
    private static JavaPlugin plugin;
    
    private static File outputDirectory;
    
    private static final ClassPool classPool = new ClassPool(true);
    
    private static final List<BukkitEventListener> succesfulListeners = new ArrayList<>();
    private static final Map<BukkitEventListener, Throwable> failedListeners = new HashMap<>();
    
    private static final Map<Class<? extends Event>, ImmutablePair<Object, Method>> eventsTracked = new HashMap<>();
    
    public static final ObservableBoolean initComplete = new ObservableBoolean();
    
    /**
     * The event bus used for passing Bukkit Events into
     */
    public static final IEventBus BUS = new BukkitEventBus();
    
    /**
     * Registers a listener to the event bus. This must follow the rules of {@link ReflectionEventBus}
     *
     * @param listener The listener to register
     */
    public static void registerListener(Object listener) {
        BUS.subscribe(listener);
    }
    
    /**
     * Registers a listener to the event bus
     *
     * @param eventType The event class
     * @param listener  The listener
     * @param <E>       The event type
     */
    public static <E extends Event> void registerListener(Class<E> eventType, EventListener<E> listener) {
        BUS.subscribe(listener);
    }
    
    /**
     * Unregisters an event listener
     *
     * @param listener The listener to unregister
     */
    public static void unregisterListener(EventListener<?> listener) {
        BUS.unsubscribe(listener);
    }
    
    private static URL getJarUrl(Class<?> clazz) {
        return clazz.getProtectionDomain().getCodeSource().getLocation();
        //String decode = URLDecoder.decode(location.toString(), StandardCharsets.UTF_8);
    }
    
    private static List<URL> getJarUrl(URLClassLoader classLoader, Predicate<URL> predicate) {
        List<URL> urls = new ArrayList<>();
        for (URL url : classLoader.getURLs()) {
            if (predicate.test(url)) {
                urls.add(url);
            }
        }
        
        if (classLoader.getParent() instanceof URLClassLoader urlClassLoader) {
            urls.addAll(getJarUrl(urlClassLoader, predicate));
        }
        
        return urls;
    }
    
    /**
     * Initalizes StarEvents. This can only be called once as long as the plugin is not null
     *
     * @param plugin The plugin that holds StarEvents
     */
    public static void init(JavaPlugin plugin) {
        if (plugin == null) {
            return;
        }
        
        if (StarEvents.plugin != null) {
            plugin.getLogger().severe("StarEvents has already been initialized by " + StarEvents.plugin.getName());
            return;
        }
        
        StarEvents.plugin = plugin;
        
        classPool.appendClassPath(new LoaderClassPath(StarEvents.class.getClassLoader()));
        
        outputDirectory = new File(plugin.getDataFolder(), "codegen");
        if (outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }
        
        URLClassLoader pluginClassLoader = (URLClassLoader) plugin.getClass().getClassLoader();
        URL serverJarFile = getJarUrl(Bukkit.class);
        
        ClassLoader serverLoader = Bukkit.class.getClassLoader();
        try {
            Collection<CtClass> eventClasses = getEventClassesFromJar(new File(serverJarFile.toURI()));
            Either<BukkitEventListener, Status> clReturn = createListener(pluginClassLoader, "StarEventsSpigotEventListener", eventClasses);
            
            if (clReturn.isRightPresent()) {
                plugin.getLogger().severe("Server Event Listener creation returned: " + clReturn.getRight().name());
                return;
            }
            
            BukkitEventListener listener = clReturn.getLeft();
            Status status = addEventListener(listener);
            plugin.getLogger().info("Server Event Listener Status: " + status.name());
            
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (Plugin p : Bukkit.getPluginManager().getPlugins()) {
                        if (p.getName().equalsIgnoreCase(plugin.getName())) {
                            continue;
                        }
                        
                        Status pluginStatus = addFromPlugin(p);
                        plugin.getLogger().info("Plugin Event Listener Creation for " + p.getName() + " returned: " + pluginStatus.name());
                    }
                    initComplete.set(true);
                }
            }.runTaskLater(plugin, 1L);
        } catch (Exception e) {}
    }
    
    /**
     * Creates and adds a generated event listener from a plugin
     *
     * @param p The plugin
     */
    public static Status addFromPlugin(Plugin p) {
        if (p.getName().equalsIgnoreCase(plugin.getName())) {
            return Status.HOLDER_PLUGIN;
        }
        
        if (!(p.getClass().getClassLoader() instanceof URLClassLoader pluginClassLoader)) {
            return Status.NOT_URL_CLASS_LOADER;
        }
        
        List<URL> urls = getJarUrl(pluginClassLoader, url -> url.toString().endsWith(".jar"));
        File jarFile = null;
        try {
            if (urls.size() == 1) {
                jarFile = new File(urls.getFirst().toURI());
            } else {
                urlLoop:
                for (URL url : urls) {
                    File file = new File(url.toURI());
                    try (JarFile jFile = new JarFile(file)) {
                        ZipEntry pluginYmlEntry = jFile.getEntry("plugin.yml");
                        if (pluginYmlEntry == null) {
                            continue;
                        }
                        
                        try (InputStream inputStream = jFile.getInputStream(pluginYmlEntry); Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {
                            String line;
                            while ((line = scanner.nextLine()) != null) {
                                if (!line.startsWith("name:")) {
                                    continue;
                                }
                                
                                String[] split = line.split(":");
                                String pluginName = split[1].trim();
                                if (p.getName().equalsIgnoreCase(pluginName)) {
                                    jarFile = file;
                                    break urlLoop;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable throwable) {}
        
        if (jarFile == null) {
            return Status.JAR_FILE_NULL;
        }
        
        Collection<CtClass> eventClasses = getEventClassesFromJar(jarFile);
        if (eventClasses.isEmpty()) {
            return Status.NO_EVENT_CLASSES;
        }
        
        Either<BukkitEventListener, Status> clReturn = createListener(pluginClassLoader, "StarEvents" + p.getName() + "Listener", eventClasses);
        
        if (clReturn.isRightPresent()) {
            return clReturn.getRight();
        }
        
        if (!clReturn.isLeftPresent()) {
            return Status.NULL;
        }
        
        BukkitEventListener listener = clReturn.getLeft();
        return addEventListener(listener);
    }
    
    /**
     * Creates a listener that is generated dynamically
     *
     * @param classLoader       The classloader where the events are located
     * @param listenerClassName The name of the class that is generated
     * @param eventClasses      The Event classes themselves. These must be normally registerable in the plugin manager
     * @return The created event listener instance
     */
    public static Either<BukkitEventListener, Status> createListener(URLClassLoader classLoader, String listenerClassName, Collection<CtClass> eventClasses) {
        if (classLoader == null || listenerClassName == null || listenerClassName.isBlank() || eventClasses == null || eventClasses.isEmpty()) {
            return ImmutableEither.right(Status.INVALID_PARAMETERS);
        }
        
        try {
            classPool.appendClassPath(new LoaderClassPath(classLoader));
            URLClassLoaderAccess loaderAccess = URLClassLoaderAccess.create(classLoader);
            CtClass eventListenerClass = classPool.get(BukkitEventListener.class.getName());
            CtClass listenerClass = classPool.makeClass(listenerClassName, eventListenerClass);
            
            for (CtClass eventClass : eventClasses) {
                try {
                    String eventName = eventClass.getSimpleName().replace("Event", "");
                    CtMethod eventMethod = CtNewMethod.make(
                            "public void on" + eventName + "(" + eventClass.getName() + " e) { handleEvent(e); }",
                            listenerClass);
                    ConstPool constPool = listenerClass.getClassFile().getConstPool();
                    AnnotationsAttribute attr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
                    Annotation annotation = new Annotation("org.bukkit.event.EventHandler", constPool);
                    annotation.addMemberValue("ignoreCancelled", new BooleanMemberValue(true, constPool));
                    int eventPriorityIndex = constPool.addUtf8Info(Descriptor.of(EventPriority.class.getName()));
                    int monitorIndex = constPool.addUtf8Info(EventPriority.MONITOR.name());
                    annotation.addMemberValue("priority", new EnumMemberValue(eventPriorityIndex, monitorIndex, constPool));
                    attr.addAnnotation(annotation);
                    eventMethod.getMethodInfo().addAttribute(attr);
                    listenerClass.addMethod(eventMethod);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
            
            try {
                String dir = "plugins" + File.separator + plugin.getDataFolder().getName() + File.separator + "codegen";
                listenerClass.writeFile(dir);
                
                loaderAccess.addURL(outputDirectory.toURI().toURL());
                Class<?> loadedClass = classLoader.loadClass(listenerClass.getName());
                return ImmutableEither.left((BukkitEventListener) loadedClass.getConstructor().newInstance());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } catch (Throwable throwable) {}
        
        return ImmutableEither.right(Status.FAIL);
    }
    
    /**
     * Finds and returns all event classes from a Jar File that have a static getHandlerList and no deprecated <br>
     * This is the same rules for registering an event listener to Bukkit
     *
     * @param jarFile The jar file to search
     * @return The event classes in the jar that have a static getHandlerList and not deprecated
     */
    public static Collection<CtClass> getEventClassesFromJar(File jarFile) {
        List<CtClass> eventClasses = new ArrayList<>();
        CtClass eventClass;
        
        try {
            eventClass = classPool.get(Event.class.getName());
        } catch (NotFoundException e) {
            plugin.getLogger().severe("Cannot find Bukkit Event Class");
            return List.of();
        }
        
        if (eventClass == null) {
            plugin.getLogger().severe("Event class is null");
            return List.of();
        }
        
        if (jarFile != null) {
            try (JarFile jar = new JarFile(jarFile)) {
                classPool.appendClassPath(jarFile.toPath().toAbsolutePath().toString());
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String entryName = entry.getName();
                    
                    if (entry.isDirectory() || !entryName.endsWith(".class")) {
                        continue;
                    }
                    
                    String className = entryName.replace('/', '.').substring(0, entryName.length() - ".class".length());
                    
                    CtClass ctClass = classPool.get(className);
                    if (ctClass.subclassOf(eventClass) || ctClass.subtypeOf(eventClass)) {
                        if (ctClass.hasAnnotation(Deprecated.class)) {
                            continue;
                        }
                        
                        CtMethod handlerListMethod = getMethod(ctClass, "getHandlerList");
                        if (handlerListMethod != null) {
                            eventClasses.add(ctClass);
                        }
                    }
                }
            } catch (Throwable throwable) {}
        }
        
        return eventClasses;
    }
    
    private static CtMethod getMethod(CtClass clazz, String name, CtClass... parameters) {
        if (clazz == null || name == null) {
            return null;
        }
        
        try {
            return clazz.getDeclaredMethod(name, parameters);
        } catch (NotFoundException e) {}
        
        try {
            CtClass superclass = clazz.getSuperclass();
            if (superclass != null) {
                return getMethod(superclass, name, parameters);
            }
        } catch (NotFoundException e) {}
        return null;
    }
    
    /**
     * Enum for the status of a listener
     */
    public enum Status {
        /**
         * The success status if a listener was registered successfully
         */
        SUCCESS,
        
        /**
         * The fail status for if a listener failed to register
         */
        FAIL,
        
        HOLDER_PLUGIN,
        NOT_URL_CLASS_LOADER,
        JAR_FILE_NULL,
        INVALID_PARAMETERS,
        NO_EVENT_CLASSES,
        /**
         * The null status if the provided listener was null
         */
        NULL
    }
    
    /**
     * Adds an event listener that listents for {@link Event}'s to then pass to the {@link IEventBus}
     *
     * @param listener The listener to register
     * @return The status of the listener registration
     */
    public static Status addEventListener(BukkitEventListener listener) {
        if (listener == null) {
            return Status.NULL;
        }
        
        try {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
            
            Set<Method> methods = ReflectionHelper.getClassMethods(listener.getClass());
            methods.removeIf(method -> !method.isAnnotationPresent(EventHandler.class) || method.getParameterCount() != 1);
            
            for (Method method : methods) {
                Class<?> paramType = method.getParameterTypes()[0];
                if (Event.class.isAssignableFrom(paramType)) {
                    if (eventsTracked.containsKey(paramType)) {
                        plugin.getLogger().warning("Event " + paramType.getName() + " has duplicate listeners");
                        plugin.getLogger().warning("  - " + listener.getClass().getName());
                        
                        ImmutablePair<Object, Method> info = eventsTracked.get(paramType);
                        plugin.getLogger().warning("  - " + info.getLeft().getClass().getName());
                    } else {
                        eventsTracked.put((Class<? extends Event>) paramType, ImmutablePair.of(listener, method));
                    }
                }
            }
            
            succesfulListeners.add(listener);
            return Status.SUCCESS;
        } catch (Throwable t) {
            failedListeners.put(listener, t);
            return Status.FAIL;
        }
    }
    
    /**
     * Returns the plugin that initalized StarEvents
     *
     * @return The plugin instance
     */
    public static JavaPlugin getPlugin() {
        return plugin;
    }
    
    /**
     * Returns a copy of the successful listeners
     *
     * @return The successful listeners
     */
    public static List<BukkitEventListener> getSuccesfulListeners() {
        return new ArrayList<>(succesfulListeners);
    }
    
    /**
     * Returns a copy of the failed listeners. <br>
     * The key is the listener instance that failed <br>
     * The value is the throwable instance that caused the listener to fail
     *
     * @return A copy of the map of failed listeners
     */
    public static Map<BukkitEventListener, Throwable> getFailedListeners() {
        return new HashMap<>(failedListeners);
    }
    
    /**
     * Returns a copy of the map of the events tracked by StarEvents <br>
     * The Key is the event class <br>
     * The value is a pair with the first value being the listener instance, and the second value being the method of the listener
     *
     * @return A copy of the mapping of events
     */
    public static Map<Class<? extends Event>, ImmutablePair<Object, Method>> getEventsTracked() {
        return new HashMap<>(eventsTracked);
    }
}
package com.stardevllc.minecraft.registry;

import com.stardevllc.starlib.objects.key.Key;
import com.stardevllc.starlib.registry.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Supplier;

public class PluginDeferredRegisterer<V> extends DeferredRegisterer<V> {
    
    public static <V> PluginDeferredRegisterer<V> create(IRegistry<V> registry, JavaPlugin plugin) {
        return new PluginDeferredRegisterer<>(registry, plugin);
    }
    
    protected final JavaPlugin plugin;
    
    protected PluginDeferredRegisterer(IRegistry<V> registry, JavaPlugin plugin) {
        super(registry);
        this.plugin = plugin;
    }
    
    @Override
    public RegistryObject<V> register(Key key, Supplier<V> supplier) {
        if (!(key instanceof PluginKey)) {
            throw new IllegalArgumentException("Key must be an instance of a PluginKey");
        }
        return super.register(key, supplier);
    }
    
    @Override
    protected Key createKey(String key) {
        return new PluginKey(plugin, key);
    }
}

package com.stardevllc.registry;

import com.stardevllc.starlib.objects.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * This represents a key that is owned by a plugin
 */
public final class PluginKey implements Key {
    
    private static final Map<String, JavaPlugin> CACHE = new HashMap<>();
    
    public static PluginKey of(JavaPlugin plugin, String key) {
        return new PluginKey(plugin, key);
    }
    
    private final JavaPlugin plugin;
    private final String key;
    private final String value;
    
    public PluginKey(JavaPlugin plugin, String key) {
        this.plugin = plugin;
        this.key = key;
        this.value = plugin.getName().toLowerCase() + ":" + key;
    }
    
    public PluginKey(String str) {
        String[] split = str.split(":");
        if (split.length != 2) {
            throw new IllegalArgumentException("Invalid Format for Plugin Key, must be {pluginName}:{key}");
        }
        
        JavaPlugin javaPlugin;
        if (!CACHE.containsKey(split[0])) {
            Plugin plugin = null;
            for (Plugin p : Bukkit.getPluginManager().getPlugins()) {
                if (p.getName().equalsIgnoreCase(split[0])) {
                    plugin = p;
                    break;
                }
            }
            
            if (plugin == null) {
                throw new IllegalArgumentException("Plugin " + split[0] + " does not exist");
            }
            
            if (!(plugin instanceof JavaPlugin jp)) {
                throw new IllegalArgumentException("Plugin " + plugin.getName() + " is not a JavaPlugin");
            }
            this.plugin = jp;
            CACHE.put(split[0], this.plugin);
        } else {
            this.plugin = CACHE.get(split[0]);
        }
        
        this.key = split[1];
        this.value = plugin.getName().toLowerCase() + ":" + key;
    }
    
    public JavaPlugin getPlugin() {
        return plugin;
    }
    
    public String getKey() {
        return key;
    }
    
    @Override
    public String toString() {
        return value;
    }
    
    @Override
    public boolean equals(Object object) {
        return value.equals(object.toString());
    }
    
    @Override
    public int hashCode() {
        return value.hashCode();
    }
    
    @Override
    public int compareTo(@NonNull Key o) {
        return value.compareTo(o.toString());
    }
}
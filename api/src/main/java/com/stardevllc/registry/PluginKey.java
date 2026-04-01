package com.stardevllc.registry;

import com.stardevllc.starlib.objects.key.Key;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;

/**
 * This represents a key that is owned by a plugin
 */
public final class PluginKey implements Key {
    
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
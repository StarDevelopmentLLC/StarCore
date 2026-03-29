package com.stardevllc.registry;

import com.stardevllc.starlib.registry.RegistryKey;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This represents a key that is owned by a plugin
 */
public final class PluginKey extends RegistryKey {
    
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
}
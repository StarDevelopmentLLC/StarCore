package com.stardevllc.minecraft.registry;

import com.stardevllc.starlib.objects.key.Key;
import com.stardevllc.starlib.registry.*;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginRegisterer<V> extends Registerer<V> {
    
    public static <V> PluginRegisterer<V> create(IRegistry<V> registry, JavaPlugin plugin) {
        return new PluginRegisterer<>(registry, plugin);
    }
    
    protected final JavaPlugin plugin;
    
    protected PluginRegisterer(IRegistry<V> registry, JavaPlugin plugin) {
        super(registry);
        this.plugin = plugin;
    }
    
    @Override
    public RegistryObject<V> register(Key key, V object) {
        if (!(key instanceof PluginKey)) {
            throw new IllegalArgumentException("Key must be a PluginKey instance");
        }
        
        return super.register(key, object);
    }
    
    @Override
    protected Key createKey(String key) {
        return new PluginKey(plugin, key);
    }
}

package com.stardevllc.starcore.api;

import com.stardevllc.starmclib.MinecraftVersion;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class VersionModule implements Listener {
    protected MinecraftVersion version;
    protected JavaPlugin plugin;
    
    public VersionModule(MinecraftVersion version, JavaPlugin plugin) {
        this.version = version;
        this.plugin = plugin;
        
        if (MinecraftVersion.CURRENT_VERSION.ordinal() >= this.version.ordinal()) {
            init();
            Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
            plugin.getLogger().info("Loaded Version Module for " + version);
        }
    }
    
    public abstract void init();
    
    protected void registerListeners(Listener... listeners) {
        if (listeners != null) {
            for (Listener listener : listeners) {
                Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
            }
        }
    }
}
package com.stardevllc.starcore.v1_18_1.events;

import com.stardevllc.starcore.api.VersionModule;
import com.stardevllc.smcversion.MinecraftVersion;
import org.bukkit.plugin.java.JavaPlugin;

public class Module_1_18_1 extends VersionModule {
    public Module_1_18_1(JavaPlugin plugin) {
        super(MinecraftVersion.v1_18_1, plugin);
    }
    
    @Override
    public void init() {
        registerListeners(new BlockEvents_1_18_1());
    }
}

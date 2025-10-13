package com.stardevllc.starcore.v1_15_2.events;

import com.stardevllc.starcore.api.VersionModule;
import com.stardevllc.smcversion.MinecraftVersion;
import org.bukkit.plugin.java.JavaPlugin;

public class Module_1_15_2 extends VersionModule {
    public Module_1_15_2(JavaPlugin plugin) {
        super(MinecraftVersion.v1_15_2, plugin);
    }
    
    @Override
    public void init() {
        registerListeners(new EntityEvents_1_15_2(), new WorldEvents_1_15_2());
    }
}

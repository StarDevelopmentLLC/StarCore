package com.stardevllc.starcore.v1_16_3.events;

import com.stardevllc.starcore.api.VersionModule;
import com.stardevllc.smcversion.MinecraftVersion;
import org.bukkit.plugin.java.JavaPlugin;

public class Module_1_16_3 extends VersionModule {
    public Module_1_16_3(JavaPlugin plugin) {
        super(MinecraftVersion.v1_16_3, plugin);
    }
    
    @Override
    public void init() {
        registerListeners(new EntityEvents_1_16_3());
    }
}

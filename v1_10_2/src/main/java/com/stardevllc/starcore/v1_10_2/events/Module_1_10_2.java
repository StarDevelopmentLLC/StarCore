package com.stardevllc.starcore.v1_10_2.events;

import com.stardevllc.starcore.api.VersionModule;
import com.stardevllc.starmclib.MinecraftVersion;
import org.bukkit.plugin.java.JavaPlugin;

public class Module_1_10_2 extends VersionModule {
    public Module_1_10_2(JavaPlugin plugin) {
        super(MinecraftVersion.v1_10_2, plugin);
    }
    
    @Override
    public void init() {
        registerListeners(new EntityEvents_1_10_2());
    }
}

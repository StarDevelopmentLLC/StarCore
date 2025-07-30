package com.stardevllc.starcore.v1_12_2.events;

import com.stardevllc.starcore.api.VersionModule;
import com.stardevllc.starmclib.MinecraftVersion;
import org.bukkit.plugin.java.JavaPlugin;

public class Module_1_12_2 extends VersionModule {
    public Module_1_12_2(JavaPlugin plugin) {
        super(MinecraftVersion.v1_12_2, plugin);
    }
    
    @Override
    public void init() {
        registerListeners(new EntityEvents_1_12_2(), new PlayerEvents_1_12_2(), new ServerEvents_1_12_2());
    }
}

package com.stardevllc.starcore.v1_21_3.events;

import com.stardevllc.starcore.api.VersionModule;
import com.stardevllc.starmclib.MinecraftVersion;
import org.bukkit.plugin.java.JavaPlugin;

public class Module_1_21_3 extends VersionModule {
    public Module_1_21_3(JavaPlugin plugin) {
        super(MinecraftVersion.v1_21_3, plugin);
    }
    
    @Override
    public void init() {
        registerListeners(new PlayerEvents_1_21_3());
    }
}

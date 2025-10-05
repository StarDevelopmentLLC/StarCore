package com.stardevllc.starcore.v1_21_6.events;

import com.stardevllc.starcore.api.VersionModule;
import com.stardevllc.starmclib.MinecraftVersion;
import org.bukkit.plugin.java.JavaPlugin;

public class Module_1_21_6 extends VersionModule {
    public Module_1_21_6(JavaPlugin plugin) {
        super(MinecraftVersion.v1_21_6, plugin);
    }
    
    @Override
    public void init() {
        registerListeners(new EntityEvents_1_21_6(), new PlayerEvents_1_21_6());
    }
}

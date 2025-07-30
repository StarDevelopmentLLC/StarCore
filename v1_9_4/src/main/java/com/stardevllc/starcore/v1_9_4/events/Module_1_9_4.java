package com.stardevllc.starcore.v1_9_4.events;

import com.stardevllc.starcore.api.VersionModule;
import com.stardevllc.starmclib.MinecraftVersion;
import org.bukkit.plugin.java.JavaPlugin;

public class Module_1_9_4 extends VersionModule {
    public Module_1_9_4(JavaPlugin plugin) {
        super(MinecraftVersion.v1_9_4, plugin);
    }
    
    @Override
    public void init() {
        registerListeners(new PlayerEvents_1_9_4(), new ServerEvents_1_9_4());
    }
}

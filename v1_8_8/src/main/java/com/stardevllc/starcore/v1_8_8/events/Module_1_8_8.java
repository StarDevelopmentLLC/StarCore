package com.stardevllc.starcore.v1_8_8.events;

import com.stardevllc.starcore.api.VersionModule;
import com.stardevllc.smcversion.MinecraftVersion;
import org.bukkit.plugin.java.JavaPlugin;

public class Module_1_8_8 extends VersionModule {
    public Module_1_8_8(JavaPlugin plugin) {
        super(MinecraftVersion.v1_8_8, plugin);
    }
    
    @Override
    public void init() {
        registerListeners(new EntityEvents_1_8_8(), new PlayerEvents_1_8_8());
    }
}

package com.stardevllc.starcore.v1_13;

import com.stardevllc.starcore.api.VersionModule;
import com.stardevllc.starcore.v1_13.events.*;
import com.stardevllc.smcversion.MinecraftVersion;
import org.bukkit.plugin.java.JavaPlugin;

public class Module_1_13 extends VersionModule {
    public Module_1_13(JavaPlugin plugin) {
        super(MinecraftVersion.v1_13, plugin);
    }
    
    @Override
    public void init() {
        registerListeners(new BlockEvents_1_13(), new EntityEvents_1_13(), new PlayerEvents_1_13());
    }
}

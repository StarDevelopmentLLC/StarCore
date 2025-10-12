package com.stardevllc.starcore.v1_21_1;

import com.stardevllc.starcore.api.VersionModule;
import com.stardevllc.starcore.v1_21_1.events.*;
import com.stardevllc.starmclib.MinecraftVersion;
import org.bukkit.plugin.java.JavaPlugin;

public class Module_1_21_1 extends VersionModule {
    public Module_1_21_1(JavaPlugin plugin) {
        super(MinecraftVersion.v1_21_1, plugin);
    }
    
    @Override
    public void init() {
        registerListeners(new BlockEvents_1_21_1(), new EntityEvents_1_21_1(), new PlayerEvents_1_21_1());
    }
}

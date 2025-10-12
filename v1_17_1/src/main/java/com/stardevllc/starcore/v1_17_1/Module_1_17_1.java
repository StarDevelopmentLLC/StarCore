package com.stardevllc.starcore.v1_17_1;

import com.stardevllc.starcore.api.VersionModule;
import com.stardevllc.starcore.v1_17_1.events.*;
import com.stardevllc.starmclib.MinecraftVersion;
import org.bukkit.plugin.java.JavaPlugin;

public class Module_1_17_1 extends VersionModule {
    public Module_1_17_1(JavaPlugin plugin) {
        super(MinecraftVersion.v1_17_1, plugin);
    }
    
    @Override
    public void init() {
        registerListeners(new BlockEvents_1_17_1(), new InventoryEvents_1_17_1(), new WorldEvents_1_17_1());
    }
}

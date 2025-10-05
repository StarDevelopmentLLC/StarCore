package com.stardevllc.starcore.v1_21_4.events;

import com.stardevllc.starcore.api.VersionModule;
import com.stardevllc.starmclib.MinecraftVersion;
import org.bukkit.plugin.java.JavaPlugin;

public class Module_1_21_4 extends VersionModule {
    public Module_1_21_4(JavaPlugin plugin) {
        super(MinecraftVersion.v1_21_4, plugin);
    }
    
    @Override
    public void init() {
        this.registerListeners(new BlockEvents_1_21_4(), new EntityEvents_1_21_4());
    }
}

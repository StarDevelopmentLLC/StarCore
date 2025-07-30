package com.stardevllc.starcore.v1_16_5.events;

import com.stardevllc.starcore.api.VersionModule;
import com.stardevllc.starmclib.MinecraftVersion;
import org.bukkit.plugin.java.JavaPlugin;

public class Module_1_16_5 extends VersionModule {
    public Module_1_16_5(JavaPlugin plugin) {
        super(MinecraftVersion.v1_16_5, plugin);
    }
    
    @Override
    public void init() {
        registerListeners(new EntityEvents_1_16_5(), new InventoryEvents_1_16_5(), new PlayerEvents_1_16_5());
    }
}

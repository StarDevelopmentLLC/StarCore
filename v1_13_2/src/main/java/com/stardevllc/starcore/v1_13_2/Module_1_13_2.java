package com.stardevllc.starcore.v1_13_2;

import com.stardevllc.starcore.api.VersionModule;
import com.stardevllc.starcore.v1_13_2.events.*;
import com.stardevllc.smcversion.MinecraftVersion;
import org.bukkit.plugin.java.JavaPlugin;

public class Module_1_13_2 extends VersionModule {
    public Module_1_13_2(JavaPlugin plugin) {
        super(MinecraftVersion.v1_13_2, plugin);
    }
    
    @Override
    public void init() {
        registerListeners(new BlockEvents_1_13_2(), new EntityEvents_1_13_2(), new PlayerEvents_1_13_2(), new ServerEvents_1_13_2());
//        ItemBuilders.mapMetaToBuilder(MapMeta.class, MapItemBuilder.class);
    }
}

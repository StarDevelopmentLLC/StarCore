package com.stardevllc.starcore.v1_14_4;

import com.stardevllc.starcore.api.VersionModule;
import com.stardevllc.starcore.api.itembuilder.ItemBuilders;
import com.stardevllc.starcore.v1_14_4.events.*;
import com.stardevllc.starcore.v1_14_4.itembuilder.CrossbowItemBuilder;
import com.stardevllc.starcore.v1_14_4.itembuilder.StewItemBuilder;
import com.stardevllc.starmclib.MinecraftVersion;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Module_1_14_4 extends VersionModule {
    public Module_1_14_4(JavaPlugin plugin) {
        super(MinecraftVersion.v1_14_4, plugin);
    }
    
    @Override
    public void init() {
        registerListeners(new BlockEvents_1_14_4(), new EntityEvents_1_14_4(), new InventoryEvents_1_14_4(), new PlayerEvents_1_14_4(), new RaidEvents_1_14_4(), new WorldEvents_1_14_4());
        ItemBuilders.mapMetaToBuilder(CrossbowMeta.class, CrossbowItemBuilder.class);
        ItemBuilders.mapMetaToBuilder(SuspiciousStewMeta.class, StewItemBuilder.class);
    }
}

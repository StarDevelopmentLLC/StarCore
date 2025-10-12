package com.stardevllc.starcore.v1_16_1;

import com.stardevllc.starcore.api.StarColors;
import com.stardevllc.starcore.api.VersionModule;
import com.stardevllc.starcore.v1_16_1.events.*;
import com.stardevllc.starmclib.MinecraftVersion;
import org.bukkit.plugin.java.JavaPlugin;

public class Module_1_16_1 extends VersionModule {
    public Module_1_16_1(JavaPlugin plugin) {
        super(MinecraftVersion.v1_16_1, plugin);
    }
    
    @Override
    public void init() {
        StarColors.setColorHandler(new ColorHandler_1_16_1());
        registerListeners(new EntityEvents_1_16_1(), new InventoryEvents_1_16_1(), new PlayerEvents_1_16_1());
//        ItemBuilders.mapMetaToBuilder(BookMeta.class, BookItemBuilder.class);
//        ItemBuilders.mapMetaToBuilder(CompassMeta.class, CompassItemBuilder.class);
    }
}

package com.stardevllc.starcore.v1_19_3;

import com.stardevllc.starcore.api.VersionModule;
import com.stardevllc.starcore.api.itembuilder.ItemBuilders;
import com.stardevllc.starcore.v1_19_3.events.InventoryEvents_1_19_3;
import com.stardevllc.starcore.v1_19_3.itembuilder.GoatHornBuilder;
import com.stardevllc.starmclib.MinecraftVersion;
import org.bukkit.inventory.meta.MusicInstrumentMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Module_1_19_3 extends VersionModule {
    public Module_1_19_3(JavaPlugin plugin) {
        super(MinecraftVersion.v1_19_3, plugin);
    }
    
    @Override
    public void init() {
        registerListeners(new InventoryEvents_1_19_3());
        ItemBuilders.mapMetaToBuilder(MusicInstrumentMeta.class, GoatHornBuilder.class);
    }
}

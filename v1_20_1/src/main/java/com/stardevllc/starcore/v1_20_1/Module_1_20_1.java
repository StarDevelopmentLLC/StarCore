package com.stardevllc.starcore.v1_20_1;

import com.stardevllc.starcore.api.VersionModule;
import com.stardevllc.starcore.api.itembuilder.ItemBuilders;
import com.stardevllc.starcore.v1_20_1.events.EntityEvents_1_20_1;
import com.stardevllc.starcore.v1_20_1.itembuilder.ArmorItemBuilder;
import com.stardevllc.starmclib.MinecraftVersion;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Module_1_20_1 extends VersionModule {
    public Module_1_20_1(JavaPlugin plugin) {
        super(MinecraftVersion.v1_20_1, plugin);
    }
    
    @Override
    public void init() {
        registerListeners(new EntityEvents_1_20_1());
        ItemBuilders.mapMetaToBuilder(ArmorMeta.class, ArmorItemBuilder.class);
    }
}

package com.stardevllc.starcore.v1_8_3;

import com.stardevllc.starcore.api.VersionModule;
import com.stardevllc.starcore.api.itembuilder.ItemBuilders;
import com.stardevllc.starcore.v1_8_3.events.BlockEvents_1_8_3;
import com.stardevllc.starmclib.MinecraftVersion;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Module_1_8_3 extends VersionModule {
    public Module_1_8_3(JavaPlugin plugin) {
        super(MinecraftVersion.v1_8_3, plugin);
    }
    
    @Override
    public void init() {
        registerListeners(new BlockEvents_1_8_3());
        ItemBuilders.mapMetaToBuilder(BlockStateMeta.class, BlockStateItemBuilder.class);
    }
}
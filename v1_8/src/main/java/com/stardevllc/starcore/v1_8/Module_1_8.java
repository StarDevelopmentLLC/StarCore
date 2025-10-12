package com.stardevllc.starcore.v1_8;

import com.stardevllc.starcore.api.StarColors;
import com.stardevllc.starcore.api.VersionModule;
import com.stardevllc.starmclib.MinecraftVersion;
import org.bukkit.plugin.java.JavaPlugin;

public class Module_1_8 extends VersionModule {
    public Module_1_8(JavaPlugin plugin) {
        super(MinecraftVersion.v1_8, plugin);
    }
    
    @Override
    public void init() {
//        ItemBuilders.mapMetaToBuilder(BannerMeta.class, BannerItemBuilder.class);
//        ItemBuilders.mapMetaToBuilder(EnchantmentStorageMeta.class, EnchantedBookBuilder.class);
//        ItemBuilders.mapMetaToBuilder(FireworkMeta.class, FireworkItemBuilder.class);
//        ItemBuilders.mapMetaToBuilder(FireworkEffectMeta.class, FireworkStarBuilder.class);
//        ItemBuilders.mapMetaToBuilder(SkullMeta.class, SkullItemBuilder.class);
        StarColors.setColorHandler(new ColorHandler_1_8());
    }
}

package com.stardevllc.minecraft.v1_8;

import com.stardevllc.minecraft.StarColors;
import com.stardevllc.minecraft.VersionModule;
import com.stardevllc.minecraft.MinecraftVersion;
import org.bukkit.plugin.java.JavaPlugin;

public class Module_1_8 extends VersionModule {
    public Module_1_8(JavaPlugin plugin) {
        super(MinecraftVersion.v1_8, plugin);
    }
    
    @Override
    public void init() {
        StarColors.setColorHandler(new ColorHandler_1_8());
    }
}

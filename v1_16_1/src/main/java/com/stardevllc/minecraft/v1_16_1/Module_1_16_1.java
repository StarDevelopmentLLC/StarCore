package com.stardevllc.minecraft.v1_16_1;

import com.stardevllc.minecraft.StarColors;
import com.stardevllc.minecraft.VersionModule;
import com.stardevllc.minecraft.MinecraftVersion;
import org.bukkit.plugin.java.JavaPlugin;

public class Module_1_16_1 extends VersionModule {
    public Module_1_16_1(JavaPlugin plugin) {
        super(MinecraftVersion.v1_16_1, plugin);
    }
    
    @Override
    public void init() {
        StarColors.setColorHandler(new ColorHandler_1_16_1());
    }
}

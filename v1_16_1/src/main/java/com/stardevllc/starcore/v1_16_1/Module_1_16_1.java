package com.stardevllc.starcore.v1_16_1;

import com.stardevllc.StarColors;
import com.stardevllc.VersionModule;
import com.stardevllc.MinecraftVersion;
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

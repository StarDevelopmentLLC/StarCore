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
        StarColors.setColorHandler(new ColorHandler_1_8());
    }
}

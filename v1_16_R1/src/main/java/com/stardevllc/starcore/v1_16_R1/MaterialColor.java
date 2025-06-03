package com.stardevllc.starcore.v1_16_R1;

import com.stardevllc.starcore.api.colors.CustomColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

//Taken from the Wiki for the Material Colors on Bedrock, just implemented into Java Edition
public class MaterialColor extends CustomColor {
    public static final List<MaterialColor> MATERIAL_COLORS = new ArrayList<>();

    public static final MaterialColor QUARTZ = new MaterialColor("quartz", 'h', "#E3D4D1");
    public static final MaterialColor IRON = new MaterialColor("iron", 'i', "#CECACA");
    public static final MaterialColor NETHERITE = new MaterialColor("netherite", 'j', "#443A3B");
    public static final MaterialColor REDSTONE = new MaterialColor("redstone", 'p', "#971607");
    public static final MaterialColor COPPER = new MaterialColor("copper", 'q', "#B4684D");
    public static final MaterialColor GOLD = new MaterialColor("gold", 'p', "#DEB12D");
    public static final MaterialColor EMERALD = new MaterialColor("emerald", 's', "#47A036");
    public static final MaterialColor DIAMOND = new MaterialColor("diamond", 't', "#2CBAA8");
    public static final MaterialColor LAPIS = new MaterialColor("lapis", 'u', "#21497B");
    public static final MaterialColor AMETHYST = new MaterialColor("amethyst", 'v', "#9A5CC6");
    
    public MaterialColor(String material, char code, String hexCode) {
        super((JavaPlugin) Bukkit.getPluginManager().getPlugin("StarCore"));
        this.symbol = '&';
        this.code = code;
        this.permission = "starcore.color.material." + material;
        this.hex = hexCode;
        calculateFields();
        MATERIAL_COLORS.add(this);
    }

    @Override
    public CustomColor symbolCode(String symbolCode) {
        return this;
    }

    @Override
    public CustomColor hexValue(String hex) {
        return this;
    }

    @Override
    public CustomColor spigotColor(net.md_5.bungee.api.ChatColor color) {
        return this;
    }

    @Override
    public CustomColor permission(String permission) {
        return this;
    }
}

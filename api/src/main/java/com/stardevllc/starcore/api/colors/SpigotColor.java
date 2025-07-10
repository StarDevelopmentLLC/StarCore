package com.stardevllc.starcore.api.colors;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

//Default chat colors from spigot, using this wrapper class to provide hex values by default on lower versions
public class SpigotColor extends CustomColor {
    
    public static final List<SpigotColor> SPIGOT_COLORS = new ArrayList<>();
    
    public static final SpigotColor BLACK = new SpigotColor(ChatColor.BLACK, '0', "#000000");
    public static final SpigotColor DARK_BLUE = new SpigotColor(ChatColor.DARK_BLUE, '1', "#0000AA");
    public static final SpigotColor DARK_GREEN = new SpigotColor(ChatColor.DARK_GREEN, '2', "#00AA00");
    public static final SpigotColor DARK_AQUA = new SpigotColor(ChatColor.DARK_AQUA, '3', "#00AAAA");
    public static final SpigotColor DARK_RED = new SpigotColor(ChatColor.DARK_RED, '4', "#AA0000");
    public static final SpigotColor DARK_PURPLE = new SpigotColor(ChatColor.DARK_PURPLE, '5', "#AA00AA");
    public static final SpigotColor GOLD = new SpigotColor(ChatColor.GOLD, '6', "#FFAA00");
    public static final SpigotColor GRAY = new SpigotColor(ChatColor.GRAY, '7', "#AAAAAA");
    public static final SpigotColor DARK_GRAY = new SpigotColor(ChatColor.DARK_GRAY, '8', "#555555");
    public static final SpigotColor BLUE = new SpigotColor(ChatColor.BLUE, '9', "#5555FF");
    public static final SpigotColor GREEN = new SpigotColor(ChatColor.GREEN, 'a', "#55FF55");
    public static final SpigotColor AQUA = new SpigotColor(ChatColor.AQUA, 'b', "#55FFFF");
    public static final SpigotColor RED = new SpigotColor(ChatColor.RED, 'c', "#FF5555");
    public static final SpigotColor LIGHT_PURPLE = new SpigotColor(ChatColor.LIGHT_PURPLE, 'd', "#FF55FF");
    public static final SpigotColor YELLOW = new SpigotColor(ChatColor.YELLOW, 'e', "#FFFF55");
    public static final SpigotColor WHITE = new SpigotColor(ChatColor.WHITE, 'f', "#FFFFFF");
    public static final SpigotColor MAGIC = new SpigotColor(ChatColor.MAGIC, 'k', "");
    public static final SpigotColor BOLD = new SpigotColor(ChatColor.BOLD, 'l', "");
    public static final SpigotColor STRIKETHROUGH = new SpigotColor(ChatColor.STRIKETHROUGH, 'm', "");
    public static final SpigotColor UNDERLINE = new SpigotColor(ChatColor.UNDERLINE, 'n', "");
    public static final SpigotColor ITALIC = new SpigotColor(ChatColor.ITALIC, 'o', "");
    public static final SpigotColor RESET = new SpigotColor(ChatColor.RESET, 'r', "");
    
    public SpigotColor(ChatColor chatColor, char character, String hexCode) {
        super((JavaPlugin) Bukkit.getPluginManager().getPlugin("StarCore"));
        this.symbol = '&';
        this.code = character;
        this.permission = "starcore.color.spigot." + chatColor.name().toLowerCase();
        this.spigotColor = chatColor;
        this.hex = hexCode;
        SPIGOT_COLORS.add(this);
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
    public CustomColor spigotColor(ChatColor color) {
        return this;
    }

    @Override
    public CustomColor permission(String permission) {
        return this;
    }
}

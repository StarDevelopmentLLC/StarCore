package com.stardevllc.starcore.color;

import com.stardevllc.starcore.StarCore;
import org.bukkit.ChatColor;

//Default chat colors from spigot, using this wrapper class to provide hex values by default on lower versions
public class SpigotColor extends CustomColor {

    public static final SpigotColor BLACK = new SpigotColor(ChatColor.BLACK, "#000000");
    public static final SpigotColor DARK_BLUE = new SpigotColor(ChatColor.DARK_BLUE, "#0000AA");
    public static final SpigotColor DARK_GREEN = new SpigotColor(ChatColor.DARK_GREEN, "#00AA00");
    public static final SpigotColor DARK_AQUA = new SpigotColor(ChatColor.DARK_AQUA, "#00AAAA");
    public static final SpigotColor DARK_RED = new SpigotColor(ChatColor.DARK_RED, "#AA0000");
    public static final SpigotColor DARK_PURPLE = new SpigotColor(ChatColor.DARK_PURPLE, "#AA00AA");
    public static final SpigotColor GOLD = new SpigotColor(ChatColor.GOLD, "#FFAA00");
    public static final SpigotColor GRAY = new SpigotColor(ChatColor.GRAY, "#AAAAAA");
    public static final SpigotColor DARK_GRAY = new SpigotColor(ChatColor.DARK_GRAY, "#555555");
    public static final SpigotColor BLUE = new SpigotColor(ChatColor.BLUE, "#5555FF");
    public static final SpigotColor GREEN = new SpigotColor(ChatColor.GREEN, "#55FF55");
    public static final SpigotColor AQUA = new SpigotColor(ChatColor.AQUA, "#55FFFF");
    public static final SpigotColor RED = new SpigotColor(ChatColor.RED, "#FF5555");
    public static final SpigotColor LIGHT_PURPLE = new SpigotColor(ChatColor.LIGHT_PURPLE, "#FF55FF");
    public static final SpigotColor YELLOW = new SpigotColor(ChatColor.YELLOW, "#FFFF55");
    public static final SpigotColor WHITE = new SpigotColor(ChatColor.WHITE, "#FFFFFF");
    public static final SpigotColor MAGIC = new SpigotColor(ChatColor.MAGIC, "");
    public static final SpigotColor BOLD = new SpigotColor(ChatColor.BOLD, "");
    public static final SpigotColor STRIKETHROUGH = new SpigotColor(ChatColor.STRIKETHROUGH, "");
    public static final SpigotColor UNDERLINE = new SpigotColor(ChatColor.UNDERLINE, "");
    public static final SpigotColor ITALIC = new SpigotColor(ChatColor.ITALIC, "");
    public static final SpigotColor RESET = new SpigotColor(ChatColor.RESET, "");
    
    public SpigotColor(ChatColor chatColor, String hexCode) {
        super(StarCore.getPlugin(StarCore.class));
        this.symbol = '&';
        this.code = chatColor.getChar();
        this.permission = "starcore.color.spigot." + chatColor.name().toLowerCase();
        this.spigotColor = chatColor.asBungee();
        this.hex = hexCode;
        ColorUtils.spigotColors.put(getChatCode(), this);
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

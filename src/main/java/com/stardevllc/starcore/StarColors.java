package com.stardevllc.starcore;

import com.stardevllc.starcore.base.NMSVersion;
import com.stardevllc.starcore.v1_16.ColorHandler_1_16;
import com.stardevllc.starcore.v1_8.ColorHandler_1_8;
import com.stardevllc.starcore.base.colors.ColorHandler;
import com.stardevllc.starcore.base.colors.CustomColor;
import org.bukkit.command.CommandSender;

import java.util.Map;

public class StarColors {
    private static final ColorHandler colorHandler;
    
    static {
        NMSVersion currentVersion = NMSVersion.CURRENT_VERSION;
        if (currentVersion.ordinal() < NMSVersion.v1_16_R1.ordinal()) {
            colorHandler = new ColorHandler_1_8();
        } else {
            colorHandler = new ColorHandler_1_16();
        }
    }

    public static void coloredMessage(CommandSender sender, String message) {
        colorHandler.coloredMessage(sender, message);
    }

    public static String color(String uncolored) {
        return colorHandler.color(uncolored);
    }

    public static String color(CommandSender sender, String uncolored) {
        return colorHandler.color(sender, uncolored);
    }

    public static String color(String uncolored, boolean translateBukkit, boolean translateCustom, boolean translateHex) {
        return colorHandler.color(uncolored, translateBukkit, translateCustom, translateHex);
    }

    public static String translateBukkit(String uncolored) {
        return colorHandler.translateBukkit(uncolored);
    }

    public static String translateBukkit(CommandSender sender, String uncolored) {
        return colorHandler.translateBukkit(sender, uncolored);
    }

    public static String translateCustom(String uncolored) {
        return colorHandler.translateCustom(uncolored);
    }

    public static String translateCustom(CommandSender sender, String uncolored) {
        return colorHandler.translateCustom(sender, uncolored);
    }

    public static String translateHex(String uncolored) {
        return colorHandler.translateHex(uncolored);
    }

    public static String translateHex(CommandSender sender, String uncolored) {
        return colorHandler.translateHex(sender, uncolored);
    }

    public static String color(CommandSender sender, String uncolored, boolean translateBukkit, boolean translateCustom, boolean translateHex) {
        return colorHandler.color(sender, uncolored, translateBukkit, translateCustom, translateHex);
    }
    
    public static void addCustomColor(CustomColor color) {
        colorHandler.addCustomColor(color);
    }
    
    public static CustomColor getCustomColor(String code) {
        return colorHandler.getCustomColor(code);
    }
    
    public static Map<String, CustomColor> getCustomColors() {
        return colorHandler.getCustomColors();
    }
    
    public static char[] getPrefixSymbols() {
        return colorHandler.getPrefixSymbols();
    }
    
    public static boolean isSpigotColor(String code) {
        return ColorHandler.isSpigotColor(code);
    }

    public static boolean isValidCode(String code) {
        return ColorHandler.isValidCode(code);
    }

    public static boolean isValidHex(String str) {
        return ColorHandler.isValidHex(str);
    }

    public static void removeColor(String code) {
        colorHandler.removeColor(code);
    }

    public static boolean isValidSymbol(char c) {
        return colorHandler.isValidSymbol(c);
    }
    
    public static String stripColor(String text) {
        return ColorHandler.stripColor(text);
    }
}

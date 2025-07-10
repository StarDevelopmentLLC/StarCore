package com.stardevllc.starcore.api.colors;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;

@SuppressWarnings("JavaReflectionMemberAccess")
public class CustomColor {
    protected final JavaPlugin owner;
    protected char symbol, code;
    protected String hex, permission = "";

    protected ChatColor spigotColor;

    public CustomColor(JavaPlugin owner) {
        this.owner = owner;
    }

    public CustomColor symbolCode(String symbolCode) {
        if (ColorHandler.isValidCode(symbolCode)) {
            this.symbol = symbolCode.charAt(0);
            this.code = symbolCode.charAt(1);
        }
        return this;
    }

    public CustomColor hexValue(String hex) {
        if (ColorHandler.isValidHex(hex)) {
            this.hex = hex;
            calculateFields();
        }
        return this;
    }

    public String getChatCode() {
        return this.symbol + "" + this.code;
    }

    public CustomColor spigotColor(ChatColor color) {
        this.spigotColor = color;
        calculateFields();
        return this;
    }

    public CustomColor permission(String permission) {
        this.permission = permission;
        return this;
    }

    public JavaPlugin getOwner() {
        return owner;
    }

    public String getHex() {
        return hex;
    }

    public ChatColor getSpigotColor() {
        return spigotColor;
    }

    protected void calculateFields() {
        if (ColorHandler.isHexSupported()) {
            if (this.hex != null && !this.hex.isEmpty()) {
                try {
                    Method ofMethod = ChatColor.class.getDeclaredMethod("of", String.class);
                    this.spigotColor = (ChatColor) ofMethod.invoke(null, this.hex);
                } catch (Exception e) {}
            }
        }
    }

    public char getSymbol() {
        return symbol;
    }

    public char getCode() {
        return code;
    }

    public String getPermission() {
        return permission;
    }

    @Override
    public String toString() {
        return getChatCode();
    }
}

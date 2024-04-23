package com.stardevllc.starcore.color;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomColor {
    protected final JavaPlugin owner;
    protected char symbol, code;
    protected String hex, permission = "";

    protected ChatColor spigotColor;

    public CustomColor(JavaPlugin owner) {
        this.owner = owner;
    }

    public CustomColor symbolCode(String symbolCode) {
        if (ColorUtils.isValidCode(symbolCode)) {
            this.symbol = symbolCode.charAt(0);
            this.code = symbolCode.charAt(1);
        }
        return this;
    }

    public CustomColor hexValue(String hex) {
        if (ColorUtils.isValidHex(hex)) {
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
        if (ColorUtils.isHexSupported()) {
            if (this.hex != null && !this.hex.isEmpty()) {
                this.spigotColor = ChatColor.of(this.hex);
            }

            if (this.spigotColor != null) {
                this.hex = Integer.toHexString(this.spigotColor.getColor().getRGB()).substring(2);
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

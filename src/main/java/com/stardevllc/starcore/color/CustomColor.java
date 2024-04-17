package com.stardevllc.starcore.color;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This class represents a custom color value <br>
 * This is created using builder-style methods <br>
 */
public class CustomColor {
    protected final JavaPlugin owner;
    protected char symbol, code;
    protected String hex, permission = "";

    protected ChatColor spigotColor;

    /**
     * Constructs a new color
     * @param owner The owner plugin.
     */
    public CustomColor(JavaPlugin owner) {
        this.owner = owner;
    }

    /**
     * Sets the symbol code for this color
     * @param symbolCode The 2 character code. See the {@link ColorUtils#COLOR_CODE_PATTERN} for valid values. Just like normal chat colors, but with custom characters.
     * @return Builder style instance
     */
    public CustomColor symbolCode(String symbolCode) {
        if (ColorUtils.isValidCode(symbolCode)) {
            this.symbol = symbolCode.charAt(0);
            this.code = symbolCode.charAt(1);
        }
        return this;
    }

    /**
     * Set the hex value. This will populate the other values stored as well
     * @param hex The color value in hex format.
     * @return Builder style instance
     */
    public CustomColor hexValue(String hex) {
        if (ColorUtils.isValidHex(hex)) {
            this.hex = hex;
            calculateFields();
        }
        return this;
    }

    /**
     * @return The user-friendly chat code of this color
     */
    public String getChatCode() {
        return this.symbol + "" + this.code;
    }

    /**
     * Sets the color value based on the BungeeCord ChatColor enum
     * @param color The color
     * @return Builder style instance
     */
    public CustomColor spigotColor(ChatColor color) {
        this.spigotColor = color;
        calculateFields();
        return this;
    }

    /**
     * Sets the permission for this color (Only works with calls to the methods that take in a {@link CommandSender}
     * @param permission The permission
     * @return Builder style instance
     */
    public CustomColor permission(String permission) {
        this.permission = permission;
        return this;
    }

    /**
     * @return The owning plugin
     */
    public JavaPlugin getOwner() {
        return owner;
    }

    /**
     * @return The HEX value
     */
    public String getHex() {
        return hex;
    }

    /**
     * @return The BungeeCord ChatColor value
     */
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

    /**
     * @return The prefix symbol
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * @return The code used after the prefix symbol
     */
    public char getCode() {
        return code;
    }

    /**
     * @return The permission needed for this color.
     */
    public String getPermission() {
        return permission;
    }

    @Override
    public String toString() {
        return getChatCode();
    }
}

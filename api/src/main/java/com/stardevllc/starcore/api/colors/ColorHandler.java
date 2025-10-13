package com.stardevllc.starcore.api.colors;

import com.stardevllc.smcversion.MinecraftVersion;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public abstract class ColorHandler {
    public static final char[] COLOR_SYMBOLS = {'&', '~', '`', '!', '@', '$', '%', '^', '*', '?'};
    public static final Pattern COLOR_CODE_PATTERN = Pattern.compile("[&~`!@$%^*?][0-9A-Z]", Pattern.CASE_INSENSITIVE);
    public static final Pattern HEX_VALUE_PATTERN = Pattern.compile("^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$", Pattern.CASE_INSENSITIVE);
    public static final Map<String, SpigotColor> spigotColors = new HashMap<>();
    
    static {
        SpigotColor.SPIGOT_COLORS.forEach(color -> spigotColors.put(color.getChatCode(), color));
    }

    protected Map<String, CustomColor> customColors = new HashMap<>();
    
    public void coloredMessage(CommandSender sender, String message) {
        sender.sendMessage(color(message));
    }

    public String color(String uncolored) {
        return color(null, uncolored);
    }

    public String color(CommandSender sender, String uncolored) {
        return color(sender, uncolored, true, true, isHexSupported());
    }

    public String color(String uncolored, boolean translateBukkit, boolean translateCustom, boolean translateHex) {
        return color(null, uncolored, translateBukkit, translateCustom, isHexSupported() && translateHex);
    }

    public String translateBukkit(String uncolored) {
        return translateBukkit(null, uncolored);
    }

    public String translateBukkit(CommandSender sender, String uncolored) {
        if (sender == null || sender.isOp()) {
            return ChatColor.translateAlternateColorCodes('&', uncolored);
        }

        StringBuilder colored = new StringBuilder();
        for (int i = 0; i < uncolored.length(); i++) {
            char c = uncolored.charAt(i);
            if (c == '&') {
                if (uncolored.length() >= i + 1) {
                    String code = "&" + uncolored.charAt(i + 1);
                    SpigotColor spigotColor = spigotColors.get(code.toLowerCase());
                    if (spigotColor != null && hasPermission(sender, spigotColor.getPermission())) {
                        colored.append(ChatColor.COLOR_CHAR);
                    } else {
                        colored.append('&');
                    }
                }
            } else {
                colored.append(c);
            }
        }

        return colored.toString();
    }

    public String translateCustom(String uncolored) {
        return translateCustom(null, uncolored);
    }

    public String translateCustom(CommandSender sender, String uncolored) {
        StringBuilder colored = new StringBuilder();
        for (int i = 0; i < uncolored.length(); i++) {
            char c = uncolored.charAt(i);
            if (isValidSymbol(c)) {
                if (uncolored.length() > i + 1) {
                    String code = String.valueOf(c) + uncolored.charAt(i + 1);
                    CustomColor color = getCustomColor(code);
                    if (color != null && hasPermission(sender, color.getPermission())) {
                        colored.append(color.getSpigotColor());
                        i++;
                        continue;
                    }
                }
            }
            colored.append(c);
        }

        return colored.toString();
    }

    public String translateHex(String uncolored) {
        return translateHex(null, uncolored);
    }
    
    public static boolean isHexSupported() {
        return MinecraftVersion.CURRENT_VERSION.ordinal() >= MinecraftVersion.v1_16.ordinal();
    }
    
    public abstract String translateHex(CommandSender sender, String uncolored);

    public String color(CommandSender sender, String uncolored, boolean translateBukkit, boolean translateCustom, boolean translateHex) {
        String text = uncolored;
        if (translateBukkit) {
            text = translateBukkit(sender, text);
        }

        if (translateCustom) {
            text = translateCustom(sender, text);
        }

        if (translateHex) {
            return translateHex(sender, text);
        }

        return text;
    }

    public boolean hasPermission(CommandSender sender, String permission) {
        if (sender == null || permission == null || permission.isEmpty()) {
            return true;
        }

        return sender.hasPermission(permission);
    }

    public static boolean isValidCode(String code) {
        if (code == null || code.isEmpty()) {
            return false;
        }
        return COLOR_CODE_PATTERN.matcher(code).matches();
    }

    public static boolean isValidHex(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return HEX_VALUE_PATTERN.matcher(str).matches();
    }

    public void addCustomColor(CustomColor customColor) {
        customColors.put(customColor.getChatCode(), customColor);
    }

    public CustomColor getCustomColor(String code) {
        return customColors.get(code);
    }

    public void removeColor(String code) {
        customColors.remove(code);
    }

    public boolean isValidSymbol(char c) {
        for (char colorChar : COLOR_SYMBOLS) {
            if (colorChar == c) {
                return true;
            }
        }
        return false;
    }

    public char[] getPrefixSymbols() {
        return COLOR_SYMBOLS;
    }

    public Map<String, CustomColor> getCustomColors() {
        return new HashMap<>(customColors);
    }

    public static String stripColor(String text) {
        if (text == null || text.isBlank()) {
            return "";
        }
        
        text = ChatColor.stripColor(text);
        text = HEX_VALUE_PATTERN.matcher(text).replaceAll("");
        return COLOR_CODE_PATTERN.matcher(text).replaceAll("");
    }

    public static boolean isSpigotColor(String code) {
        return spigotColors.containsKey(code.toLowerCase());
    }
}

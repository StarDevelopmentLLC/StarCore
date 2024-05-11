package com.stardevllc.starcore.color;

import com.stardevllc.starcore.utils.NMSVersion;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public final class ColorUtils {
    private static final char[] COLOR_SYMBOLS = {'&', '~', '`', '!', '@', '$', '%', '^', '*', '?'};
    public static final Pattern COLOR_CODE_PATTERN = Pattern.compile("[&~`!@$%^*?][0-9A-Z]", Pattern.CASE_INSENSITIVE);
    public static final Pattern HEX_VALUE_PATTERN = Pattern.compile("^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$", Pattern.CASE_INSENSITIVE);

    private static Map<String, CustomColor> customColors = new HashMap<>();
    public static Map<String, SpigotColor> spigotColors = new HashMap<>();
    
    static {
        if (isHexSupported()) { //If Hex codes are supported, then register the material colors
            MaterialColor.MATERIAL_COLORS.forEach(color -> customColors.put(color.getChatCode(), color));
        }
    }
    
    public static void coloredMessage(CommandSender sender, String message) {
        sender.sendMessage(color(message));
    }

    public static String color(String uncolored) {
        return color(null, uncolored);
    }

    public static String color(CommandSender sender, String uncolored) {
        return color(sender, uncolored, true, true, isHexSupported());
    }

    public static String color(String uncolored, boolean translateBukkit, boolean translateCustom, boolean translateHex) {
        return color(null, uncolored, translateBukkit, translateCustom, isHexSupported() && translateHex);
    }

    public static String translateBukkit(String uncolored) {
        return translateBukkit(null, uncolored);
    }

    public static String translateBukkit(CommandSender sender, String uncolored) {
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

    public static String translateCustom(String uncolored) {
        return translateCustom(null, uncolored);
    }

    public static String translateCustom(CommandSender sender, String uncolored) {
        StringBuilder colored = new StringBuilder();
        for (int i = 0; i < uncolored.length(); i++) {
            char c = uncolored.charAt(i);
            if (ColorUtils.isValidSymbol(c)) {
                if (uncolored.length() > i + 1) {
                    String code = String.valueOf(c) + uncolored.charAt(i + 1);
                    CustomColor color = ColorUtils.getCustomColor(code);
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

    public static String translateHex(String uncolored) {
        return translateHex(null, uncolored);
    }
    
    public static boolean isHexSupported() {
        return NMSVersion.CURRENT_VERSION.ordinal() >= NMSVersion.v1_16_R1.ordinal();
    }
    
    public static String translateHex(CommandSender sender, String uncolored) {
        StringBuilder colored = new StringBuilder();
        if (!isHexSupported()) {
            Bukkit.getLogger().warning("Hex Colors are not supported by this Minecraft Version, ignoring them.");
            return uncolored;
        }
        for (int i = 0; i < uncolored.length(); i++) {
            char c = uncolored.charAt(i);
            if (c == '#') {
                if (hasPermission(sender, "starmclib.color.hex")) {
                    if (uncolored.length() > i + 6) {
                        String colorCode = '#' + uncolored.substring(i + 1, i + 7);
                        ChatColor color = ChatColor.of(colorCode);
                        colored.append(color);
                        i += 6;
                        continue;
                    }
                }
            }
            colored.append(c);
        }
        return colored.toString();
    }

    public static String color(CommandSender sender, String uncolored, boolean translateBukkit, boolean translateCustom, boolean translateHex) {
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

    private static boolean hasPermission(CommandSender sender, String permission) {
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

    public static void addCustomColor(CustomColor customColor) {
        customColors.put(customColor.getChatCode(), customColor);
    }

    public static CustomColor getCustomColor(String code) {
        return customColors.get(code);
    }

    public static void removeColor(String code) {
        customColors.remove(code);
    }

    public static boolean isValidSymbol(char c) {
        for (char colorChar : COLOR_SYMBOLS) {
            if (colorChar == c) {
                return true;
            }
        }
        return false;
    }

    public static char[] getPrefixSymbols() {
        return COLOR_SYMBOLS;
    }

    public static Map<String, CustomColor> getCustomColors() {
        return new HashMap<>(customColors);
    }

    public static String stripColor(String text) {
        text = ChatColor.stripColor(text);
        text = HEX_VALUE_PATTERN.matcher(text).replaceAll("");
        return COLOR_CODE_PATTERN.matcher(text).replaceAll("");
    }

    public static boolean isSpigotColor(String code) {
        return spigotColors.containsKey(code.toLowerCase());
    }
}

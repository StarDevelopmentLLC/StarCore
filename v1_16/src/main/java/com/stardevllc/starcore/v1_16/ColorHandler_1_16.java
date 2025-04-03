package com.stardevllc.starcore.v1_16;

import com.stardevllc.starcore.base.colors.ColorHandler;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class ColorHandler_1_16 extends ColorHandler {
    public ColorHandler_1_16() {
        MaterialColor.MATERIAL_COLORS.forEach(color -> customColors.put(color.getChatCode(), color));
    }

    public String translateHex(CommandSender sender, String uncolored) {
        StringBuilder colored = new StringBuilder();
        if (!isHexSupported()) {
            Bukkit.getLogger().warning("Hex Colors are not supported by this Minecraft Version, ignoring them.");
            return uncolored;
        }
        for (int i = 0; i < uncolored.length(); i++) {
            char c = uncolored.charAt(i);
            if (c == '#') {
                if (hasPermission(sender, "starcore.color.hex")) {
                    if (uncolored.length() > i + 6) {
                        String colorCode = '#' + uncolored.substring(i + 1, i + 7);
                        ChatColor color = ChatColor.of(colorCode);
                        colored.append(color);
                        continue;
                    }
                }
            }
            colored.append(c);
        }
        return colored.toString();
    }
}

package com.stardevllc.starcore.gui.element;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Future use
 */
public class FillerElement extends Element {
    public FillerElement(Material material) {
        iconCreator(player -> {
            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.WHITE.toString());
            item.setItemMeta(meta);
            return item;
        });
    }
}
package com.stardevllc.starcore.api.wrappers;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface PlayerHandWrapper {
    ItemStack getItemInMainHand(Player player);
    ItemStack getItemInOffHand(Player player);
}

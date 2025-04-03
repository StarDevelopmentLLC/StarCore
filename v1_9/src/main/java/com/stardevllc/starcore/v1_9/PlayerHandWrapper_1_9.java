package com.stardevllc.starcore.v1_9;

import com.stardevllc.starcore.base.wrappers.PlayerHandWrapper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerHandWrapper_1_9 implements PlayerHandWrapper {
    @Override
    public ItemStack getItemInMainHand(Player player) {
        return player.getInventory().getItemInMainHand();
    }

    @Override
    public ItemStack getItemInOffHand(Player player) {
        return player.getInventory().getItemInOffHand();
    }
}

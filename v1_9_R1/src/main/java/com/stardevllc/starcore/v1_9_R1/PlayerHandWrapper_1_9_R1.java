package com.stardevllc.starcore.v1_9_R1;

import com.stardevllc.starcore.api.wrappers.PlayerHandWrapper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerHandWrapper_1_9_R1 implements PlayerHandWrapper {
    @Override
    public ItemStack getItemInMainHand(Player player) {
        return player.getInventory().getItemInMainHand();
    }

    @Override
    public ItemStack getItemInOffHand(Player player) {
        return player.getInventory().getItemInOffHand();
    }
}

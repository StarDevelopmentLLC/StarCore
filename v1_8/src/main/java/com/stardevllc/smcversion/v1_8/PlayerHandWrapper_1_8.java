package com.stardevllc.smcversion.v1_8;

import com.stardevllc.smcversion.wrappers.PlayerHandWrapper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerHandWrapper_1_8 implements PlayerHandWrapper {
    @Override
    public ItemStack getItemInMainHand(Player player) {
        return player.getItemInHand();
    }

    @Override
    public ItemStack getItemInOffHand(Player player) {
        return null;
    }
}

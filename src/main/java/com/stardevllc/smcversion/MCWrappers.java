package com.stardevllc.smcversion;

import com.stardevllc.smcversion.v1_13_2.EnchantWrapper_1_13_2;
import com.stardevllc.smcversion.v1_13_2.ItemWrapper_1_13_2;
import com.stardevllc.smcversion.v1_8.*;
import com.stardevllc.smcversion.v1_9_2.PlayerHandWrapper_1_9_2;
import com.stardevllc.smcversion.wrappers.*;

public final class MCWrappers {
    private MCWrappers() {}
    
    private static EnchantWrapper enchantWrapper;
    private static ItemWrapper itemWrapper;
    private static PlayerHandWrapper playerHandWrapper;
    
    public static EnchantWrapper getEnchantWrapper() {
        if (enchantWrapper == null) {
            if (MinecraftVersion.CURRENT_VERSION.ordinal() <= MinecraftVersion.v1_13.ordinal()) {
                enchantWrapper = new EnchantWrapper_1_8();
            } else if (MinecraftVersion.CURRENT_VERSION.ordinal() >= MinecraftVersion.v1_13_2.ordinal()) {
                enchantWrapper = new EnchantWrapper_1_13_2();
            } else {
                enchantWrapper = null;
            }
        }
        
        return enchantWrapper;
    }
    
    public static ItemWrapper getItemWrapper() {
        if (itemWrapper == null) {
            if (MinecraftVersion.CURRENT_VERSION.ordinal() <= MinecraftVersion.v1_10.ordinal()) {
                itemWrapper = new ItemWrapper_1_8();
            } else if (MinecraftVersion.CURRENT_VERSION.ordinal() >= MinecraftVersion.v1_13_2.ordinal()) {
                itemWrapper = new ItemWrapper_1_13_2();
            } else {
                itemWrapper = null;
            }
        }
        return itemWrapper;
    }
    
    public static PlayerHandWrapper getPlayerHandWrapper() {
        if (playerHandWrapper == null) {
            if (MinecraftVersion.CURRENT_VERSION.ordinal() <= MinecraftVersion.v1_8_8.ordinal()) {
                playerHandWrapper = new PlayerHandWrapper_1_8();
            } else if (MinecraftVersion.CURRENT_VERSION.ordinal() >= MinecraftVersion.v1_9.ordinal()) {
                playerHandWrapper = new PlayerHandWrapper_1_9_2();
            } else {
                playerHandWrapper = null;
            }
        }
        return playerHandWrapper;
    }
}

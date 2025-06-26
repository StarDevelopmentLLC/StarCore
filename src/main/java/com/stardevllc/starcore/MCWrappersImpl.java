package com.stardevllc.starcore;

import com.stardevllc.starcore.api.wrappers.*;
import com.stardevllc.starcore.v1_11_R1.ItemWrapper_1_11_R1;
import com.stardevllc.starcore.v1_13_R2.EnchantWrapper_1_13_R2;
import com.stardevllc.starcore.v1_13_R2.ItemWrapper_1_13_R2;
import com.stardevllc.starcore.v1_8_R1.*;
import com.stardevllc.starcore.v1_9_R1.PlayerHandWrapper_1_9_R1;
import com.stardevllc.starmclib.MinecraftVersion;

public class MCWrappersImpl implements MCWrappers {
    
    private EnchantWrapper enchantWrapper;
    private ItemWrapper itemWrapper;
    private PlayerHandWrapper playerHandWrapper;
    
    @Override
    public EnchantWrapper getEnchantWrapper() {
        if (enchantWrapper == null) {
            if (MinecraftVersion.CURRENT_VERSION.ordinal() <= MinecraftVersion.v1_13.ordinal()) {
                enchantWrapper = new EnchantWrapper_1_8_R1();
            } else if (MinecraftVersion.CURRENT_VERSION.ordinal() >= MinecraftVersion.v1_13_2.ordinal()) {
                enchantWrapper = new EnchantWrapper_1_13_R2();
            } else {
                enchantWrapper = null;
            }
        }
        
        return enchantWrapper;
    }
    
    @Override
    public ItemWrapper getItemWrapper() {
        if (itemWrapper == null) {
            if (MinecraftVersion.CURRENT_VERSION.ordinal() <= MinecraftVersion.v1_10.ordinal()) {
                itemWrapper = new ItemWrapper_1_8_R1();
            } else if (MinecraftVersion.CURRENT_VERSION.ordinal() <= MinecraftVersion.v1_13.ordinal()) {
                itemWrapper = new ItemWrapper_1_11_R1();
            } else if (MinecraftVersion.CURRENT_VERSION.ordinal() >= MinecraftVersion.v1_13_2.ordinal()) {
                itemWrapper = new ItemWrapper_1_13_R2();
            } else {
                itemWrapper = null;
            }
        }
        return itemWrapper;
    }
    
    @Override
    public PlayerHandWrapper getPlayerHandWrapper() {
        if (playerHandWrapper == null) {
            if (MinecraftVersion.CURRENT_VERSION.ordinal() <= MinecraftVersion.v1_8_8.ordinal()) {
                playerHandWrapper = new PlayerHandWrapper_1_8_R1();
            } else if (MinecraftVersion.CURRENT_VERSION.ordinal() >= MinecraftVersion.v1_9.ordinal()) {
                playerHandWrapper = new PlayerHandWrapper_1_9_R1();
            } else {
                playerHandWrapper = null;
            }
        }
        return playerHandWrapper;
    }
}

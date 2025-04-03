package com.stardevllc.starcore;

import com.stardevllc.starcore.base.NMSVersion;
import com.stardevllc.starcore.base.wrappers.*;
import com.stardevllc.starcore.v1_11.ItemWrapper_1_11;
import com.stardevllc.starcore.v1_13_R2.EnchantWrapper_1_13_R2;
import com.stardevllc.starcore.v1_13_R2.ItemWrapper_1_13_R2;
import com.stardevllc.starcore.v1_8.*;
import com.stardevllc.starcore.v1_9.PlayerHandWrapper_1_9;

public class MCWrappersImpl implements MCWrappers{
    
    private EnchantWrapper enchantWrapper;
    private ItemWrapper itemWrapper;
    private PlayerHandWrapper playerHandWrapper;
    
    @Override
    public EnchantWrapper getEnchantWrapper() {
        if (enchantWrapper == null) {
            if (NMSVersion.CURRENT_VERSION.ordinal() <= NMSVersion.v1_13_R1.ordinal()) {
                enchantWrapper = new EnchantWrapper_1_8();
            } else if (NMSVersion.CURRENT_VERSION.ordinal() >= NMSVersion.v1_13_R2.ordinal()) {
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
            if (NMSVersion.CURRENT_VERSION.ordinal() <= NMSVersion.v1_10_R1.ordinal()) {
                itemWrapper = new ItemWrapper_1_8();
            } else if (NMSVersion.CURRENT_VERSION.ordinal() <= NMSVersion.v1_13_R1.ordinal()) {
                itemWrapper = new ItemWrapper_1_11();
            } else if (NMSVersion.CURRENT_VERSION.ordinal() >= NMSVersion.v1_13_R2.ordinal()) {
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
            if (NMSVersion.CURRENT_VERSION.ordinal() <= NMSVersion.v1_8_R3.ordinal()) {
                playerHandWrapper = new PlayerHandWrapper_1_8();
            } else if (NMSVersion.CURRENT_VERSION.ordinal() >= NMSVersion.v1_9_R1.ordinal()) {
                playerHandWrapper = new PlayerHandWrapper_1_9();
            } else {
                playerHandWrapper = null;
            }
        }
        return playerHandWrapper;
    }
}

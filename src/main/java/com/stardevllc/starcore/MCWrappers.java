package com.stardevllc.starcore;

import com.stardevllc.starcore.base.NMSVersion;
import com.stardevllc.starcore.base.wrappers.*;
import com.stardevllc.starcore.v1_11.ItemWrapper_1_11;
import com.stardevllc.starcore.v1_13_R2.EnchantWrapper_1_13_R2;
import com.stardevllc.starcore.v1_13_R2.ItemWrapper_1_13_R2;
import com.stardevllc.starcore.v1_8.*;
import com.stardevllc.starcore.v1_9.PlayerHandWrapper_1_9;

public class MCWrappers {
    
    public static final EnchantWrapper ENCHANT_WRAPPER;
    public static final ItemWrapper ITEM_WRAPPER;
    public static final PlayerHandWrapper PLAYER_HAND_WRAPPER;
    
    static {
        if (NMSVersion.CURRENT_VERSION.ordinal() <= NMSVersion.v1_13_R1.ordinal()) {
            ENCHANT_WRAPPER = new EnchantWrapper_1_8();
        } else if (NMSVersion.CURRENT_VERSION.ordinal() >= NMSVersion.v1_13_R2.ordinal()) {
            ENCHANT_WRAPPER = new EnchantWrapper_1_13_R2();
        } else {
            ENCHANT_WRAPPER = null;
        }
        
        if (NMSVersion.CURRENT_VERSION.ordinal() <= NMSVersion.v1_10_R1.ordinal()) {
            ITEM_WRAPPER = new ItemWrapper_1_8();  
        } else if (NMSVersion.CURRENT_VERSION.ordinal() <= NMSVersion.v1_13_R1.ordinal()) {
            ITEM_WRAPPER = new ItemWrapper_1_11();
        } else if (NMSVersion.CURRENT_VERSION.ordinal() >= NMSVersion.v1_13_R2.ordinal()) {
            ITEM_WRAPPER = new ItemWrapper_1_13_R2();
        } else {
            ITEM_WRAPPER = null;
        }
        
        if (NMSVersion.CURRENT_VERSION.ordinal() <= NMSVersion.v1_8_R3.ordinal()) {
            PLAYER_HAND_WRAPPER = new PlayerHandWrapper_1_8();
        } else if (NMSVersion.CURRENT_VERSION.ordinal() >= NMSVersion.v1_9_R1.ordinal()) {
            PLAYER_HAND_WRAPPER = new PlayerHandWrapper_1_9();
        } else {
            PLAYER_HAND_WRAPPER = null;
        }
    }
}

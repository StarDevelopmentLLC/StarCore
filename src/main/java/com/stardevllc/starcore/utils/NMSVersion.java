package com.stardevllc.starcore.utils;

import com.stardevllc.starcore.wrapper.EnchantWrapper;
import com.stardevllc.starcore.wrapper.ItemWrapper;
import com.stardevllc.starcore.wrapper.v1_11.ItemWrapper_1_11;
import com.stardevllc.starcore.wrapper.v1_13_R2.EnchantWrapper_1_13_R2;
import com.stardevllc.starcore.wrapper.v1_13_R2.ItemWrapper_1_13_R2;
import com.stardevllc.starcore.wrapper.v1_8.EnchantWrapper_1_8;
import com.stardevllc.starcore.wrapper.v1_8.ItemWrapper_1_8;
import org.bukkit.Bukkit;

public enum NMSVersion {
    UNDEFINED,
    v1_8_R1(ItemWrapper_1_8.class, EnchantWrapper_1_8.class),
    v1_8_R2(ItemWrapper_1_8.class, EnchantWrapper_1_8.class),
    v1_8_R3(ItemWrapper_1_8.class, EnchantWrapper_1_8.class),
    v1_9_R1(ItemWrapper_1_8.class, EnchantWrapper_1_8.class),
    v1_9_R2(ItemWrapper_1_8.class, EnchantWrapper_1_8.class), 
    v1_10_R1(ItemWrapper_1_8.class, EnchantWrapper_1_8.class), 
    v1_11_R1(ItemWrapper_1_11.class, EnchantWrapper_1_8.class), 
    v1_12_R1(ItemWrapper_1_11.class, EnchantWrapper_1_8.class),
    v1_13_R1(ItemWrapper_1_11.class, EnchantWrapper_1_8.class),
    v1_13_R2(ItemWrapper_1_13_R2.class, EnchantWrapper_1_13_R2.class), 
    v1_14_R1(ItemWrapper_1_13_R2.class, EnchantWrapper_1_13_R2.class), 
    v1_15_R1(ItemWrapper_1_13_R2.class, EnchantWrapper_1_13_R2.class),
    v1_16_R1(ItemWrapper_1_13_R2.class, EnchantWrapper_1_13_R2.class),
    v1_16_R2(ItemWrapper_1_13_R2.class, EnchantWrapper_1_13_R2.class),
    v1_16_R3(ItemWrapper_1_13_R2.class, EnchantWrapper_1_13_R2.class), 
    v1_17_R1(ItemWrapper_1_13_R2.class, EnchantWrapper_1_13_R2.class),
    v1_18_R1(ItemWrapper_1_13_R2.class, EnchantWrapper_1_13_R2.class),
    v1_18_R2(ItemWrapper_1_13_R2.class, EnchantWrapper_1_13_R2.class),
    v1_19_R1(ItemWrapper_1_13_R2.class, EnchantWrapper_1_13_R2.class),
    v1_19_R2(ItemWrapper_1_13_R2.class, EnchantWrapper_1_13_R2.class),
    v1_19_R3(ItemWrapper_1_13_R2.class, EnchantWrapper_1_13_R2.class), 
    v1_20_R1(ItemWrapper_1_13_R2.class, EnchantWrapper_1_13_R2.class),
    v1_20_R2(ItemWrapper_1_13_R2.class, EnchantWrapper_1_13_R2.class), 
    v1_20_R3(ItemWrapper_1_13_R2.class, EnchantWrapper_1_13_R2.class);
    
    private Class<? extends ItemWrapper> itemWrapper;
    private Class<? extends EnchantWrapper> enchantWrapper;

    NMSVersion() {
    }

    NMSVersion(Class<? extends ItemWrapper> itemWrapper, Class<? extends EnchantWrapper> enchantWrapper) {
        this.itemWrapper = itemWrapper;
        this.enchantWrapper = enchantWrapper;
    }

    public Class<? extends ItemWrapper> getItemWrapper() {
        return itemWrapper;
    }

    public Class<? extends EnchantWrapper> getEnchantWrapper() {
        return enchantWrapper;
    }

    public static final NMSVersion CURRENT_VERSION = getCurrentVersion();
    
    private static NMSVersion getCurrentVersion() {
        String a = Bukkit.getServer().getClass().getPackage().getName();
        String version = a.substring(a.lastIndexOf('.') + 1);
        NMSVersion nmsVersion;
        try {
            nmsVersion = valueOf(version);
        } catch (Exception e) {
            nmsVersion = NMSVersion.UNDEFINED;
        }
        return nmsVersion;
    }
}
package com.stardevllc.starcore.base;

import com.stardevllc.converter.string.EnumStringConverter;
import com.stardevllc.converter.string.StringConverters;

import java.lang.reflect.Method;

public enum NMSVersion {
    UNDEFINED,
    v1_8_R1,
    v1_8_R2,
    v1_8_R3,
    v1_9_R1,
    v1_9_R2,
    v1_10_R1,
    v1_11_R1,
    v1_12_R1,
    v1_13_R1,
    v1_13_R2,
    v1_14_R1,
    v1_15_R1, 
    v1_16_R1, 
    v1_16_R2,
    v1_16_R3,
    v1_17_R1,
    v1_18_R1,
    v1_18_R2,
    v1_19_R1,
    v1_19_R2,
    v1_19_R3,
    v1_20_R1,
    v1_20_R2,
    v1_20_R3,
    v1_20_R4,
    v1_21_R1, 
    v1_21_R2, 
    v1_21_R3;

    static {
        StringConverters.addConverter(NMSVersion.class, new EnumStringConverter<>(NMSVersion.class));
    }

    public static final NMSVersion CURRENT_VERSION = getCurrentVersion();

    private static NMSVersion getCurrentVersion() {
        try {
            Class<?> bukkitClass = Class.forName("org.bukkit.Bukkit");
            Method getServerMethod = bukkitClass.getMethod("getServer");
            String a = getServerMethod.invoke(null).getClass().getPackage().getName();
            String version = a.substring(a.lastIndexOf('.') + 1);
            System.out.println("[StarColors] Detected NMS Version: " + version);
            return valueOf(version);
        } catch (Exception e) {
            System.out.println("[StarColors] NMS Version incompatible with this version of StarCore, is it up to date?");
            return NMSVersion.UNDEFINED;
        }
    }
}
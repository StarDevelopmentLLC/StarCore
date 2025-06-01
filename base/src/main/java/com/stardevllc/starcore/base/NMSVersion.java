package com.stardevllc.starcore.base;

import com.stardevllc.converter.string.EnumStringConverter;
import com.stardevllc.converter.string.StringConverters;

import java.lang.reflect.Method;

public enum NMSVersion {
    UNDEFINED,
    v1_8_R1,  //1.8
    v1_8_R2,  //1.8.3
    v1_8_R3,  //1.8.4-1.8.8
    v1_9_R1,  //1.9, 1.9.2
    v1_9_R2,  //1.9.4
    v1_10_R1, //1.10, 1.10.2
    v1_11_R1, //1.11, 1.11.2
    v1_12_R1, //1.12, 1.12.1, 1.12.2
    v1_13_R1, //1.13
    v1_13_R2, //1.13.1, 1.13.2
    v1_14_R1, //1.14, 1.14.1, 1.14.2, 1.14.3, 1.14.4
    v1_15_R1, //1.15, 1.15.1, 1.15.2
    v1_16_R1, //1.16.1
    v1_16_R2, //1.16.2, 1.16.3
    v1_16_R3, //1.16.4, 1.16.5
    v1_17_R1, //1.17, 1.17.1
    v1_18_R1, //1.18, 1.18.1
    v1_18_R2, //1.18.2
    v1_19_R1, //1.19, 1.19.1, 1.19.2
    v1_19_R2, //1.19.3
    v1_19_R3, //1.19.4
    v1_20_R1, //1.20, 1.20.1
    v1_20_R2, //1.20.2
    v1_20_R3, //1.20.3, 1.20.4
    v1_20_R4, //1.20.5, 1.20.5, 1.20.6
    v1_21_R1, //1.21, 1.21.1
    v1_21_R2, //1.21.2, 1.21.3
    v1_21_R3; //1.21.4, 1.21.5
    
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
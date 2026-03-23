package com.stardevllc.smcversion;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.*;

public enum MinecraftVersion {
    v1_8, v1_8_1, v1_8_2, v1_8_3, v1_8_4, v1_8_5, v1_8_6, v1_8_7, v1_8_8, v1_8_9,
    v1_9, v1_9_1, v1_9_2, v1_9_3, v1_9_4, 
    v1_10, v1_10_1, v1_10_2, 
    v1_11, v1_11_1, v1_11_2, 
    v1_12, v1_12_1, v1_12_2, 
    v1_13, v1_13_1, v1_13_2, 
    v1_14, v1_14_1, v1_14_2, v1_14_3, v1_14_4, 
    v1_15, v1_15_1, v1_15_2, 
    v1_16, v1_16_1, v1_16_2, v1_16_3, v1_16_4, v1_16_5, 
    v1_17, v1_17_1, 
    v1_18, v1_18_1, v1_18_2, 
    v1_19, v1_19_1, v1_19_2, v1_19_3, v1_19_4, 
    v1_20, v1_20_1, v1_20_2, v1_20_3, v1_20_4, v1_20_5, v1_20_6, 
    v1_21, v1_21_1, v1_21_2, v1_21_3, v1_21_4, v1_21_5, v1_21_6, v1_21_7, v1_21_8, v1_21_9, v1_21_10, v1_21_11,
    
    UNDEFINED;
    
    static {
        //Reflectively add a string converter to the StringConverters to prevent having to have a dependency
        try {
            Class<?> stringConvertersClass = Class.forName("com.stardevllc.starlib.converter.string.StringConverters");
            Class<?> stringConverterClass = Class.forName("com.stardevllc.starlib.converter.string.StringConverter");
            Class<?> enumConverterClass = Class.forName("com.stardevllc.starlib.converter.string.EnumStringConverter");
            Constructor<?> enumConstructor = enumConverterClass.getDeclaredConstructor(Class.class);
            Object converter = enumConstructor.newInstance(MinecraftVersion.class);
            Method addConverterMethod = stringConvertersClass.getDeclaredMethod("addConverter", Class.class, stringConverterClass);
            addConverterMethod.invoke(null, MinecraftVersion.class, converter);
        } catch (Exception e) {}
    }
    
    public static final MinecraftVersion CURRENT_VERSION = getVersion();
    
    private static MinecraftVersion getVersion() {
        JavaPlugin plugin = null;
        try {
            Class<?> pluginClassLoader = Class.forName("org.bukkit.plugin.java.PluginClassLoader");
            ClassLoader classLoader = MinecraftVersion.class.getClassLoader();
            if (pluginClassLoader.isInstance(classLoader)) {
                Field pluginField = pluginClassLoader.getDeclaredField("plugin");
                pluginField.setAccessible(true);
                plugin = (JavaPlugin) pluginField.get(classLoader);
            }
        } catch (Throwable e) {}
        
        if (plugin == null) {
            Bukkit.getLogger().severe("[SMCVersion] Could not determine the plugin that holds the MinecraftVersion enum");
            return UNDEFINED;
        }
        
        String versionString = "";
        
        try {
            String spigotVersion = Bukkit.getServer().getVersion();
            String[] split = spigotVersion.split("MC: ");
            String[] split2 = split[1].split(" ");
            
            versionString = "v" + split2[0].replace(")", "").replace(".", "_");
            return valueOf(versionString);
        } catch (Exception e) {
            plugin.getLogger().info("Incompatible version detected (" + versionString + "). Please ensure that " + plugin.getName() + " supports this version of Minecraft");
        }
        
        return UNDEFINED;
    }
}
package com.stardevllc.smaterial;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@SuppressWarnings("JavaReflectionInvocation")
public enum ArmorSet {

    NONE(SMaterial.AIR, SMaterial.AIR, SMaterial.AIR, SMaterial.AIR),
    LEATHER(SMaterial.LEATHER_HELMET, SMaterial.LEATHER_CHESTPLATE, SMaterial.LEATHER_LEGGINGS, SMaterial.LEATHER_BOOTS),
    CHAINMAIL(SMaterial.CHAINMAIL_HELMET, SMaterial.CHAINMAIL_CHESTPLATE, SMaterial.CHAINMAIL_LEGGINGS, SMaterial.CHAINMAIL_BOOTS),
    GOLD(SMaterial.GOLDEN_HELMET, SMaterial.GOLDEN_CHESTPLATE, SMaterial.GOLDEN_LEGGINGS, SMaterial.GOLDEN_BOOTS),
    IRON(SMaterial.IRON_HELMET, SMaterial.IRON_CHESTPLATE, SMaterial.IRON_LEGGINGS, SMaterial.IRON_BOOTS),
    DIAMOND(SMaterial.DIAMOND_HELMET, SMaterial.DIAMOND_CHESTPLATE, SMaterial.DIAMOND_LEGGINGS, SMaterial.DIAMOND_BOOTS), 
    NETHERITE(SMaterial.NETHERITE_HELMET, SMaterial.NETHERITE_CHESTPLATE, SMaterial.NETHERITE_LEGGINGS, SMaterial.NETHERITE_BOOTS);
    
    static {
        //Reflectively add a string converter to the StringConverters to prevent having to have a dependency
        try {
            Class<?> stringConvertersClass = Class.forName("com.stardevllc.starlib.converter.string.StringConverters");
            Class<?> stringConverterClass = Class.forName("com.stardevllc.starlib.converter.string.StringConverter");
            Class<?> enumConverterClass = Class.forName("com.stardevllc.starlib.converter.string.EnumStringConverter");
            Constructor<?> enumConstructor = enumConverterClass.getDeclaredConstructor(Class.class);
            Object converter = enumConstructor.newInstance(ArmorSet.class);
            Method addConverterMethod = stringConvertersClass.getDeclaredMethod("addConverter", Class.class, stringConverterClass);
            addConverterMethod.invoke(null, ArmorSet.class, converter);
        } catch (Exception e) {}
    }

    private final SMaterial helmet, chestplate, leggings, boots;

    ArmorSet(SMaterial helmet, SMaterial chestplate, SMaterial leggings, SMaterial boots) {
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
    }

    public SMaterial getHelmet() {
        return helmet;
    }

    public SMaterial getChestplate() {
        return chestplate;
    }

    public SMaterial getLeggings() {
        return leggings;
    }

    public SMaterial getBoots() {
        return boots;
    }
}

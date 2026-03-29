package com.stardevllc.smaterial;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@SuppressWarnings("JavaReflectionInvocation")
public enum ArmorSlot {
    HELMET, CHESTPLATE, LEGGINGS, BOOTS;
    
    static {
        try {
            Class<?> stringConvertersClass = Class.forName("com.stardevllc.starlib.converter.string.StringConverters");
            Class<?> stringConverterClass = Class.forName("com.stardevllc.starlib.converter.string.StringConverter");
            Class<?> enumConverterClass = Class.forName("com.stardevllc.starlib.converter.string.EnumStringConverter");
            Constructor<?> enumConstructor = enumConverterClass.getDeclaredConstructor(Class.class);
            Object converter = enumConstructor.newInstance(ArmorSlot.class);
            Method addConverterMethod = stringConvertersClass.getDeclaredMethod("addConverter", Class.class, stringConverterClass);
            addConverterMethod.invoke(null, ArmorSlot.class, converter);
        } catch (Exception e) {}
    }
}

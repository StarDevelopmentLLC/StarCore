package com.stardevllc.smaterial;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@SuppressWarnings("JavaReflectionInvocation")
public enum ArmorMaterial {
    LEATHER, CHAINMAIL, GOLD, IRON, DIAMOND, NETHERITE;
    
    static {
        //Reflectively add a string converter to the StringConverters to prevent having to have a dependency
        try {
            Class<?> stringConvertersClass = Class.forName("com.stardevllc.starlib.converter.string.StringConverters");
            Class<?> stringConverterClass = Class.forName("com.stardevllc.starlib.converter.string.StringConverter");
            Class<?> enumConverterClass = Class.forName("com.stardevllc.starlib.converter.string.EnumStringConverter");
            Constructor<?> enumConstructor = enumConverterClass.getDeclaredConstructor(Class.class);
            Object converter = enumConstructor.newInstance(ArmorMaterial.class);
            Method addConverterMethod = stringConvertersClass.getDeclaredMethod("addConverter", Class.class, stringConverterClass);
            addConverterMethod.invoke(null, ArmorMaterial.class, converter);
        } catch (Exception e) {}
    }
}
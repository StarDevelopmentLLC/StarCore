package com.stardevllc.smaterial;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@SuppressWarnings("JavaReflectionInvocation")
public enum ToolMaterial {
    WOODEN, STONE, GOLDEN, IRON, DIAMOND, NETHERITE;
    
    static {
        //Reflectively add a string converter to the StringConverters to prevent having to have a dependency
        try {
            Class<?> stringConvertersClass = Class.forName("com.stardevllc.starlib.converter.string.StringConverters");
            Class<?> stringConverterClass = Class.forName("com.stardevllc.starlib.converter.string.StringConverter");
            Class<?> enumConverterClass = Class.forName("com.stardevllc.starlib.converter.string.EnumStringConverter");
            Constructor<?> enumConstructor = enumConverterClass.getDeclaredConstructor(Class.class);
            Object converter = enumConstructor.newInstance(ToolMaterial.class);
            Method addConverterMethod = stringConvertersClass.getDeclaredMethod("addConverter", Class.class, stringConverterClass);
            addConverterMethod.invoke(null, ToolMaterial.class, converter);
        } catch (Exception e) {}
    }
}

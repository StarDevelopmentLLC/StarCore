package com.stardevllc.smaterial;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@SuppressWarnings("JavaReflectionInvocation")
public enum ToolSet {
    
    WOOD(SMaterial.WOODEN_SWORD, SMaterial.WOODEN_PICKAXE, SMaterial.WOODEN_AXE, SMaterial.WOODEN_SHOVEL, SMaterial.WOODEN_HOE),
    STONE(SMaterial.STONE_SWORD, SMaterial.STONE_PICKAXE, SMaterial.STONE_AXE, SMaterial.STONE_SHOVEL, SMaterial.STONE_HOE),
    GOLD(SMaterial.GOLDEN_SWORD, SMaterial.GOLDEN_PICKAXE, SMaterial.GOLDEN_AXE, SMaterial.GOLDEN_SHOVEL, SMaterial.GOLDEN_HOE),
    IRON(SMaterial.IRON_SWORD, SMaterial.IRON_PICKAXE, SMaterial.IRON_AXE, SMaterial.IRON_SHOVEL, SMaterial.IRON_HOE),
    DIAMOND(SMaterial.DIAMOND_SWORD, SMaterial.DIAMOND_PICKAXE, SMaterial.DIAMOND_AXE, SMaterial.DIAMOND_SHOVEL, SMaterial.DIAMOND_HOE),
    NETHERITE(SMaterial.NETHERITE_SWORD, SMaterial.NETHERITE_PICKAXE, SMaterial.NETHERITE_AXE, SMaterial.NETHERITE_SHOVEL, SMaterial.NETHERITE_HOE);
    
    static {
        //Reflectively add a string converter to the StringConverters to prevent having to have a dependency
        try {
            Class<?> stringConvertersClass = Class.forName("com.stardevllc.starlib.converter.string.StringConverters");
            Class<?> stringConverterClass = Class.forName("com.stardevllc.starlib.converter.string.StringConverter");
            Class<?> enumConverterClass = Class.forName("com.stardevllc.starlib.converter.string.EnumStringConverter");
            Constructor<?> enumConstructor = enumConverterClass.getDeclaredConstructor(Class.class);
            Object converter = enumConstructor.newInstance(ToolSet.class);
            Method addConverterMethod = stringConvertersClass.getDeclaredMethod("addConverter", Class.class, stringConverterClass);
            addConverterMethod.invoke(null, ToolSet.class, converter);
        } catch (Exception e) {}
    }
    
    private final SMaterial sword, pickaxe, axe, shovel, hoe;

    ToolSet(SMaterial sword, SMaterial pickaxe, SMaterial axe, SMaterial shovel, SMaterial hoe) {
        this.sword = sword;
        this.pickaxe = pickaxe;
        this.axe = axe;
        this.shovel = shovel;
        this.hoe = hoe;
    }

    public SMaterial getPickaxe() {
        return pickaxe;
    }

    public SMaterial getAxe() {
        return axe;
    }

    public SMaterial getShovel() {
        return shovel;
    }

    public SMaterial getHoe() {
        return hoe;
    }

    public SMaterial getSword() {
        return sword;
    }
}
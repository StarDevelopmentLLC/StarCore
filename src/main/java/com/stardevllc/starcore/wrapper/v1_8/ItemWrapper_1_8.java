package com.stardevllc.starcore.wrapper.v1_8;

import com.stardevllc.starcore.wrapper.AttributeModifierWrapper;
import com.stardevllc.starcore.wrapper.ItemWrapper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Method;
import java.util.Map;

@SuppressWarnings("JavaReflectionMemberAccess")
public class ItemWrapper_1_8 implements ItemWrapper {
    
    private static Method spigotMethod;
    private static Class<?> spigotClass;
    private static Method isUnbreakableMethod;
    private static Method setUnbreakableMethod;
    
    static {
        try {
            spigotMethod = ItemMeta.class.getMethod("spigot");
            spigotClass = Class.forName("org.bukkit.inventory.meta.ItemMeta#Spigot");
            isUnbreakableMethod = spigotClass.getMethod("isUnbreakable");
            setUnbreakableMethod = spigotClass.getMethod("setUnbreakable", boolean.class);
        } catch (Throwable e) {}
    }
    
    @Override
    public void addAttributeModifier(ItemMeta itemMeta, String attribute, AttributeModifierWrapper wrapper) {
        //no-op (for now)
    }

    @Override
    public boolean isUnbreakable(ItemStack itemStack) {
        try {
            Object spigot = spigotMethod.invoke(itemStack.getItemMeta());
            return (boolean) isUnbreakableMethod.invoke(spigot);
        } catch (Throwable e) {} 
        return false;
    }

    @Override
    public void setUnbreakable(ItemMeta itemMeta, boolean unbreakable) {
        try {
            Object spigot = spigotMethod.invoke(itemMeta);
            setUnbreakableMethod.invoke(spigot, unbreakable);
        } catch (Throwable e) {}
    }

    @Override
    public Map<String, AttributeModifierWrapper> getAttributeModifiers(ItemStack itemStack) {
        return Map.of();
    }

    @Override
    public void setDamage(ItemStack itemStack, int damage) {
        itemStack.setDurability((short) damage);
    }

    @Override
    public int getDamage(ItemStack itemStack) {
        return itemStack.getDurability();
    }
}

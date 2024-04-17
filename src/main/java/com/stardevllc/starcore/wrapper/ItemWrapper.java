package com.stardevllc.starcore.wrapper;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public interface ItemWrapper {
    void addAttributeModifier(ItemMeta itemMeta, String attribute, AttributeModifierWrapper wrapper);
    boolean isUnbreakable(ItemStack itemStack);
    void setUnbreakable(ItemMeta itemMeta, boolean unbreakable);
    Map<String, AttributeModifierWrapper> getAttributeModifiers(ItemStack itemStack);
    void setDamage(ItemStack itemMeta, int damage);
    int getDamage(ItemStack itemStack);
}
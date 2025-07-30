package com.stardevllc.starcore.api.wrappers;

import org.bukkit.inventory.ItemStack;

import java.util.Map;

public interface ItemWrapper {
    void addAttributeModifier(ItemStack itemStack, String attribute, AttributeModifierWrapper wrapper);
    Map<String, AttributeModifierWrapper> getAttributeModifiers(ItemStack itemStack);
}
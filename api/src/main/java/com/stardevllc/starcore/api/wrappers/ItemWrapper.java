package com.stardevllc.starcore.api.wrappers;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public interface ItemWrapper {
    void addAttributeModifier(ItemMeta itemMeta, String attribute, AttributeModifierWrapper wrapper);
    Map<String, AttributeModifierWrapper> getAttributeModifiers(ItemStack itemStack);
}
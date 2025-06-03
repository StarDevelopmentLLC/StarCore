package com.stardevllc.starcore.v1_8_R1;

import com.stardevllc.starcore.api.wrappers.AttributeModifierWrapper;
import com.stardevllc.starcore.api.wrappers.ItemWrapper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class ItemWrapper_1_8_R1 implements ItemWrapper {
    
    @Override
    public void addAttributeModifier(ItemMeta itemMeta, String attribute, AttributeModifierWrapper wrapper) {
        //no-op (for now)
    }

    @Override
    public Map<String, AttributeModifierWrapper> getAttributeModifiers(ItemStack itemStack) {
        return Map.of();
    }
}
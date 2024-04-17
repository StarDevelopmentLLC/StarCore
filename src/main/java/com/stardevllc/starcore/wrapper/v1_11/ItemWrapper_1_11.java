package com.stardevllc.starcore.wrapper.v1_11;

import com.stardevllc.starcore.wrapper.AttributeModifierWrapper;
import com.stardevllc.starcore.wrapper.ItemWrapper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class ItemWrapper_1_11 implements ItemWrapper {
    @Override
    public void addAttributeModifier(ItemMeta itemMeta, String attribute, AttributeModifierWrapper wrapper) {
        //no-op (for now)
    }

    @Override
    public boolean isUnbreakable(ItemStack itemStack) {
        return itemStack.getItemMeta().isUnbreakable();
    }

    @Override
    public void setUnbreakable(ItemMeta itemMeta, boolean unbreakable) {
        itemMeta.setUnbreakable(unbreakable);
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

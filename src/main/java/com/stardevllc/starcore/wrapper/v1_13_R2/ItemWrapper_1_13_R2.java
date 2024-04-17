package com.stardevllc.starcore.wrapper.v1_13_R2;

import com.stardevllc.starcore.wrapper.AttributeModifierWrapper;
import com.stardevllc.starcore.wrapper.ItemWrapper;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class ItemWrapper_1_13_R2 implements ItemWrapper {
    @Override
    public void addAttributeModifier(ItemMeta itemMeta, String attribute, AttributeModifierWrapper wrapper) {
        itemMeta.addAttributeModifier(Attribute.valueOf(attribute.toUpperCase()), new AttributeModifier(wrapper.getUuid(), wrapper.getName(), wrapper.getAmount(), AttributeModifier.Operation.valueOf(wrapper.getOperation().toUpperCase()), wrapper.getSlot()));
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
        Map<String, AttributeModifierWrapper> modifiers = new HashMap<>();
        itemStack.getItemMeta().getAttributeModifiers().forEach((attribute, modifier) -> modifiers.put(attribute.name(), new AttributeModifierWrapper(modifier.getUniqueId(), modifier.getName(), modifier.getAmount(), modifier.getOperation().name(), modifier.getSlot())));
        return modifiers;
    }

    @Override
    public void setDamage(ItemStack itemMeta, int damage) {
        if (itemMeta instanceof Damageable damageable) {
            damageable.setDamage(damage);
        }
    }

    @Override
    public int getDamage(ItemStack itemStack) {
        if (itemStack.getItemMeta() instanceof Damageable damageable) {
            return damageable.getDamage();
        }
        
        return 0;
    }
}
package com.stardevllc.starcore.v1_8.itembuilder;

import com.stardevllc.starcore.api.itembuilder.ItemBuilder;
import com.stardevllc.starmclib.XMaterial;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.HashMap;
import java.util.Map;

public class EnchantedBookBuilder extends ItemBuilder<EnchantedBookBuilder, EnchantmentStorageMeta> {
    
    protected Map<Enchantment, Integer> storedEnchants = new HashMap<>();
    
    public EnchantedBookBuilder() {
        super(XMaterial.ENCHANTED_BOOK);
    }
    
    public EnchantedBookBuilder(ItemStack itemStack) {
        super(itemStack);
        
        EnchantmentStorageMeta itemMeta = (EnchantmentStorageMeta) itemStack.getItemMeta();
        if (itemMeta != null) {
            this.storedEnchants.putAll(itemMeta.getStoredEnchants());
        }
    }
    
    public EnchantedBookBuilder(EnchantedBookBuilder builder) {
        super(builder);
        this.storedEnchants.putAll(builder.storedEnchants);
    }
    
    public EnchantedBookBuilder(Enchantment enchantment, int level) {
        addStoredEnchant(enchantment, level);
    }
    
    public EnchantedBookBuilder addStoredEnchant(Enchantment enchantment, int level) {
        this.storedEnchants.put(enchantment, level);
        return this;
    }
    
    public EnchantedBookBuilder setStoredEnchants(Map<Enchantment, Integer> enchants) {
        this.storedEnchants.clear();
        this.storedEnchants.putAll(enchants);
        return this;
    }
    
    @Override
    protected EnchantmentStorageMeta createItemMeta() {
        EnchantmentStorageMeta itemMeta = super.createItemMeta();
        this.storedEnchants.forEach((enchant, level) -> itemMeta.addStoredEnchant(enchant, level, true));
        return itemMeta;
    }

    @Override
    public EnchantedBookBuilder clone() {
        return new EnchantedBookBuilder(this);
    }
}

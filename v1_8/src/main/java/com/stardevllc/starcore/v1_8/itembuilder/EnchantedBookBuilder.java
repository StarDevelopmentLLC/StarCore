package com.stardevllc.starcore.v1_8.itembuilder;

import com.stardevllc.starcore.api.itembuilder.ItemBuilder;
import com.stardevllc.starmclib.XMaterial;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.HashMap;
import java.util.Map;

public class EnchantedBookBuilder extends ItemBuilder {
    
    static {
        ItemBuilder.META_TO_BUILDERS.put(EnchantmentStorageMeta.class, EnchantedBookBuilder.class);
    }

    protected Map<Enchantment, Integer> storedEnchants = new HashMap<>();
    
    public EnchantedBookBuilder() {
        super(XMaterial.ENCHANTED_BOOK);
    }

    public EnchantedBookBuilder(Enchantment enchantment, int level) {
        addStoredEnchant(enchantment, level);
    }
    
    protected static EnchantedBookBuilder createFromItemStack(ItemStack itemStack) {
        EnchantedBookBuilder itemBuilder = new EnchantedBookBuilder();
        EnchantmentStorageMeta itemMeta = (EnchantmentStorageMeta) itemStack.getItemMeta();
        itemBuilder.setStoredEnchants(itemMeta.getStoredEnchants());
        return itemBuilder;
    }

    protected static EnchantedBookBuilder createFromConfig(ConfigurationSection section) {
        EnchantedBookBuilder builder = new EnchantedBookBuilder();
        ConfigurationSection storedEnchantsSection = section.getConfigurationSection("storedenchantments");
        if (storedEnchantsSection != null) {
            for (Object enchantName : storedEnchantsSection.getKeys(false)) {
                Enchantment enchantment = getMCWrappers().getEnchantWrapper().getEnchantmentByKey(enchantName.toString());
                int level = storedEnchantsSection.getInt(enchantName.toString());
                builder.addStoredEnchant(enchantment, level);
            }
        }
        return builder;
    }

    @Override
    public void saveToConfig(ConfigurationSection section) {
        super.saveToConfig(section);
        storedEnchants.forEach((enchant, level) -> section.set("storedenchantments." + getMCWrappers().getEnchantWrapper().getEnchantmentKey(enchant), level));
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
        EnchantmentStorageMeta itemMeta = (EnchantmentStorageMeta) super.createItemMeta();
        this.storedEnchants.forEach((enchant, level) -> itemMeta.addStoredEnchant(enchant, level, true));
        return itemMeta;
    }

    @Override
    public EnchantedBookBuilder clone() {
        EnchantedBookBuilder clone = (EnchantedBookBuilder) super.clone();
        clone.storedEnchants.putAll(this.storedEnchants);
        return clone;
    }
}

package com.stardevllc.starcore.api.wrappers;

import org.bukkit.enchantments.Enchantment;

public interface EnchantWrapper {
    Enchantment getEnchantmentByKey(String key);
    String getEnchantmentKey(Enchantment enchantment);
}

package com.stardevllc.starcore.wrapper;

import org.bukkit.enchantments.Enchantment;

public interface EnchantWrapper {
    Enchantment getEnchantmentByKey(String key);
    String getEnchantmentKey(Enchantment enchantment);
}

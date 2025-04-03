package com.stardevllc.starcore.base.wrappers;

import org.bukkit.enchantments.Enchantment;

public interface EnchantWrapper {
    Enchantment getEnchantmentByKey(String key);
    String getEnchantmentKey(Enchantment enchantment);
}

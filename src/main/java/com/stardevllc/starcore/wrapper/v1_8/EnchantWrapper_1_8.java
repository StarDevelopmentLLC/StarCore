package com.stardevllc.starcore.wrapper.v1_8;

import com.stardevllc.starcore.wrapper.EnchantWrapper;
import org.bukkit.enchantments.Enchantment;

public class EnchantWrapper_1_8 implements EnchantWrapper {
    @Override
    public Enchantment getEnchantmentByKey(String key) {
        return Enchantment.getByName(key);
    }

    @Override
    public String getEnchantmentKey(Enchantment enchantment) {
        return enchantment.getName();
    }
}
package com.stardevllc.starcore.v1_8;

import org.bukkit.enchantments.Enchantment;
import com.stardevllc.starcore.wrapper.EnchantWrapper;

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

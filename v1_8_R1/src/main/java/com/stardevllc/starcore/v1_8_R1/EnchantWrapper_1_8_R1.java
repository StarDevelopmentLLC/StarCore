package com.stardevllc.starcore.v1_8_R1;

import com.stardevllc.starcore.api.wrappers.EnchantWrapper;
import org.bukkit.enchantments.Enchantment;

public class EnchantWrapper_1_8_R1 implements EnchantWrapper {
    @Override
    public Enchantment getEnchantmentByKey(String key) {
        return Enchantment.getByName(key);
    }

    @Override
    public String getEnchantmentKey(Enchantment enchantment) {
        return enchantment.getName();
    }
}

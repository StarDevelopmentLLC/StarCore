package com.stardevllc.starcore.v1_13_2;

import com.stardevllc.starcore.api.wrappers.EnchantWrapper;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

public class EnchantWrapper_1_13_2 implements EnchantWrapper {
    @Override
    public Enchantment getEnchantmentByKey(String key) {
        return Enchantment.getByKey(NamespacedKey.minecraft(key));
    }

    @Override
    public String getEnchantmentKey(Enchantment enchantment) {
        return enchantment.getKey().getKey();
    }
}
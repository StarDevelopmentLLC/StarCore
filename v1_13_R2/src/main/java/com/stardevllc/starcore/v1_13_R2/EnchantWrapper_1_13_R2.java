package com.stardevllc.starcore.v1_13_R2;

import com.stardevllc.starcore.wrapper.EnchantWrapper;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

public class EnchantWrapper_1_13_R2 implements EnchantWrapper {
    @Override
    public Enchantment getEnchantmentByKey(String key) {
        return Enchantment.getByKey(NamespacedKey.minecraft(key));
    }

    @Override
    public String getEnchantmentKey(Enchantment enchantment) {
        return enchantment.getKey().getKey();
    }
}
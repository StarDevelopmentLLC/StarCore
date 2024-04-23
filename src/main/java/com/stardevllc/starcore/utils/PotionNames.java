package com.stardevllc.starcore.utils;

import com.stardevllc.starlib.helper.StringHelper;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

import static org.bukkit.potion.PotionEffectType.*;

public class PotionNames {
    public final Map<PotionEffectType, String> effectNames = new HashMap<>();
    private static final PotionNames instance = new PotionNames() {
        @Override
        public void setName(PotionEffectType potionEffectType, String name) {
            throw new RuntimeException("Cannot set the potion name using the default instance");
        }
    };
    
    public static PotionNames createInstance() {
        return new PotionNames();
    }
    
    public static PotionNames getInstance() {
        return instance;
    }

    private PotionNames() {
        //noinspection deprecation
        for (PotionEffectType effectType : values()) {
            if (effectType != null) {
                //noinspection deprecation
                effectNames.put(effectType, StringHelper.titlize(effectType.getName()));
            } 
        }
        
        effectNames.put(SLOW, "Slowness");
        effectNames.put(FAST_DIGGING, "Haste");
        effectNames.put(SLOW_DIGGING, "Miner's Fatigue");
        effectNames.put(INCREASE_DAMAGE, "Strength");
        effectNames.put(HEAL, "Instant Health");
        effectNames.put(HARM, "Instant Damage");
        effectNames.put(JUMP, "Jump Boost");
        effectNames.put(CONFUSION, "Nausia");
        effectNames.put(DAMAGE_RESISTANCE, "Resistance");
    }
    
    /**
     * Gets the default instance of the entity name
     * @param potionEffectType The potion effect type
     * @return The name using the default instance. This is just a wrapper method
     */
    public static String getDefaultName(PotionEffectType potionEffectType) {
        return getInstance().getName(potionEffectType);
    }
    
    public String getName(PotionEffectType potionEffectType) {
        return effectNames.get(potionEffectType);
    }
    
    public void setName(PotionEffectType potionEffectType, String name) {
        effectNames.put(potionEffectType, name);
    }
}

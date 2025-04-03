package com.stardevllc.starcore.utils;

import com.stardevllc.helper.StringHelper;
import com.stardevllc.starcore.base.NMSVersion;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

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

    @SuppressWarnings("JavaReflectionMemberAccess")
    private PotionNames() {
        //noinspection deprecation
        for (PotionEffectType effectType : PotionEffectType.values()) {
            if (effectType != null) {
                //noinspection deprecation
                effectNames.put(effectType, StringHelper.titlize(effectType.getName()));
            } 
        }
        
        if (NMSVersion.CURRENT_VERSION.ordinal() < NMSVersion.v1_20_R4.ordinal()) {
            Class<PotionEffectType> effectClass = PotionEffectType.class;

            try {
                effectNames.put((PotionEffectType) effectClass.getField("SLOW").get(null), "Slowness");
                effectNames.put((PotionEffectType) effectClass.getField("FAST_DIGGING").get(null), "Haste");
                effectNames.put((PotionEffectType) effectClass.getField("SLOW_DIGGING").get(null), "Miner's Fatigue");
                effectNames.put((PotionEffectType) effectClass.getField("INCREASE_DAMAGE").get(null), "Strength");
                effectNames.put((PotionEffectType) effectClass.getField("HEAL").get(null), "Instant Health");
                effectNames.put((PotionEffectType) effectClass.getField("HARM").get(null), "Instant Damage");
                effectNames.put((PotionEffectType) effectClass.getField("JUMP").get(null), "Jump Boost");
                effectNames.put((PotionEffectType) effectClass.getField("CONFUSION").get(null), "Nausia");
                effectNames.put((PotionEffectType) effectClass.getField("DAMAGE_RESISTANCE").get(null), "Resistance");
            } catch (Exception e) {
            }
        }
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

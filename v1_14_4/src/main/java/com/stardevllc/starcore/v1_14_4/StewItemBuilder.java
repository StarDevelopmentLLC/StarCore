package com.stardevllc.starcore.v1_14_4;

import com.stardevllc.starcore.api.XMaterial;
import com.stardevllc.starcore.base.itembuilder.ItemBuilder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class StewItemBuilder extends ItemBuilder {
    
    static {
        ItemBuilder.META_TO_BUILDERS.put(SuspiciousStewMeta.class, StewItemBuilder.class);
    }
    
    private List<PotionEffect> effects = new ArrayList<>();
    
    public StewItemBuilder() {
        super(XMaterial.SUSPICIOUS_STEW);
    }
    
    protected static StewItemBuilder createFromItemStack(ItemStack itemStack) {
        StewItemBuilder builder = new StewItemBuilder();
        SuspiciousStewMeta meta = (SuspiciousStewMeta) itemStack.getItemMeta();
        return builder.setEffects(meta.getCustomEffects());
    }

    protected static StewItemBuilder createFromConfig(ConfigurationSection section) {
        StewItemBuilder builder = new StewItemBuilder();
        ConfigurationSection effectsSection = section.getConfigurationSection("effects");
        if (effectsSection != null) {
            for (Object key : effectsSection.getKeys(false)) {
                builder.addEffect(effectsSection.getSerializable(key.toString(), PotionEffect.class));
            }
        }
        return builder;
    }

    @Override
    public void saveToConfig(ConfigurationSection section) {
        super.saveToConfig(section);
        for (int i = 0; i < effects.size(); i++) {
            section.set("effects." + i, effects.get(i));
        }
    }
    
    public StewItemBuilder addEffect(PotionEffect effect) {
        this.effects.add(effect);
        return this;
    }
    
    public StewItemBuilder setEffects(List<PotionEffect> effects) {
        this.effects.clear();
        this.effects.addAll(effects);
        return this;
    }

    @Override
    protected SuspiciousStewMeta createItemMeta() {
        SuspiciousStewMeta itemMeta = (SuspiciousStewMeta) super.createItemMeta();
        this.effects.forEach(effect -> itemMeta.addCustomEffect(effect, true));
        return itemMeta;
    }

    @Override
    public StewItemBuilder clone() {
        StewItemBuilder clone = (StewItemBuilder) super.clone();
        clone.effects.addAll(this.effects);
        return clone;
    }
}

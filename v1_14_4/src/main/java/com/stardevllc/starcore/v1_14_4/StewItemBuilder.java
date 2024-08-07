package com.stardevllc.starcore.v1_14_4;

import com.stardevllc.starcore.item.ItemBuilder;
import com.stardevllc.starcore.xseries.XMaterial;
import dev.dejvokep.boostedyaml.block.implementation.Section;
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

    protected static StewItemBuilder createFromConfig(Section section) {
        StewItemBuilder builder = new StewItemBuilder();
        Section effectsSection = section.getSection("effects");
        if (effectsSection != null) {
            for (Object key : effectsSection.getKeys()) {
                builder.addEffect(effectsSection.getAs(key.toString(), PotionEffect.class));
            }
        }
        return builder;
    }

    @Override
    public void saveToConfig(Section section) {
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

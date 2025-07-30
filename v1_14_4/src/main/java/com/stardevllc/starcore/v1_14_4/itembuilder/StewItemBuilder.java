package com.stardevllc.starcore.v1_14_4.itembuilder;

import com.stardevllc.starcore.api.itembuilder.ItemBuilder;
import com.stardevllc.starmclib.XMaterial;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class StewItemBuilder extends ItemBuilder<StewItemBuilder, SuspiciousStewMeta> {
    
    private List<PotionEffect> effects = new ArrayList<>();
    
    public StewItemBuilder() {
        super(XMaterial.SUSPICIOUS_STEW);
    }
    
    public StewItemBuilder(ItemStack itemStack) {
        super(itemStack);
        
        SuspiciousStewMeta itemMeta = (SuspiciousStewMeta) itemStack.getItemMeta();
        if (itemMeta != null) {
            this.effects.addAll(itemMeta.getCustomEffects());
        }
    }
    
    public StewItemBuilder(StewItemBuilder builder) {
        super(builder);
        this.effects.addAll(builder.effects);
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
        SuspiciousStewMeta itemMeta = super.createItemMeta();
        this.effects.forEach(effect -> itemMeta.addCustomEffect(effect, true));
        return itemMeta;
    }

    @Override
    public StewItemBuilder clone() {
        return new StewItemBuilder(this);
    }
}

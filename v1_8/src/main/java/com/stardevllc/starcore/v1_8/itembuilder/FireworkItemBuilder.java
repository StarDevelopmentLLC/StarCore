package com.stardevllc.starcore.v1_8.itembuilder;

import com.stardevllc.starcore.api.itembuilder.ItemBuilder;
import com.stardevllc.starmclib.XMaterial;
import org.bukkit.FireworkEffect;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.*;

public class FireworkItemBuilder extends ItemBuilder<FireworkItemBuilder, FireworkMeta> {
    
    private List<FireworkEffect> effects = new LinkedList<>();
    private int power;
    
    public FireworkItemBuilder() {
        super(XMaterial.FIREWORK_ROCKET);
    }
    
    public FireworkItemBuilder(ItemStack itemStack) {
        super(itemStack);
        
        FireworkMeta itemMeta = (FireworkMeta) itemStack.getItemMeta();
        if (itemMeta != null) {
            this.power = itemMeta.getPower();
            this.effects.addAll(itemMeta.getEffects());
        }
    }
    
    public FireworkItemBuilder(FireworkItemBuilder builder) {
        super(builder);
        this.effects.addAll(builder.effects);
        this.power = builder.power;
    }
    
    public FireworkItemBuilder(FireworkEffect effect, int power) {
        addEffect(effect).power(power);
    }
    
    public FireworkItemBuilder power(int power) {
        this.power = power;
        return this;
    }
    
    public FireworkItemBuilder addEffect(FireworkEffect effect) {
        this.effects.add(effect);
        return this;
    }
    
    public FireworkItemBuilder addEffects(Collection<FireworkEffect> effects) {
        this.effects.addAll(effects);
        return this;
    }
    
    public FireworkItemBuilder setEffects(Collection<FireworkEffect> effects) {
        this.effects.clear();
        this.effects.addAll(effects);
        return this;
    }

    @Override
    protected FireworkMeta createItemMeta() {
        FireworkMeta itemMeta = super.createItemMeta();
        itemMeta.addEffects(this.effects);
        itemMeta.setPower(this.power);
        return itemMeta;
    }

    @Override
    public FireworkItemBuilder clone() {
        return new FireworkItemBuilder(this);
    }
}

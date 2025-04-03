package com.stardevllc.starcore.base.itembuilder.material;

import com.stardevllc.starcore.base.itembuilder.ItemBuilder;
import com.stardevllc.starcore.base.itembuilder.XMaterial;
import org.bukkit.FireworkEffect;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.*;

public class FireworkItemBuilder extends ItemBuilder {
    
    static {
        ItemBuilder.META_TO_BUILDERS.put(FireworkMeta.class, FireworkItemBuilder.class);
    }
    
    private List<FireworkEffect> effects = new LinkedList<>();
    private int power;
    
    public FireworkItemBuilder() {
        super(XMaterial.FIREWORK_ROCKET);
    }

    public FireworkItemBuilder(FireworkEffect effect, int power) {
        addEffect(effect).power(power);
    }
    
    protected static FireworkItemBuilder createFromItemStack(ItemStack itemStack) {
        FireworkItemBuilder itemBuilder = new FireworkItemBuilder();
        FireworkMeta meta = (FireworkMeta) itemStack.getItemMeta();
        itemBuilder.power(meta.getPower()).setEffects(meta.getEffects());
        return itemBuilder;
    }

    protected static FireworkItemBuilder createFromConfig(ConfigurationSection section) {
        FireworkItemBuilder builder = new FireworkItemBuilder();
        builder.power(section.getInt("power"));
        ConfigurationSection effectsSection = section.getConfigurationSection("effects");
        if (effectsSection != null) {
            for (Object key : effectsSection.getKeys(false)) {
                FireworkEffect object = (FireworkEffect) effectsSection.get(key.toString(), FireworkEffect.class);
                builder.addEffect(object);
            }
        }
        return builder;
    }

    @Override
    public void saveToConfig(ConfigurationSection section) {
        super.saveToConfig(section);
        section.set("power", this.power);
        for (int i = 0; i < effects.size(); i++) {
            section.set("effects." + i, effects.get(i));
        }
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
        FireworkMeta itemMeta = (FireworkMeta) super.createItemMeta();
        itemMeta.addEffects(this.effects);
        itemMeta.setPower(this.power);
        return itemMeta;
    }

    @Override
    public FireworkItemBuilder clone() {
        FireworkItemBuilder clone = (FireworkItemBuilder) super.clone();
        clone.power = this.power;
        clone.effects.addAll(this.effects);
        return clone;
    }
}

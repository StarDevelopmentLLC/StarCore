package com.stardevllc.starcore.v1_8.itembuilder;

import com.stardevllc.starcore.api.itembuilder.ItemBuilder;
import com.stardevllc.starmclib.XMaterial;
import org.bukkit.FireworkEffect;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;

public class FireworkStarBuilder extends ItemBuilder {
    
    static {
        ItemBuilder.META_TO_BUILDERS.put(FireworkEffectMeta.class, FireworkStarBuilder.class);
    }
    
    private FireworkEffect effect;
    
    public FireworkStarBuilder() {
        super(XMaterial.FIREWORK_STAR);
    }

    public FireworkStarBuilder(FireworkEffect effect) {
        effect(effect);
    }

    protected static FireworkStarBuilder createFromItemStack(ItemStack itemStack) {
        FireworkStarBuilder itemBuilder = new FireworkStarBuilder();
        FireworkEffectMeta meta = (FireworkEffectMeta) itemStack.getItemMeta();
        itemBuilder.effect(meta.getEffect());
        return itemBuilder;
    }

    protected static FireworkStarBuilder createFromConfig(ConfigurationSection section) {
        FireworkStarBuilder builder = new FireworkStarBuilder();
        builder.effect((FireworkEffect) section.get("effect", FireworkEffect.class));
        return builder;
    }

    @Override
    public void saveToConfig(ConfigurationSection section) {
        super.saveToConfig(section);
        section.set("effect", this.effect);
    }
    
    public FireworkStarBuilder effect(FireworkEffect effect) {
        this.effect = effect;
        return this;
    }

    @Override
    protected FireworkEffectMeta createItemMeta() {
        FireworkEffectMeta itemMeta = (FireworkEffectMeta) super.createItemMeta();
        itemMeta.setEffect(this.effect);
        return itemMeta;
    }

    @Override
    public FireworkStarBuilder clone() {
        FireworkStarBuilder clone = (FireworkStarBuilder) super.clone();
        clone.effect = this.effect;
        return clone;
    }
}

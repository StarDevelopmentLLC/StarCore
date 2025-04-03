package com.stardevllc.starcore.v1_21;

import com.stardevllc.starcore.base.itembuilder.ItemBuilder;
import com.stardevllc.starcore.base.itembuilder.XMaterial;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.OminousBottleMeta;

public class OminousBottleBuilder extends ItemBuilder {

    static {
        ItemBuilder.META_TO_BUILDERS.put(OminousBottleMeta.class, OminousBottleBuilder.class);
    }
    
    protected int amplifier;
    
    public OminousBottleBuilder() {
    }

    public OminousBottleBuilder(XMaterial material) {
        super(material);
    }

    protected static OminousBottleBuilder createFromItemStack(ItemStack itemStack) {
        OminousBottleBuilder itemBuilder = new OminousBottleBuilder(XMaterial.matchXMaterial(itemStack));
        OminousBottleMeta itemMeta = (OminousBottleMeta) itemStack.getItemMeta();
        itemBuilder.amplifier(itemMeta.getAmplifier());
        return itemBuilder;
    }

    protected static OminousBottleBuilder createFromConfig(ConfigurationSection section) {
        OminousBottleBuilder builder = new OminousBottleBuilder();
        builder.amplifier(section.getInt("amplifier"));
        return builder;
    }
    
    public OminousBottleBuilder amplifier(int amplifier) {
        this.amplifier = amplifier;
        return this;
    }

    @Override
    public void saveToConfig(ConfigurationSection section) {
        super.saveToConfig(section);
        section.set("amplifier", this.amplifier);
    }

    @Override
    protected OminousBottleMeta createItemMeta() {
        OminousBottleMeta itemMeta = (OminousBottleMeta) super.createItemMeta();
        itemMeta.setAmplifier(this.amplifier);
        return itemMeta;
    }
}

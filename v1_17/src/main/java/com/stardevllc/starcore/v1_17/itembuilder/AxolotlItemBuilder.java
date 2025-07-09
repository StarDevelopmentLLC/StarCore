package com.stardevllc.starcore.v1_17.itembuilder;

import com.stardevllc.starcore.api.itembuilder.ItemBuilder;
import com.stardevllc.starmclib.XMaterial;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Axolotl;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.AxolotlBucketMeta;

public class AxolotlItemBuilder extends ItemBuilder {
    
    static {
        ItemBuilder.META_TO_BUILDERS.put(AxolotlBucketMeta.class, AxolotlItemBuilder.class);
    }
    
    private Axolotl.Variant variant;
    
    public AxolotlItemBuilder() {
        super(XMaterial.AXOLOTL_BUCKET);
    }

    public AxolotlItemBuilder(Axolotl.Variant variant) {
        this();
        variant(variant);
    }
    
    protected static AxolotlItemBuilder createFromItemStack(ItemStack itemStack) {
        AxolotlItemBuilder itemBuilder = new AxolotlItemBuilder();
        AxolotlBucketMeta itemMeta = (AxolotlBucketMeta) itemStack.getItemMeta();
        itemBuilder.variant(itemMeta.getVariant());
        return itemBuilder;
    }
    
    protected static AxolotlItemBuilder createFromConfig(ConfigurationSection section) {
        AxolotlItemBuilder builder = new AxolotlItemBuilder();
        builder.variant(Axolotl.Variant.valueOf(section.getString("variant")));
        return builder;
    }

    @Override
    public void saveToConfig(ConfigurationSection section) {
        super.saveToConfig(section);
        section.set("variant", variant.name());
    }

    public AxolotlItemBuilder variant(Axolotl.Variant variant) {
        this.variant = variant;
        return this;
    }

    @Override
    protected AxolotlBucketMeta createItemMeta() {
        AxolotlBucketMeta meta = (AxolotlBucketMeta) super.createItemMeta();
        meta.setVariant(this.variant);
        return meta;
    }

    @Override
    public AxolotlItemBuilder clone() {
        AxolotlItemBuilder clone = (AxolotlItemBuilder) super.clone();
        clone.variant = this.variant;
        return clone;
    }
}

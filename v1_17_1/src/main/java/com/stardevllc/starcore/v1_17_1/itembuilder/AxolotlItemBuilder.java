package com.stardevllc.starcore.v1_17_1.itembuilder;

import com.stardevllc.starcore.api.itembuilder.ItemBuilder;
import com.stardevllc.starmclib.XMaterial;
import org.bukkit.entity.Axolotl;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.AxolotlBucketMeta;

public class AxolotlItemBuilder extends ItemBuilder<AxolotlItemBuilder, AxolotlBucketMeta> {
    
    private Axolotl.Variant variant;
    
    public AxolotlItemBuilder() {
        super(XMaterial.AXOLOTL_BUCKET);
    }
    
    public AxolotlItemBuilder(ItemStack itemStack) {
        super(itemStack);
        
        AxolotlBucketMeta itemMeta = (AxolotlBucketMeta) itemStack.getItemMeta();
        if (itemMeta != null) {
            this.variant = itemMeta.getVariant();
        }
    }
    
    public AxolotlItemBuilder(AxolotlItemBuilder builder) {
        super(builder);
        this.variant = builder.variant;
    }
    
    public AxolotlItemBuilder(Axolotl.Variant variant) {
        this();
        variant(variant);
    }

    public AxolotlItemBuilder variant(Axolotl.Variant variant) {
        this.variant = variant;
        return this;
    }

    @Override
    protected AxolotlBucketMeta createItemMeta() {
        AxolotlBucketMeta meta = super.createItemMeta();
        meta.setVariant(this.variant);
        return meta;
    }

    @Override
    public AxolotlItemBuilder clone() {
        return new AxolotlItemBuilder(this);
    }
}

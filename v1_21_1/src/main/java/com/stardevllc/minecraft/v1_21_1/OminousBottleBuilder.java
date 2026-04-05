package com.stardevllc.minecraft.v1_21_1;

import com.stardevllc.minecraft.itembuilder.ItemBuilder;
import com.stardevllc.minecraft.smaterial.SMaterial;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.OminousBottleMeta;

public class OminousBottleBuilder extends ItemBuilder<OminousBottleBuilder, OminousBottleMeta> {

    protected int amplifier;
    
    public OminousBottleBuilder() {
        super(SMaterial.OMINOUS_BOTTLE);
    }
    
    public OminousBottleBuilder(ItemStack itemStack) {
        super(itemStack);
        
        OminousBottleMeta itemMeta = (OminousBottleMeta) itemStack.getItemMeta();
        if (itemMeta != null) {
            this.amplifier = itemMeta.getAmplifier();
        }
    }
    
    public OminousBottleBuilder(OminousBottleBuilder builder) {
        super(builder);
        this.amplifier = builder.amplifier;
    }
    
    public OminousBottleBuilder amplifier(int amplifier) {
        this.amplifier = amplifier;
        return this;
    }

    @Override
    protected OminousBottleMeta createItemMeta() {
        OminousBottleMeta itemMeta = super.createItemMeta();
        itemMeta.setAmplifier(this.amplifier);
        return itemMeta;
    }
    
    @Override
    public OminousBottleBuilder clone() {
        return new OminousBottleBuilder(this);
    }
}

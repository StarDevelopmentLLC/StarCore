package com.stardevllc.minecraft.v1_8;

import com.stardevllc.minecraft.itembuilder.ItemBuilder;
import com.stardevllc.minecraft.smaterial.SMaterial;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullItemBuilder extends ItemBuilder<SkullItemBuilder, SkullMeta> {
    
    private String owner;
    
    public SkullItemBuilder() {
        super(SMaterial.PLAYER_HEAD);
    }
    
    public SkullItemBuilder(SkullItemBuilder builder) {
        super(builder);
        this.owner = builder.owner;
    }
    
    public SkullItemBuilder(ItemStack itemStack) {
        super(itemStack);
        
        SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();
        if (itemMeta != null) {
            this.owner = itemMeta.getOwner();
        }
    }
    
    public SkullItemBuilder owner(String owner) {
        this.owner = owner;
        return this;
    }

    @Override
    protected SkullMeta createItemMeta() {
        SkullMeta itemMeta = super.createItemMeta();
        if (this.owner != null) {
            itemMeta.setOwner(this.owner);
        }
        
        return itemMeta;
    }

    @Override
    public SkullItemBuilder clone() {
        return new SkullItemBuilder(this);
    }
}

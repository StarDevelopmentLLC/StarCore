package com.stardevllc.starcore.api.itembuilder;

import com.stardevllc.starmclib.XMaterial;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BasicItemBuilder extends ItemBuilder<BasicItemBuilder, ItemMeta> {
    
    public BasicItemBuilder() {
    }
    
    public BasicItemBuilder(ItemStack itemStack) {
        super(itemStack);
    }
    
    public BasicItemBuilder(BasicItemBuilder builder) {
        super(builder);
    }
    
    public BasicItemBuilder(XMaterial material) {
        super(material);
    }
    
    @Override
    public BasicItemBuilder clone() {
        return new BasicItemBuilder(this);
    }
}
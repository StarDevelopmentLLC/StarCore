package com.stardevllc.starcore.v1_16_1.itembuilder;

import com.stardevllc.starcore.api.itembuilder.ItemBuilder;
import com.stardevllc.starmclib.XMaterial;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

public class CompassItemBuilder extends ItemBuilder<CompassItemBuilder, CompassMeta> {
    
    private Location lodestone;
    private boolean tracked;
    
    public CompassItemBuilder() {
        super(XMaterial.COMPASS);
    }
    
    public CompassItemBuilder(ItemStack itemStack) {
        super(itemStack);
        
        CompassMeta itemMeta = (CompassMeta) itemStack.getItemMeta();
        if (itemMeta != null) {
            this.lodestone = itemMeta.getLodestone();
            this.tracked = itemMeta.isLodestoneTracked();
        }
    }
    
    public CompassItemBuilder(CompassItemBuilder builder) {
        super(builder);
        this.lodestone = builder.lodestone;
        this.tracked = builder.tracked;
    }
    
    public CompassItemBuilder(Location lodestone) {
        this();
        lodestone(lodestone);
    }

    public CompassItemBuilder(Location lodestone, boolean tracked) {
        this();
        lodestone(lodestone).tracked(tracked);
    }
    
    public CompassItemBuilder lodestone(Location location) {
        this.lodestone = location;
        return this;
    }
    
    public CompassItemBuilder tracked(boolean tracked) {
        this.tracked = tracked;
        return this;
    }

    @Override
    protected CompassMeta createItemMeta() {
        CompassMeta itemMeta = super.createItemMeta();
        itemMeta.setLodestone(this.lodestone);
        itemMeta.setLodestoneTracked(this.tracked);
        return itemMeta;
    }

    @Override
    public CompassItemBuilder clone() {
        return new CompassItemBuilder(this);
    }
}

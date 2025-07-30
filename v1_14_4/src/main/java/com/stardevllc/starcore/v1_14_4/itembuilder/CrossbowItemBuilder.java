package com.stardevllc.starcore.v1_14_4.itembuilder;

import com.stardevllc.starcore.api.itembuilder.ItemBuilder;
import com.stardevllc.starmclib.XMaterial;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;

import java.util.LinkedList;
import java.util.List;

public class CrossbowItemBuilder extends ItemBuilder<CrossbowItemBuilder, CrossbowMeta> {
    
    private List<ItemStack> projectiles = new LinkedList<>();
    
    public CrossbowItemBuilder() {
        super(XMaterial.CROSSBOW);
    }
    
    public CrossbowItemBuilder(CrossbowItemBuilder builder) {
        super(builder);
        this.projectiles.addAll(builder.projectiles);
    }
    
    public CrossbowItemBuilder(ItemStack itemStack) {
        super(itemStack);
        
        CrossbowMeta itemMeta = (CrossbowMeta) itemStack.getItemMeta();
        if (itemMeta != null) {
            this.projectiles.addAll(itemMeta.getChargedProjectiles());
        }
    }
    
    public CrossbowItemBuilder addProjectile(ItemStack projectile) {
        this.projectiles.add(projectile);
        return this;
    }
    
    public CrossbowItemBuilder setProjectiles(List<ItemStack> projectiles) {
        this.projectiles.clear();
        this.projectiles.addAll(projectiles);
        return this;
    }

    @Override
    protected CrossbowMeta createItemMeta() {
        CrossbowMeta itemMeta = super.createItemMeta();
        itemMeta.setChargedProjectiles(this.projectiles);
        return itemMeta;
    }

    @Override
    public CrossbowItemBuilder clone() {
        return new CrossbowItemBuilder(this);
    }
}

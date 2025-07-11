package com.stardevllc.starcore.v1_14_4.itembuilder;

import com.stardevllc.starcore.api.itembuilder.ItemBuilder;
import com.stardevllc.starmclib.XMaterial;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;

import java.util.LinkedList;
import java.util.List;

public class CrossbowItemBuilder extends ItemBuilder {
    
    static {
        ItemBuilder.META_TO_BUILDERS.put(CrossbowMeta.class, CrossbowItemBuilder.class);
    }
    
    private List<ItemStack> projectiles = new LinkedList<>();
    
    public CrossbowItemBuilder() {
        super(XMaterial.CROSSBOW);
    }

    public CrossbowItemBuilder(ItemStack projectile) {
        addProjectile(projectile);
    }
    
    protected static CrossbowItemBuilder createFromItemStack(ItemStack itemStack) {
        CrossbowItemBuilder itemBuilder = new CrossbowItemBuilder();
        CrossbowMeta crossbowMeta = (CrossbowMeta) itemStack.getItemMeta();
        itemBuilder.setProjectiles(crossbowMeta.getChargedProjectiles());
        return itemBuilder;
    }

    protected static CrossbowItemBuilder createFromConfig(ConfigurationSection section) {
        CrossbowItemBuilder builder = new CrossbowItemBuilder();
        ConfigurationSection projectilesSection = section.getConfigurationSection("projectiles");
        if (projectilesSection != null) {
            for (String key : projectilesSection.getKeys(false)) {
                builder.addProjectile(projectilesSection.getSerializable(key, ItemStack.class));
            }
        }
        return builder;
    }

    @Override
    public void saveToConfig(ConfigurationSection section) {
        super.saveToConfig(section);
        for (int i = 0; i < projectiles.size(); i++) {
            section.set("projectiles." + i, projectiles.get(i));   
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
        CrossbowMeta itemMeta = (CrossbowMeta) super.createItemMeta();
        itemMeta.setChargedProjectiles(this.projectiles);
        return itemMeta;
    }

    @Override
    public CrossbowItemBuilder clone() {
        CrossbowItemBuilder clone = (CrossbowItemBuilder) super.clone();
        clone.projectiles.addAll(this.projectiles);
        return clone;
    }
}

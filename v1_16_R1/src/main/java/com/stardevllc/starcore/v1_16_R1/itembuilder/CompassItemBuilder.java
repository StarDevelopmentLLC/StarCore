package com.stardevllc.starcore.v1_16_R1.itembuilder;

import com.stardevllc.starcore.api.itembuilder.ItemBuilder;
import com.stardevllc.starmclib.XMaterial;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

public class CompassItemBuilder extends ItemBuilder {
    
    static {
        ItemBuilder.META_TO_BUILDERS.put(CompassMeta.class, CompassItemBuilder.class);
    }
    
    private Location lodestone;
    private boolean tracked;
    
    public CompassItemBuilder() {
        super(XMaterial.COMPASS);
    }

    public CompassItemBuilder(Location lodestone) {
        this();
        lodestone(lodestone);
    }

    public CompassItemBuilder(Location lodestone, boolean tracked) {
        this();
        lodestone(lodestone).tracked(tracked);
    }
    
    protected static CompassItemBuilder createFromItemStack(ItemStack itemStack) {
        CompassItemBuilder itemBuilder = new CompassItemBuilder();
        CompassMeta itemMeta = (CompassMeta) itemStack.getItemMeta();
        itemBuilder.lodestone(itemMeta.getLodestone().clone()).tracked(itemMeta.isLodestoneTracked());
        return itemBuilder;
    }

    protected static CompassItemBuilder createFromConfig(ConfigurationSection section) {
        CompassItemBuilder builder = new CompassItemBuilder();
        builder.tracked(section.getBoolean("tracked"));
        if (section.contains("lodestone")) {
            World world = Bukkit.getWorld(section.getString("lodestone.world"));
            int x = section.getInt("lodestone.x");
            int y = section.getInt("lodestone.y");
            int z = section.getInt("lodestone.z");
            builder.lodestone(new Location(world, x, y, z));
        }
        return builder;
    }

    @Override
    public void saveToConfig(ConfigurationSection section) {
        super.saveToConfig(section);
        section.set("tracked", this.tracked);
        if (this.lodestone != null) {
            section.set("lodestone.x", this.lodestone.getBlockX());
            section.set("lodestone.y", this.lodestone.getBlockY());
            section.set("lodestone.z", this.lodestone.getBlockZ());
            section.set("lodestone.world", this.lodestone.getWorld().getName());
        }
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
        CompassMeta itemMeta = (CompassMeta) super.createItemMeta();
        itemMeta.setLodestone(this.lodestone);
        itemMeta.setLodestoneTracked(this.tracked);
        return itemMeta;
    }

    @Override
    public CompassItemBuilder clone() {
        CompassItemBuilder clone = (CompassItemBuilder) super.clone();
        if (this.lodestone != null) {
            clone.lodestone = this.lodestone.clone();
        }
        clone.tracked = this.tracked;
        return clone;
    }
}

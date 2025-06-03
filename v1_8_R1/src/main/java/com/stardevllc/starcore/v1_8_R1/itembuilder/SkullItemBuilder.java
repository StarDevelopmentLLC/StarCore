package com.stardevllc.starcore.v1_8_R1.itembuilder;

import com.stardevllc.starcore.api.XMaterial;
import com.stardevllc.starcore.api.itembuilder.ItemBuilder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullItemBuilder extends ItemBuilder {
    
    static {
        ItemBuilder.META_TO_BUILDERS.put(SkullMeta.class, SkullItemBuilder.class);
    }
    
    private String owner;
    
    public SkullItemBuilder() {
        super(XMaterial.PLAYER_HEAD);
    }
    
    protected static SkullItemBuilder createFromItemStack(ItemStack itemStack) {
        SkullItemBuilder builder = new SkullItemBuilder();
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        builder.owner(meta.getOwner()); //getOwner deprecated in 1.12.2
        return builder;
    }

    protected static SkullItemBuilder createFromConfig(ConfigurationSection section) {
        SkullItemBuilder builder = new SkullItemBuilder();
        if (section.contains("owner")) {
            builder.owner(section.getString("owner"));
        }
        
        return builder;
    }

    @Override
    public void saveToConfig(ConfigurationSection section) {
        super.saveToConfig(section);
        if (owner != null) {
            section.set("owner", owner);
        }
    }
    
    public SkullItemBuilder owner(String owner) {
        this.owner = owner;
        return this;
    }
    
    @Override
    public SkullItemBuilder addEnchant(Enchantment enchantment, int level) {
        super.addEnchant(enchantment, level);
        return this;
    }

    @Override
    public SkullItemBuilder addItemFlags(ItemFlag... itemFlags) {
        super.addItemFlags(itemFlags);
        return this;
    }

    @Override
    protected SkullMeta createItemMeta() {
        SkullMeta itemMeta = (SkullMeta) super.createItemMeta();
        if (this.owner != null) {
            itemMeta.setOwner(this.owner);
        }
        
        return itemMeta;
    }

    @Override
    public SkullItemBuilder clone() {
        SkullItemBuilder clone = (SkullItemBuilder) super.clone();
        clone.owner = this.owner;
        return clone;
    }
}

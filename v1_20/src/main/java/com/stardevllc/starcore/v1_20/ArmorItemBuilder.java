package com.stardevllc.starcore.v1_20;

import com.stardevllc.starcore.api.XMaterial;
import com.stardevllc.starcore.base.itembuilder.ItemBuilder;
import com.stardevllc.starcore.base.itembuilder.enums.ArmorMaterial;
import com.stardevllc.starcore.base.itembuilder.enums.ArmorSlot;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

public class ArmorItemBuilder extends ItemBuilder {
    
    static {
        ItemBuilder.META_TO_BUILDERS.put(ArmorMeta.class, ArmorItemBuilder.class);
    }

    protected static ArmorItemBuilder createFromItemStack(ItemStack itemStack) {
        ArmorItemBuilder itemBuilder = new ArmorItemBuilder(XMaterial.matchXMaterial(itemStack));
        ArmorMeta itemMeta = (ArmorMeta) itemStack.getItemMeta();
        itemBuilder.trim(itemMeta.getTrim());
        if (itemMeta instanceof LeatherArmorMeta leatherArmor) {
            itemBuilder.color(leatherArmor.getColor());
        }
        return itemBuilder;
    }
    
    protected static ArmorItemBuilder createFromConfig(ConfigurationSection section) {
        ArmorItemBuilder builder = new ArmorItemBuilder();
        TrimMaterial tm = Bukkit.getRegistry(TrimMaterial.class).get(NamespacedKey.fromString(section.getString("trim.material")));
        TrimPattern tp = Bukkit.getRegistry(TrimPattern.class).get(NamespacedKey.fromString(section.getString("trim.pattern")));
        builder.trim(new ArmorTrim(tm, tp));
        return builder;
    }
    
    protected ArmorTrim trim;
    protected Color color;
    
    public ArmorItemBuilder(XMaterial material) {
        super(material);
    }

    public ArmorItemBuilder(ArmorMaterial material, ArmorSlot slot) {
        super(XMaterial.valueOf(material.name() + "_" + slot.name()));
    }
    
    protected ArmorItemBuilder() {}

    @Override
    public void saveToConfig(ConfigurationSection section) {
        super.saveToConfig(section);
        section.set("trim.material", trim.getMaterial().getKey().toString());
        section.set("trim.pattern", trim.getPattern().getKey().toString());
        if (color != null) {
            section.set("color", color.asRGB());
        }
    }

    public ArmorItemBuilder trim(ArmorTrim trim) {
        this.trim = trim;
        return this;
    }
    
    public ArmorItemBuilder color(Color color) {
        this.color = color;
        return this;
    }

    @Override
    protected ArmorMeta createItemMeta() {
        ArmorMeta itemMeta = (ArmorMeta) super.createItemMeta();
        itemMeta.setTrim(this.trim);
        if (itemMeta instanceof LeatherArmorMeta colorableArmorMeta) {
            colorableArmorMeta.setColor(this.color);
        }
        return itemMeta;
    }

    @Override
    public ArmorItemBuilder clone() {
        ArmorItemBuilder clone = (ArmorItemBuilder) super.clone();
        clone.trim = this.trim;
        clone.color = this.color;
        return clone;
    }
}

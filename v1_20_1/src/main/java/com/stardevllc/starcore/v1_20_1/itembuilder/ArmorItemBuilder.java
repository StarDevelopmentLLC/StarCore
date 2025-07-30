package com.stardevllc.starcore.v1_20_1.itembuilder;

import com.stardevllc.starcore.api.itembuilder.ItemBuilder;
import com.stardevllc.starcore.api.itembuilder.enums.ArmorMaterial;
import com.stardevllc.starcore.api.itembuilder.enums.ArmorSlot;
import com.stardevllc.starmclib.XMaterial;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;

public class ArmorItemBuilder extends ItemBuilder<ArmorItemBuilder, ArmorMeta> {
    protected ArmorTrim trim;
    protected Color color;
    
    public ArmorItemBuilder(XMaterial material) {
        super(material);
    }
    
    public ArmorItemBuilder(ItemStack itemStack) {
        super(itemStack);
        
        ArmorMeta itemMeta = (ArmorMeta) itemStack.getItemMeta();
        if (itemMeta != null) {
            this.trim = itemMeta.getTrim();
            if (itemMeta instanceof LeatherArmorMeta colorableArmorMeta) {
                this.color = colorableArmorMeta.getColor();
            }
        }
    }
    
    public ArmorItemBuilder(ArmorMaterial material, ArmorSlot slot) {
        super(XMaterial.valueOf(material.name() + "_" + slot.name()));
    }
    
    public ArmorItemBuilder(ArmorItemBuilder builder) {
        super(builder);
        this.trim = builder.trim;
        this.color = builder.color;
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
        ArmorMeta itemMeta = super.createItemMeta();
        itemMeta.setTrim(this.trim);
        if (itemMeta instanceof LeatherArmorMeta colorableArmorMeta) {
            colorableArmorMeta.setColor(this.color);
        }
        return itemMeta;
    }

    @Override
    public ArmorItemBuilder clone() {
        return new ArmorItemBuilder(this);
    }
}

package com.stardevllc.minecraft.v1_8;

import com.stardevllc.minecraft.itembuilder.ItemBuilder;
import com.stardevllc.minecraft.smaterial.ArmorSlot;
import com.stardevllc.minecraft.smaterial.SMaterial;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Map;

public class LeatherArmorBuilder extends ItemBuilder<LeatherArmorBuilder, LeatherArmorMeta> {
    
    private Color color;
    
    public LeatherArmorBuilder() {
    }
    
    public LeatherArmorBuilder(LeatherArmorBuilder builder) {
        super(builder);
        this.color = builder.color;
    }
    
    public LeatherArmorBuilder(ItemStack itemStack) {
        super(itemStack);
        
        LeatherArmorMeta itemMeta = (LeatherArmorMeta) itemStack.getItemMeta();
        if (itemMeta != null) {
            this.color = itemMeta.getColor();
        }
    }
    
    public LeatherArmorBuilder(ArmorSlot slot) {
        super(SMaterial.valueOf("LEATHER_" + slot.name()));
    }
    
    public LeatherArmorBuilder(Map<String, Object> serialized) {
        super(serialized);
        this.color = (Color) serialized.get("color");
    }
    
    public LeatherArmorBuilder slot(ArmorSlot slot) {
        this.material = SMaterial.valueOf("LEATHER_" + slot.name());
        return this;
    }
    
    public LeatherArmorBuilder color(Color color) {
        this.color = color;
        return this;
    }
    
    @Override
    protected LeatherArmorMeta createItemMeta() {
        LeatherArmorMeta itemMeta = super.createItemMeta();
        if (this.color != null) {
            itemMeta.setColor(this.color);
        }
        return itemMeta;
    }
    
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> serialized = super.serialize();
        serialized.put("color", this.color);
        return serialized;
    }
    
    @Override
    public LeatherArmorBuilder clone() {
        return new LeatherArmorBuilder(this);
    }
}

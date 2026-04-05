package com.stardevllc.minecraft.v1_14_4;

import com.stardevllc.minecraft.itembuilder.ItemBuilder;
import com.stardevllc.minecraft.smaterial.SMaterial;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockDataMeta;

public class BlockDataItemBuilder extends ItemBuilder<BlockDataItemBuilder, BlockDataMeta> {
    
    private BlockData blockData;
    
    public BlockDataItemBuilder() {
    }
    
    public BlockDataItemBuilder(BlockDataItemBuilder builder) {
        super(builder);
        this.blockData = builder.blockData;
    }
    
    public BlockDataItemBuilder(ItemStack itemStack) {
        super(itemStack);
        
        if (itemStack.getItemMeta() instanceof BlockDataMeta itemMeta) {
            this.blockData = itemMeta.getBlockData(this.material.parseMaterial());
        }
    }
    
    public BlockDataItemBuilder(SMaterial material) {
        super(material);
    }
    
    @Override
    public BlockDataItemBuilder clone() {
        return new BlockDataItemBuilder(this);
    }
}

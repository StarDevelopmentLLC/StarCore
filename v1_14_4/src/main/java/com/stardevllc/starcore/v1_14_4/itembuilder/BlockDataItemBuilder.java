package com.stardevllc.starcore.v1_14_4.itembuilder;

import com.stardevllc.starcore.api.itembuilder.ItemBuilder;
import com.stardevllc.starmclib.XMaterial;
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
    
    public BlockDataItemBuilder(XMaterial material) {
        super(material);
    }
    
    @Override
    public BlockDataItemBuilder clone() {
        return new BlockDataItemBuilder(this);
    }
}

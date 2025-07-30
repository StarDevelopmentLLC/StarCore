package com.stardevllc.starcore.v1_8_3;

import com.stardevllc.starcore.api.itembuilder.ItemBuilder;
import com.stardevllc.starmclib.XMaterial;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class BlockStateItemBuilder extends ItemBuilder<BlockStateItemBuilder, BlockStateMeta> {
    
    private BlockState blockState;
    
    public BlockStateItemBuilder() {
    }
    
    public BlockStateItemBuilder(BlockStateItemBuilder builder) {
        super(builder);
        this.blockState = builder.blockState;
    }
    
    public BlockStateItemBuilder(ItemStack itemStack) {
        super(itemStack);
        
        if (itemStack.getItemMeta() instanceof BlockStateMeta itemMeta) {
            this.blockState = itemMeta.getBlockState();
        }
    }
    
    public BlockStateItemBuilder(XMaterial material) {
        super(material);
    }
    
    public BlockStateItemBuilder blockState(BlockState blockState) {
        this.blockState = blockState;
        return this;
    }
    
    @Override
    protected BlockStateMeta createItemMeta() {
        BlockStateMeta itemMeta = super.createItemMeta();
        itemMeta.setBlockState(this.blockState);
        return itemMeta;
    }
    
    @Override
    public BlockStateItemBuilder clone() {
        return new BlockStateItemBuilder(this);
    }
}

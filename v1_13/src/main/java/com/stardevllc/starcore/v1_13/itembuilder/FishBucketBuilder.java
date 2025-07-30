package com.stardevllc.starcore.v1_13.itembuilder;

import com.stardevllc.starcore.api.itembuilder.ItemBuilder;
import com.stardevllc.starmclib.XMaterial;
import org.bukkit.DyeColor;
import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;

public class FishBucketBuilder extends ItemBuilder<FishBucketBuilder, TropicalFishBucketMeta> {
    
    private DyeColor bodyColor = DyeColor.BLACK;
    private TropicalFish.Pattern pattern = TropicalFish.Pattern.BLOCKFISH;
    private DyeColor patternColor = DyeColor.WHITE;
    
    public FishBucketBuilder() {
        super(XMaterial.TROPICAL_FISH_BUCKET);
    }
    
    public FishBucketBuilder(ItemStack itemStack) {
        super(itemStack);
        
        TropicalFishBucketMeta itemMeta = (TropicalFishBucketMeta) itemStack.getItemMeta();
        if (itemMeta != null) {
            this.bodyColor = itemMeta.getBodyColor();
            this.pattern = itemMeta.getPattern();
            this.patternColor = itemMeta.getPatternColor();
        }
    }
    
    public FishBucketBuilder(FishBucketBuilder builder) {
        super(builder);
        this.bodyColor = builder.bodyColor;
        this.pattern = builder.pattern;
        this.patternColor = builder.patternColor;
    }
    
    public FishBucketBuilder(DyeColor bodyColor, TropicalFish.Pattern pattern, DyeColor patternColor) {
        bodyColor(bodyColor).pattern(pattern).patternColor(patternColor);
    }
    
    public FishBucketBuilder bodyColor(DyeColor color) {
        this.bodyColor = color;
        return this;
    }
    
    public FishBucketBuilder pattern(TropicalFish.Pattern pattern) {
        this.pattern = pattern;
        return this;
    }
    
    public FishBucketBuilder patternColor(DyeColor color) {
        this.patternColor = color;
        return this;
    }

    @Override
    protected TropicalFishBucketMeta createItemMeta() {
        TropicalFishBucketMeta itemMeta = super.createItemMeta();
        itemMeta.setBodyColor(this.bodyColor);
        itemMeta.setPattern(this.pattern);
        itemMeta.setPatternColor(this.patternColor);
        return itemMeta;
    }

    @Override
    public FishBucketBuilder clone() {
        return new FishBucketBuilder(this);
    }
}

package com.stardevllc.starcore.v1_13_R1.itembuilder;

import com.stardevllc.starcore.api.XMaterial;
import com.stardevllc.starcore.api.itembuilder.ItemBuilder;
import org.bukkit.DyeColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;

public class FishBucketBuilder extends ItemBuilder {
    
    static {
        ItemBuilder.META_TO_BUILDERS.put(TropicalFishBucketMeta.class, FishBucketBuilder.class);
    }
    
    private DyeColor bodyColor = DyeColor.BLACK;
    private TropicalFish.Pattern pattern = TropicalFish.Pattern.BLOCKFISH;
    private DyeColor patternColor = DyeColor.WHITE;
    
    public FishBucketBuilder() {
        super(XMaterial.TROPICAL_FISH_BUCKET);
    }

    public FishBucketBuilder(DyeColor bodyColor, TropicalFish.Pattern pattern, DyeColor patternColor) {
        bodyColor(bodyColor).pattern(pattern).patternColor(patternColor);
    }
    
    protected static FishBucketBuilder createFromItemStack(ItemStack itemStack) {
        FishBucketBuilder builder = new FishBucketBuilder();
        TropicalFishBucketMeta meta = (TropicalFishBucketMeta) itemStack.getItemMeta();
        builder.bodyColor(meta.getBodyColor()).pattern(meta.getPattern()).patternColor(meta.getPatternColor());
        return builder;
    }

    protected static FishBucketBuilder createFromConfig(ConfigurationSection section) {
        FishBucketBuilder builder = new FishBucketBuilder();
        builder.bodyColor(DyeColor.valueOf(section.getString("bodycolor")));
        builder.pattern(TropicalFish.Pattern.valueOf("pattern"));
        builder.patternColor(DyeColor.valueOf("patterncolor"));
        return builder;
    }

    @Override
    public void saveToConfig(ConfigurationSection section) {
        super.saveToConfig(section);
        section.set("bodycolor", this.bodyColor.name());
        section.set("pattern", this.pattern.name());
        section.set("patterncolor", this.patternColor.name());
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
        TropicalFishBucketMeta itemMeta = (TropicalFishBucketMeta) super.createItemMeta();
        itemMeta.setBodyColor(this.bodyColor);
        itemMeta.setPattern(this.pattern);
        itemMeta.setPatternColor(this.patternColor);
        return itemMeta;
    }

    @Override
    public FishBucketBuilder clone() {
        FishBucketBuilder clone = (FishBucketBuilder) super.clone();
        clone.bodyColor = this.bodyColor;
        clone.pattern = this.pattern;
        clone.patternColor = this.patternColor;
        return clone;
    }
}

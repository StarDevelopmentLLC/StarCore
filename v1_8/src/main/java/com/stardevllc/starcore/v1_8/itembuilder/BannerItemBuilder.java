package com.stardevllc.starcore.v1_8.itembuilder;

import com.stardevllc.starcore.api.itembuilder.ItemBuilder;
import com.stardevllc.starmclib.XMaterial;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.LinkedList;
import java.util.List;

public class BannerItemBuilder extends ItemBuilder<BannerItemBuilder, BannerMeta> {
    
    private List<Pattern> patterns = new LinkedList<>();
    
    public BannerItemBuilder() {}
    
    public BannerItemBuilder(ItemStack itemStack) {
        super(itemStack);
        
        BannerMeta itemMeta = (BannerMeta) itemStack.getItemMeta();
        if (itemMeta != null) {
            this.patterns.addAll(itemMeta.getPatterns());
        }
    }
    
    public BannerItemBuilder(BannerItemBuilder builder) {
        super(builder);
        this.patterns.addAll(builder.patterns);
    }
    
    public BannerItemBuilder(XMaterial material) {
        super(material);
    }
    
    public BannerItemBuilder addPattern(Pattern pattern) {
        this.patterns.add(pattern);
        return this;
    }
    
    public BannerItemBuilder setPatterns(List<Pattern> patterns) {
        this.patterns.clear();
        this.patterns.addAll(patterns);
        return this;
    }
    
    public BannerItemBuilder addPattern(int index, Pattern pattern) {
        this.patterns.add(index, pattern);
        return this;
    }

    @Override
    protected BannerMeta createItemMeta() {
        BannerMeta itemMeta = super.createItemMeta();
        itemMeta.setPatterns(this.patterns);
        return itemMeta;
    }

    @Override
    public BannerItemBuilder clone() {
        return new BannerItemBuilder(this);
    }
}

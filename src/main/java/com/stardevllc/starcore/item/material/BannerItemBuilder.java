package com.stardevllc.starcore.item.material;

import com.cryptomorin.xseries.XMaterial;
import com.stardevllc.starcore.item.ItemBuilder;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.LinkedList;
import java.util.List;

public class BannerItemBuilder extends ItemBuilder {
    
    static {
        ItemBuilder.META_TO_BUILDERS.put(BannerMeta.class, BannerItemBuilder.class);
    }
    
    private List<Pattern> patterns = new LinkedList<>();
    
    public BannerItemBuilder(XMaterial material) {
        super(material);
    }
    
    protected BannerItemBuilder() {
        
    }

    protected static BannerItemBuilder createFromItemStack(ItemStack itemStack) {
        BannerItemBuilder itemBuilder = new BannerItemBuilder();
        BannerMeta bannerMeta = (BannerMeta) itemStack.getItemMeta();
        itemBuilder.patterns.addAll(bannerMeta.getPatterns());
        return itemBuilder;
    }

    protected static BannerItemBuilder createFromConfig(ConfigurationSection section) {
        BannerItemBuilder builder = new BannerItemBuilder();
        ConfigurationSection patternsSection = section.getConfigurationSection("patterns");
        if (patternsSection != null) {
            for (String key : patternsSection.getKeys(false)) {
                PatternType type = PatternType.valueOf(patternsSection.getString(key + ".type"));
                DyeColor color = DyeColor.valueOf(patternsSection.getString(key + ".color"));
                builder.addPattern(new Pattern(color, type));
            }
        }
        return builder;
    }

    @Override
    public void saveToConfig(ConfigurationSection section) {
        super.saveToConfig(section);
        for (int i = 0; i < patterns.size(); i++) {
            section.set("patterns." + i + ".type", patterns.get(i).getPattern().name());
            section.set("patterns." + i + ".color", patterns.get(i).getColor().name());
        }
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
        BannerMeta itemMeta = (BannerMeta) super.createItemMeta();
        itemMeta.setPatterns(this.patterns);
        return itemMeta;
    }

    @Override
    public BannerItemBuilder clone() {
        BannerItemBuilder clone = (BannerItemBuilder) super.clone();
        clone.patterns.addAll(this.patterns);
        return clone;
    }
}

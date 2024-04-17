package com.stardevllc.starcore.utils.item.material;

import com.cryptomorin.xseries.XMaterial;
import com.stardevllc.starcore.utils.item.ItemBuilder;
import org.bukkit.DyeColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
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

    public BannerItemBuilder(DyeColor dyeColor) {
        super(switch (dyeColor) {
            case WHITE -> XMaterial.WHITE_BANNER;
            case ORANGE -> XMaterial.ORANGE_BANNER;
            case MAGENTA -> XMaterial.MAGENTA_BANNER;
            case LIGHT_BLUE -> XMaterial.LIGHT_BLUE_BANNER;
            case YELLOW -> XMaterial.YELLOW_BANNER;
            case LIME -> XMaterial.LIME_BANNER;
            case PINK -> XMaterial.PINK_BANNER;
            case GRAY -> XMaterial.GRAY_BANNER;
            case LIGHT_GRAY -> XMaterial.LIGHT_GRAY_BANNER;
            case CYAN -> XMaterial.CYAN_BANNER;
            case PURPLE -> XMaterial.PURPLE_BANNER;
            case BLUE -> XMaterial.BLUE_BANNER;
            case BROWN -> XMaterial.BROWN_BANNER;
            case GREEN -> XMaterial.GREEN_BANNER;
            case RED -> XMaterial.RED_BANNER;
            case BLACK -> XMaterial.BLACK_BANNER;
        });
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
    public BannerItemBuilder addAttributeModifier(Attribute attribute, String name, double amount, AttributeModifier.Operation operation, EquipmentSlot slot) {
        super.addAttributeModifier(attribute, name, amount, operation, slot);
        return this;
    }

    @Override
    public BannerItemBuilder addAttributeModifier(Attribute attribute, String name, double amount, AttributeModifier.Operation operation) {
        super.addAttributeModifier(attribute, name, amount, operation);
        return this;
    }

    @Override
    public BannerItemBuilder addEnchant(Enchantment enchantment, int level) {
        super.addEnchant(enchantment, level);
        return this;
    }

    @Override
    public BannerItemBuilder addItemFlags(ItemFlag... itemFlags) {
        super.addItemFlags(itemFlags);
        return this;
    }

    @Override
    public BannerItemBuilder setLore(List<String> lore) {
        super.setLore(lore);
        return this;
    }

    @Override
    public BannerItemBuilder addLoreLine(String line) {
        super.addLoreLine(line);
        return this;
    }

    @Override
    public BannerItemBuilder material(XMaterial material) {
        super.material(material);
        return this;
    }

    @Override
    public BannerItemBuilder amount(int amount) {
        super.amount(amount);
        return this;
    }

    @Override
    public BannerItemBuilder displayName(String displayName) {
        super.displayName(displayName);
        return this;
    }

    @Override
    public BannerItemBuilder unbreakable(boolean unbreakable) {
        super.unbreakable(unbreakable);
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

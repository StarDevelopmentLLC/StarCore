package com.stardevllc.starcore.utils.item;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Multimap;
import com.stardevllc.starcore.utils.color.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * A builder for Items <br> 
 * You can use the child classes to customize every type that has an ItemMeta <br>
 * Note: This uses the XMaterial library to allow multi-version support
 */
@SuppressWarnings("deprecation")
public class ItemBuilder implements Cloneable {
    
    protected static final Map<Class<? extends ItemMeta>, Class<? extends ItemBuilder>> META_TO_BUILDERS = new HashMap<>();
    
    protected XMaterial material;
    protected int amount;
    protected Map<Attribute, AttributeModifier> attributes = new EnumMap<>(Attribute.class);
    protected Map<Enchantment, Integer> enchantments = new HashMap<>();
    protected ItemFlag[] itemFlags;
    protected String displayName;
    protected List<String> lore = new LinkedList<>();
    protected boolean unbreakable;
    protected int repairCost;
    protected int damage;
    
    @SuppressWarnings("SuspiciousMethodCalls")
    public static ItemBuilder fromConfig(ConfigurationSection section) {
        XMaterial material = XMaterial.valueOf(section.getString("material"));
        ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(material.parseMaterial());
        ItemBuilder itemBuilder;
        if (META_TO_BUILDERS.containsKey(itemMeta)) {
            itemBuilder = getSubClassFromMeta(itemMeta, "createFromConfig", ConfigurationSection.class, section);
        } else {
            itemBuilder = new ItemBuilder();
        }
       
        itemBuilder.material(material);
        itemBuilder.amount(section.getInt("amount"));
        itemBuilder.displayName(section.getString("displayname"));
        itemBuilder.setLore(section.getStringList("lore"));
        itemBuilder.repairCost(section.getInt("repaircost"));
        itemBuilder.damage(section.getInt("damage"));
        List<String> flagNames = section.getStringList("flags");
        if (!flagNames.isEmpty()) {
            for (String flagName : flagNames) {
                itemBuilder.addItemFlags(ItemFlag.valueOf(flagName));
            }
        }
        
        ConfigurationSection enchantsSection = section.getConfigurationSection("enchantments");
        if (enchantsSection != null) {
            for (String enchantName : enchantsSection.getKeys(false)) {
                Enchantment enchantment = Enchantment.getByKey(NamespacedKey.fromString(enchantName.replace("_", ":")));
                int level = enchantsSection.getInt(enchantName);
                itemBuilder.addEnchant(enchantment, level);
            }
        }
        
        ConfigurationSection attributesSection = section.getConfigurationSection("attributes");
        if (attributesSection != null) {
            for (String key : attributesSection.getKeys(false)) {
                Attribute attribute = Attribute.valueOf(key.toUpperCase());
                String name = attributesSection.getString(key + ".name");
                double amount = attributesSection.getDouble(key + ".amount");
                Operation operation = Operation.valueOf(attributesSection.getString(key + ".operation"));
                if (!attributesSection.contains(key + ".slot")) {
                    itemBuilder.addAttributeModifier(attribute, name, amount, operation);
                } else {
                    EquipmentSlot slot = EquipmentSlot.valueOf(attributesSection.getString(key + ".slot"));
                    itemBuilder.addAttributeModifier(attribute, name, amount, operation, slot);
                }
            }
        }

        return itemBuilder;
    }
    
    public void saveToConfig(ConfigurationSection section) {
        section.set("material", material.name());
        section.set("amount", amount);
        section.set("displayname", displayName);
        section.set("lore", lore);
        section.set("repaircost", repairCost);
        section.set("damage", damage);
        List<String> flags = new ArrayList<>();
        if (itemFlags != null) {
            for (ItemFlag itemFlag : itemFlags) {
                flags.add(itemFlag.name());
            }
        }
        section.set("flags", flags);
        
        enchantments.forEach((enchant, level) -> section.set("enchantments." + enchant.getKey().toString().replace(":", "_"), level));
        attributes.forEach((attribute, modifier) -> {
            String attributeName = attribute.name().toLowerCase();
            section.set("attributes." + attributeName + ".amount", modifier.getAmount());
            section.set("attributes." + attributeName + ".name", modifier.getName());
            section.set("attributes." + attributeName + ".operation", modifier.getOperation().name());
            if (modifier.getSlot() != null) {
                section.set("attributes." + attributeName + ".slot", modifier.getSlot().name());
            }
        });
    }

    public static ItemBuilder of(XMaterial material) {
        return new ItemBuilder(material);
    }
    
    public static ItemBuilder fromItemStack(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        ItemBuilder itemBuilder = getSubClassFromMeta(itemMeta, "createFromItemStack", ItemStack.class, itemStack);
        
        itemBuilder.displayName(itemMeta.getDisplayName()).amount(itemStack.getAmount()).addItemFlags(itemMeta.getItemFlags().toArray(new ItemFlag[0]))
                .unbreakable(itemMeta.isUnbreakable()).setLore(itemMeta.getLore()).setEnchants(itemMeta.getEnchants());
        Multimap<Attribute, AttributeModifier> attributeModifiers = itemMeta.getAttributeModifiers();
        if (attributeModifiers != null) {
            attributeModifiers.forEach((attribute, modifier) -> itemBuilder.attributes.put(attribute, modifier));
        }
        
        if (itemMeta instanceof Repairable repairable) {
            itemBuilder.repairCost(repairable.getRepairCost());
        }
        
        if (itemMeta instanceof Damageable damageable) {
            itemBuilder.damage(damageable.getDamage());
        }

        return itemBuilder;
    }
    
    protected ItemBuilder() {
        
    }
    
    protected ItemBuilder(XMaterial material) {
        this.material = material;
    }
    
    public ItemBuilder addAttributeModifier(Attribute attribute, String name, double amount, Operation operation, EquipmentSlot slot) {
        this.attributes.put(attribute, new AttributeModifier(UUID.randomUUID(), name, amount, operation, slot));
        return this;
    }

    public ItemBuilder addAttributeModifier(Attribute attribute, String name, double amount, Operation operation) {
        this.attributes.put(attribute, new AttributeModifier(UUID.randomUUID(), name, amount, operation));
        return this;
    }
    
    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        this.enchantments.put(enchantment, level);
        return this;
    }
    
    public ItemBuilder setEnchants(Map<Enchantment, Integer> enchants) {
        this.enchantments.clear();
        this.enchantments.putAll(enchants);
        return this;
    }
    
    public ItemBuilder addItemFlags(ItemFlag... itemFlags) {
        this.itemFlags = itemFlags;
        return this;
    }
    
    public ItemBuilder setLore(List<String> lore) {
        this.lore.clear();
        this.lore.addAll(lore);
        return this;
    }
    
    public ItemBuilder addLoreLine(String line) {
        this.lore.add(line);
        return this;
    }
    
    public ItemBuilder setLoreLine(int index, String line) {
        this.lore.set(index, line);
        return this;
    }

    public ItemBuilder material(XMaterial material) {
        this.material = material;
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public ItemBuilder unbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }
    
    public ItemBuilder repairCost(int repairCost) {
        this.repairCost = repairCost;
        return this;
    }
    
    public ItemBuilder damage(int damage) {
        this.damage = damage;
        return this;
    }

    protected ItemMeta createItemMeta() {
        ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(this.material.parseMaterial());
        if (!attributes.isEmpty()) {
            this.attributes.forEach((attribute, modifier) -> itemMeta.addAttributeModifier(attribute, modifier));
        }

        if (!this.enchantments.isEmpty()) {
            this.enchantments.forEach((enchant, level) -> itemMeta.addEnchant(enchant, level, true));
        }

        if (itemFlags != null) {
            itemMeta.addItemFlags(this.itemFlags);
        }

        if (this.displayName != null) {
            itemMeta.setDisplayName(ColorUtils.color(this.displayName));
        }

        if (!this.lore.isEmpty()) {
            List<String> coloredLore = this.lore.stream().map(ColorUtils::color).collect(Collectors.toCollection(LinkedList::new));
            itemMeta.setLore(coloredLore);
        }
        
        if (itemMeta instanceof Repairable repairable) {
            repairable.setRepairCost(this.repairCost);
        }
        
        if (itemMeta instanceof Damageable damageable) {
            damageable.setDamage(this.damage);
        }
        
        itemMeta.setUnbreakable(unbreakable);
        return itemMeta;
    }
    
    public ItemStack build() {
        if (amount < 1) {
            amount = 1;
        }
        
        if (!material.parseMaterial().isItem()) {
            return null;
        }
        
        ItemStack itemStack = material.parseItem();
        itemStack.setAmount(amount);
        ItemMeta itemMeta = createItemMeta();
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemBuilder clone() {
        ItemBuilder clone = null;
        try {
            clone = (ItemBuilder) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        clone.amount = amount;
        clone.attributes.putAll(this.attributes);
        clone.enchantments.putAll(this.enchantments);
        clone.itemFlags = this.itemFlags;
        clone.displayName = this.displayName;
        clone.lore.addAll(this.lore);
        clone.unbreakable = this.unbreakable;
        clone.repairCost = this.repairCost;
        clone.damage = this.damage;
        return clone;
    }
    
    private static ItemBuilder getSubClassFromMeta(ItemMeta itemMeta, String methodName, Class<?> paramClass, Object param) {
        Class<? extends ItemBuilder> itemBuilderClass = null;
        for (Map.Entry<Class<? extends ItemMeta>, Class<? extends ItemBuilder>> entry : META_TO_BUILDERS.entrySet()) {
            if (entry.getKey().isAssignableFrom(itemMeta.getClass())) {
                itemBuilderClass = entry.getValue();
                break;
            }
        }

        try {
            Method method = itemBuilderClass.getDeclaredMethod(methodName, paramClass);
            method.setAccessible(true);
            return (ItemBuilder) method.invoke(null, param);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassCastException e) {
            Logger logger = Bukkit.getLogger();
            logger.log(Level.SEVERE, "Error while parsing an ItemStack into an ItemBuilder", e);
            return null;
        }
    }
}
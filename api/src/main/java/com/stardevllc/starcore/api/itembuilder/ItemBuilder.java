package com.stardevllc.starcore.api.itembuilder;

import com.stardevllc.starcore.api.wrappers.AttributeModifierWrapper;
import com.stardevllc.starcore.api.wrappers.MCWrappers;
import com.stardevllc.starmclib.XMaterial;
import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.nbtapi.iface.ReadableNBT;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ItemBuilder implements Cloneable {

    protected static final Map<Class<? extends ItemMeta>, Class<? extends ItemBuilder>> META_TO_BUILDERS = new HashMap<>();

    public static Function<String, String> colorFunction = s -> ChatColor.translateAlternateColorCodes('&', s);
    
    protected XMaterial material;
    protected int amount;
    protected Map<String, AttributeModifierWrapper> attributes = new HashMap<>();
    protected Map<Enchantment, Integer> enchantments = new HashMap<>();
    protected ItemFlag[] itemFlags;
    protected String displayName;
    protected List<String> lore = new LinkedList<>();
    protected boolean unbreakable;
    protected int repairCost;
    protected int damage;

    protected Map<String, Object> customNBT = new HashMap<>();

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
            for (Object enchantName : enchantsSection.getKeys(false)) {
                Enchantment enchantment = getMCWrappers().getEnchantWrapper().getEnchantmentByKey(enchantName.toString().replace("_", ":"));
                int level = enchantsSection.getInt(enchantName.toString());
                itemBuilder.addEnchant(enchantment, level);
            }
        }

        ConfigurationSection attributesSection = section.getConfigurationSection("attributes");
        if (attributesSection != null) {
            for (Object key : attributesSection.getKeys(false)) {
                String attribute = key.toString().toUpperCase();
                String name = attributesSection.getString(key + ".name");
                double amount = attributesSection.getDouble(key + ".amount");
                String operation = attributesSection.getString(key + ".operation");
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
    
    protected static ItemBuilder createFromItemStack(ItemStack itemStack) {
        return new ItemBuilder(XMaterial.matchXMaterial(itemStack.getType()));
    }
    
    protected static MCWrappers getMCWrappers() {
        return Bukkit.getServicesManager().getRegistration(MCWrappers.class).getProvider();
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

        enchantments.forEach((enchant, level) -> section.set("enchantments." + getMCWrappers().getEnchantWrapper().getEnchantmentKey(enchant), level));
        attributes.forEach((attribute, modifier) -> {
            String attributeName = attribute.toLowerCase();
            section.set("attributes." + attributeName + ".amount", modifier.getAmount());
            section.set("attributes." + attributeName + ".name", modifier.getName());
            section.set("attributes." + attributeName + ".operation", modifier.getOperation());
            if (modifier.getSlot() != null) {
                section.set("attributes." + attributeName + ".slot", modifier.getSlot().name());
            }
        });
    }

    public static ItemBuilder of(XMaterial xMaterial) {
        Material material = xMaterial.parseMaterial();
        ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(material);
        
        if (itemMeta == null) {
            return new ItemBuilder(xMaterial);
        }
        
        Class<? extends ItemBuilder> builderClass = META_TO_BUILDERS.get(itemMeta.getClass());
        
        if (builderClass != null) {
            try {
                Constructor<? extends ItemBuilder> matConstructor = builderClass.getDeclaredConstructor(XMaterial.class);
                matConstructor.setAccessible(true);
                return matConstructor.newInstance(xMaterial);
            } catch (NoSuchMethodException e) {
                try {
                    Constructor<? extends ItemBuilder> noArgsConstructor = builderClass.getDeclaredConstructor();
                    noArgsConstructor.setAccessible(true);
                    return noArgsConstructor.newInstance();
                } catch (Exception ex) {
                    return new ItemBuilder(xMaterial);
                }
            } catch (Exception e) {
                return new ItemBuilder(xMaterial);
            }
        } 
        
        return new ItemBuilder(xMaterial);
    }

    public static ItemBuilder fromItemStack(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        ItemBuilder itemBuilder = getSubClassFromMeta(itemMeta, "createFromItemStack", ItemStack.class, itemStack);

        itemBuilder.displayName(itemMeta.getDisplayName()).amount(itemStack.getAmount())
                .addItemFlags(itemMeta.getItemFlags().toArray(new ItemFlag[0]))
                .setLore(itemMeta.getLore()).setEnchants(itemMeta.getEnchants());

        NBT.get(itemStack, nbt -> {
            itemBuilder.unbreakable(nbt.getBoolean("Unbreakable")).damage(nbt.getInteger("Damage"));
        });
        Map<String, AttributeModifierWrapper> attributeModifiers = getMCWrappers().getItemWrapper().getAttributeModifiers(itemStack);
        if (attributeModifiers != null) {
            itemBuilder.attributes.putAll(attributeModifiers);
        }

        if (itemMeta instanceof Repairable repairable) {
            itemBuilder.repairCost(repairable.getRepairCost());
        }

        ReadableNBT compund = NBT.get(itemStack, nbt -> {
            return nbt.getCompound("custom");
        });

        if (compund != null) {
            Set<String> keys = compund.getKeys();
            if (keys != null && !keys.isEmpty()) {
                for (String key : keys) {
                    switch (compund.getType(key)) {
                        case NBTTagByte -> itemBuilder.addCustomNBT(key, compund.getByte(key));
                        case NBTTagShort -> itemBuilder.addCustomNBT(key, compund.getShort(key));
                        case NBTTagInt -> itemBuilder.addCustomNBT(key, compund.getInteger(key));
                        case NBTTagLong -> itemBuilder.addCustomNBT(key, compund.getLong(key));
                        case NBTTagFloat -> itemBuilder.addCustomNBT(key, compund.getFloat(key));
                        case NBTTagDouble -> itemBuilder.addCustomNBT(key, compund.getDouble(key));
                        case NBTTagString -> itemBuilder.addCustomNBT(key, compund.getString(key));
                        default -> {
                        }
                    }
                }
            }
        }

        return itemBuilder;
    }

    protected ItemBuilder() {
        
    }

    protected ItemBuilder(XMaterial material) {
        this.material = material;
    }

    public <T extends ItemBuilder> T addAttributeModifier(String attribute, String name, double amount, String operation, EquipmentSlot slot) {
        this.attributes.put(attribute, new AttributeModifierWrapper(UUID.randomUUID(), name, amount, operation, slot));
        return (T) this;
    }

    public <T extends ItemBuilder> T addAttributeModifier(String attribute, String name, double amount, String operation) {
        this.attributes.put(attribute, new AttributeModifierWrapper(UUID.randomUUID(), name, amount, operation, null));
        return (T) this;
    }

    public <T extends ItemBuilder> T addEnchant(Enchantment enchantment, int level) {
        this.enchantments.put(enchantment, level);
        return (T) this;
    }

    public <T extends ItemBuilder> T setEnchants(Map<Enchantment, Integer> enchants) {
        this.enchantments.clear();
        this.enchantments.putAll(enchants);
        return (T) this;
    }

    public <T extends ItemBuilder> T addItemFlags(ItemFlag... itemFlags) {
        this.itemFlags = itemFlags;
        return (T) this;
    }

    public <T extends ItemBuilder> T setLore(List<String> lore) {
        this.lore.clear();
        if (lore != null) {
            this.lore.addAll(lore);
        }
        return (T) this;
    }

    public <T extends ItemBuilder> T addLoreLine(String line) {
        this.lore.add(line);
        return (T) this;
    }

    public <T extends ItemBuilder> T setLoreLine(int index, String line) {
        this.lore.set(index, line);
        return (T) this;
    }

    public <T extends ItemBuilder> T material(XMaterial material) {
        this.material = material;
        return (T) this;
    }

    public <T extends ItemBuilder> T amount(int amount) {
        this.amount = amount;
        return (T) this;
    }

    public <T extends ItemBuilder> T displayName(String displayName) {
        this.displayName = displayName;
        return (T) this;
    }

    public <T extends ItemBuilder> T unbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return (T) this;
    }

    public <T extends ItemBuilder> T repairCost(int repairCost) {
        this.repairCost = repairCost;
        return (T) this;
    }

    public <T extends ItemBuilder> T damage(int damage) {
        this.damage = damage;
        return (T) this;
    }

    public <T extends ItemBuilder> T addCustomNBT(String key, Object value) {
        this.customNBT.put(key, value);
        return (T) this;
    }

    protected ItemMeta createItemMeta() {
        ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(this.material.parseMaterial());
        if (!attributes.isEmpty()) {
            this.attributes.forEach((attribute, modifier) -> getMCWrappers().getItemWrapper().addAttributeModifier(itemMeta, attribute, modifier));
        }

        if (!this.enchantments.isEmpty()) {
            this.enchantments.forEach((enchant, level) -> itemMeta.addEnchant(enchant, level, true));
        }

        if (itemFlags != null) {
            itemMeta.addItemFlags(this.itemFlags);
        }

        if (this.displayName != null) {
            itemMeta.setDisplayName(colorFunction.apply(this.displayName));
        }
        
        List<String> coloredLore = new LinkedList<>();
        this.lore.forEach(line -> coloredLore.add(colorFunction.apply(line)));
        itemMeta.setLore(coloredLore);

        if (itemMeta instanceof Repairable repairable) {
            repairable.setRepairCost(this.repairCost);
        }
        return itemMeta;
    }

    public ItemStack build() {
        if (amount < 1) {
            amount = 1;
        }
        
        if (material == null) {
            Bukkit.getLogger().severe("[StarCore] Material was null in ItemBuilder.build()");
            return null;
        }

        if (material.parseMaterial() == null) {
            return null;
        }

        ItemStack itemStack = material.parseItem();
        itemStack.setAmount(amount);
        ItemMeta itemMeta = createItemMeta();
        itemStack.setItemMeta(itemMeta);

        NBT.modify(itemStack, nbtItem -> {
            if (!this.customNBT.isEmpty()) {
                ReadWriteNBT customCompound = nbtItem.getOrCreateCompound("custom");
                this.customNBT.forEach((key, value) -> {
                    if (value instanceof String str) {
                        customCompound.setString(key, str);
                    } else if (value instanceof Integer i) {
                        customCompound.setInteger(key, i);
                    } else if (value instanceof Double d) {
                        customCompound.setDouble(key, d);
                    } else if (value instanceof Byte b) {
                        customCompound.setByte(key, b);
                    } else if (value instanceof Short s) {
                        customCompound.setShort(key, s);
                    } else if (value instanceof Long l) {
                        customCompound.setLong(key, l);
                    } else if (value instanceof Float f) {
                        customCompound.setFloat(key, f);
                    } else if (value instanceof Boolean b) {
                        customCompound.setBoolean(key, b);
                    } else if (value instanceof UUID uuid) {
                        customCompound.setUUID(key, uuid);
                    }
                });
            }

            nbtItem.setBoolean("Unbreakable", unbreakable);
            nbtItem.setInteger("Damage", this.damage);
        });

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
        
        if (itemBuilderClass == null) {
            itemBuilderClass = ItemBuilder.class;
        }

        try {
            Method method = itemBuilderClass.getDeclaredMethod(methodName, paramClass);
            method.setAccessible(true);
            return (ItemBuilder) method.invoke(null, param);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassCastException e) {
            Logger logger = Bukkit.getLogger();
            logger.severe("Error while parsing an ItemStack into an ItemBuilder");
            logger.severe("  ItemMeta Class: " + itemMeta.getClass().getName());
            logger.severe("  MethodName: " + methodName);
            logger.severe("  ParamClass: " + paramClass);
            logger.severe("  Param: " + param);
            logger.severe("  ItemBuilder class: " + itemBuilderClass.getName());
            logger.log(Level.SEVERE, "", e);
            return null;
        }
    }
}
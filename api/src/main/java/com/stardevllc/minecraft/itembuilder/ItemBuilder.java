package com.stardevllc.minecraft.itembuilder;

import com.stardevllc.minecraft.smaterial.SMaterial;
import com.stardevllc.minecraft.wrappers.AttributeModifierWrapper;
import com.stardevllc.starlib.objects.builder.IBuilder;
import com.stardevllc.starlib.reflection.ReflectionHelper;
import com.stardevllc.starlib.serialization.StarSerializable;
import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.nbtapi.iface.ReadableNBT;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class ItemBuilder<I extends ItemBuilder<I, M>, M extends ItemMeta> implements IBuilder<ItemStack, I>, StarSerializable, ConfigurationSerializable {
    //Reflection methods for the unbreakable flag
    
    //These are the set and is unbreakable methods in ItemMeta itself. This is in newer spigot versions and should be preferred
    private static final Method metaSetUnbreakable = ReflectionHelper.getClassMethod(ItemMeta.class, "setUnbreakable", boolean.class);
    private static final Method metaIsUnbreakable = ReflectionHelper.getClassMethod(ItemMeta.class, "isUnbreakable");
    
    //These are relalted to the spigot() based unbreakable methods 
    private static final Method metaSpigot = ReflectionHelper.getClassMethod(ItemMeta.class, "spigot");
    
    private static final Method spigotSetUnbreakable, spigotIsUnbreakable;
    
    //Need a static initializer to allow detection of the spigot() method without errors
    static {
        Class<?> metaSpigotClass = null;
        try {
            metaSpigotClass = Class.forName("org.bukkit.inventory.meta.ItemMeta$Spigot");
        } catch (Throwable e) {}
        
        if (metaSpigotClass != null) {
            spigotSetUnbreakable = ReflectionHelper.getClassMethod(metaSpigotClass, "setUnbreakable", boolean.class);
            spigotIsUnbreakable = ReflectionHelper.getClassMethod(metaSpigotClass, "isUnbreakable");
        } else {
            spigotSetUnbreakable = null;
            spigotIsUnbreakable = null;
        }
    }
    
    public static Function<String, String> colorFunction = s -> ChatColor.translateAlternateColorCodes('&', s);
    
    protected SMaterial material;
    protected int amount;
    protected Map<String, AttributeModifierWrapper> attributes = new HashMap<>();
    protected Map<Enchantment, Integer> enchantments = new HashMap<>();
    protected List<ItemFlag> itemFlags = new ArrayList<>();
    protected String displayName;
    protected List<String> lore = new LinkedList<>();
    protected boolean unbreakable;
    protected int repairCost;
    protected int damage;

    protected Map<String, Object> customNBT = new HashMap<>();

    public ItemBuilder() {}
    
    public ItemBuilder(I builder) {
        this.material = builder.material;
        this.amount = builder.amount;
        this.attributes.putAll(builder.attributes);
        this.enchantments.putAll(builder.enchantments);
        this.itemFlags.addAll(builder.itemFlags);
        this.displayName = builder.displayName;
        this.lore.addAll(builder.lore);
        this.unbreakable = builder.unbreakable;
        this.repairCost = builder.repairCost;
        this.damage = builder.damage;
        this.customNBT.putAll(builder.customNBT);
    }
    
    public ItemBuilder(ItemStack itemStack) {
        this.material = SMaterial.matchSMaterial(itemStack);
        this.amount = itemStack.getAmount();
        this.enchantments.putAll(itemStack.getEnchantments());
        this.damage = itemStack.getDurability();
        
        NBT.get(itemStack, nbt -> {
            damage = nbt.getInteger("Damage");
        });
        
        //TODO
//        Map<String, AttributeModifierWrapper> attributeModifiers = MCWrappers.getItemWrapper().getAttributeModifiers(itemStack);
//        if (attributeModifiers != null) {
//            attributes.putAll(attributeModifiers);
//        }
        
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            if (metaIsUnbreakable != null) {
                try {
                    this.unbreakable = (boolean) metaIsUnbreakable.invoke(itemStack.getItemMeta());
                } catch (Throwable e) {}
            } else if (metaSpigot != null && spigotIsUnbreakable != null) {
                try {
                    Object spigot = metaSpigot.invoke(itemMeta);
                    this.unbreakable = (boolean) spigotIsUnbreakable.invoke(spigot);
                } catch (Throwable e) {}
            }
            
            if (itemMeta.getItemFlags() != null) {
                this.itemFlags.addAll(itemMeta.getItemFlags());
            }
            
            if (itemMeta.getLore() != null) {
                this.lore.addAll(itemMeta.getLore());
            }
            
            this.displayName = itemMeta.getDisplayName();
            
            if (itemMeta instanceof Repairable repairable) {
                repairCost = repairable.getRepairCost();
            }
        }
        
        ReadableNBT compund = NBT.get(itemStack, nbt -> {
            return nbt.getCompound("custom");
        });

        if (compund != null) {
            Set<String> keys = compund.getKeys();
            if (keys != null && !keys.isEmpty()) {
                for (String key : keys) {
                    switch (compund.getType(key)) {
                        case NBTTagByte -> addCustomNBT(key, compund.getByte(key));
                        case NBTTagShort -> addCustomNBT(key, compund.getShort(key));
                        case NBTTagInt -> addCustomNBT(key, compund.getInteger(key));
                        case NBTTagLong -> addCustomNBT(key, compund.getLong(key));
                        case NBTTagFloat -> addCustomNBT(key, compund.getFloat(key));
                        case NBTTagDouble -> addCustomNBT(key, compund.getDouble(key));
                        case NBTTagString -> addCustomNBT(key, compund.getString(key));
                        default -> {}
                    }
                }
            }
        }
    }

    public ItemBuilder(SMaterial material) {
        this.material = material;
    }
    
    //TODO Implement Saving and Loading of Enchantments and Attributes, will probably make a utility like SMaterial
    public ItemBuilder(Map<String, Object> serialized) {
        this.material = (SMaterial) serialized.get("material");
        this.amount = (int) serialized.get("amount");
//        this.attributes = (Map<String, AttributeModifierWrapper>) serialized.get("attributes");
//        this.enchantments = (Map<Enchantment, Integer>) serialized.get("enchantments");
        this.itemFlags = (List<ItemFlag>) serialized.get("flags");
        this.displayName = (String) serialized.get("displayname");
        this.lore = (List<String>) serialized.get("lore");
        this.unbreakable = (boolean) serialized.get("unbreakable");
        this.repairCost = (int) serialized.get("repaircost");
        this.damage = (int) serialized.get("damage");
    }
    
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> serialized = new HashMap<>();
        serialized.put("material", material);
        serialized.put("amount", amount);
//        serialized.put("attributes", attributes);
//        serialized.put("enchantments", enchantments);
        serialized.put("flags", itemFlags);
        serialized.put("displayname", displayName);
        serialized.put("lore", lore);
        serialized.put("unbreakable", unbreakable);
        serialized.put("repaircost", repairCost);
        serialized.put("damage", damage);
        
        return serialized;
    }
    
    public static Function<String, String> getColorFunction() {
        return colorFunction;
    }
    
    public SMaterial getMaterial() {
        return material;
    }
    
    public int getAmount() {
        return amount;
    }
    
    public Map<String, AttributeModifierWrapper> getAttributes() {
        return attributes;
    }
    
    public Map<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }
    
    public List<ItemFlag> getItemFlags() {
        return itemFlags;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public List<String> getLore() {
        return lore;
    }
    
    public boolean isUnbreakable() {
        return unbreakable;
    }
    
    public int getRepairCost() {
        return repairCost;
    }
    
    public int getDamage() {
        return damage;
    }
    
    public Map<String, Object> getCustomNBT() {
        return customNBT;
    }
    
    public I addAttributeModifier(String attribute, String name, double amount, String operation, EquipmentSlot slot) {
        this.attributes.put(attribute, new AttributeModifierWrapper(UUID.randomUUID(), name, amount, operation, slot));
        return self();
    }

    public I addAttributeModifier(String attribute, String name, double amount, String operation) {
        this.attributes.put(attribute, new AttributeModifierWrapper(UUID.randomUUID(), name, amount, operation, null));
        return self();
    }

    public I addEnchant(Enchantment enchantment, int level) {
        this.enchantments.put(enchantment, level);
        return self();
    }

    public I setEnchants(Map<Enchantment, Integer> enchants) {
        this.enchantments.clear();
        this.enchantments.putAll(enchants);
        return self();
    }

    public I addItemFlags(ItemFlag... itemFlags) {
        this.itemFlags.addAll(List.of(itemFlags));
        return self();
    }

    public I setLore(List<String> lore) {
        this.lore.clear();
        if (lore != null) {
            this.lore.addAll(lore);
        }
        return self();
    }

    public I addLoreLine(String line) {
        this.lore.add(line);
        return self();
    }

    public I setLoreLine(int index, String line) {
        this.lore.set(index, line);
        return self();
    }

    public I material(SMaterial material) {
        this.material = material;
        return self();
    }

    public I amount(int amount) {
        this.amount = amount;
        return self();
    }

    public I displayName(String displayName) {
        this.displayName = displayName;
        return self();
    }

    public I unbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return self();
    }

    public I repairCost(int repairCost) {
        this.repairCost = repairCost;
        return self();
    }

    public I damage(int damage) {
        this.damage = damage;
        return self();
    }

    public I addCustomNBT(String key, Object value) {
        this.customNBT.put(key, value);
        return self();
    }
    
    public I clearCustomNBT() {
        this.customNBT.clear();
        return self();
    }
    
    public I removeCustomNBT(String key) {
        this.customNBT.remove(key);
        return self();
    }

    protected M createItemMeta() {
        M itemMeta = (M) Bukkit.getItemFactory().getItemMeta(this.material.parseMaterial());
        
        if (itemMeta == null) {
            throw new IllegalStateException("No item meta was created for the material \"" + this.material + "\"");
        }

        if (!this.enchantments.isEmpty()) {
            this.enchantments.forEach((enchant, level) -> itemMeta.addEnchant(enchant, level, true));
        }

        if (itemFlags != null) {
            itemMeta.addItemFlags(this.itemFlags.toArray(new ItemFlag[0]));
        }

        if (this.displayName != null) {
            itemMeta.setDisplayName(colorFunction.apply(this.displayName));
        }
        
        if (unbreakable) {
            if (metaSetUnbreakable != null) {
                try {
                    metaSetUnbreakable.invoke(itemMeta, true);
                } catch (Throwable e) {}
            } else if (metaSpigot != null && spigotSetUnbreakable != null) {
                try {
                    Object spigot = metaSpigot.invoke(itemMeta);
                    spigotSetUnbreakable.invoke(spigot, true);
                } catch (Throwable e) {}
            }
        }
        
        List<String> coloredLore = this.lore.stream().map(line -> colorFunction.apply(line)).collect(Collectors.toCollection(LinkedList::new));
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
        
        //TODO
//        if (!attributes.isEmpty()) {
//            this.attributes.forEach((attribute, modifier) -> MCWrappers.getItemWrapper().addAttributeModifier(itemStack, attribute, modifier));
//        }

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
        });

        return itemStack;
    }
    
    @Override
    public abstract I clone();
}
package com.stardevllc.starcore.api.itembuilder;

import com.stardevllc.starcore.api.wrappers.AttributeModifierWrapper;
import com.stardevllc.starcore.api.wrappers.MCWrappers;
import com.stardevllc.starlib.builder.IBuilder;
import com.stardevllc.starmclib.XMaterial;
import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.nbtapi.iface.ReadableNBT;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class ItemBuilder<I extends ItemBuilder<I, M>, M extends ItemMeta> implements IBuilder<ItemStack, I> {
    public static Function<String, String> colorFunction = s -> ChatColor.translateAlternateColorCodes('&', s);
    
    protected XMaterial material;
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
    
    protected static MCWrappers getMCWrappers() {
        return Bukkit.getServicesManager().getRegistration(MCWrappers.class).getProvider();
    }

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
        this.material = XMaterial.matchXMaterial(itemStack);
        this.amount = itemStack.getAmount();
        this.enchantments.putAll(itemStack.getEnchantments());
        this.damage = itemStack.getDurability();
        
        NBT.get(itemStack, nbt -> {
            unbreakable = nbt.getBoolean("Unbreakable");
            damage = nbt.getInteger("Damage");
        });
        
        Map<String, AttributeModifierWrapper> attributeModifiers = getMCWrappers().getItemWrapper().getAttributeModifiers(itemStack);
        if (attributeModifiers != null) {
            attributes.putAll(attributeModifiers);
        }
        
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            this.itemFlags.addAll(itemMeta.getItemFlags());
            this.lore.addAll(itemMeta.getLore());
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

    public ItemBuilder(XMaterial material) {
        this.material = material;
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

    public I material(XMaterial material) {
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

    protected M createItemMeta() {
        M itemMeta = (M) Bukkit.getItemFactory().getItemMeta(this.material.parseMaterial());

        if (!this.enchantments.isEmpty()) {
            this.enchantments.forEach((enchant, level) -> itemMeta.addEnchant(enchant, level, true));
        }

        if (itemFlags != null) {
            itemMeta.addItemFlags(this.itemFlags.toArray(new ItemFlag[0]));
        }

        if (this.displayName != null) {
            itemMeta.setDisplayName(colorFunction.apply(this.displayName));
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
        
        if (!attributes.isEmpty()) {
            this.attributes.forEach((attribute, modifier) -> getMCWrappers().getItemWrapper().addAttributeModifier(itemStack, attribute, modifier));
        }

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
    
    @Override
    public abstract I clone();
}
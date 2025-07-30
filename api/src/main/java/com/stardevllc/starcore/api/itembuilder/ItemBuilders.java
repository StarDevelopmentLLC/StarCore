package com.stardevllc.starcore.api.itembuilder;

import com.stardevllc.starcore.api.wrappers.MCWrappers;
import com.stardevllc.starmclib.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public final class ItemBuilders {
    private static final Map<Class<? extends ItemMeta>, Class<? extends ItemBuilder<?, ?>>> META_TO_BUILDERS = new HashMap<>();
    
    public static <M extends ItemMeta> void mapMetaToBuilder(Class<M> meta, Class<? extends ItemBuilder<?, M>> builder) {
        META_TO_BUILDERS.put(meta, builder);
    }
    
    public static <M extends ItemMeta, I extends ItemBuilder<I, M>> I of(Class<I> builderClass, XMaterial material) {
        return (I) of(material);
    }
    
    public static ItemBuilder<?, ?> of(XMaterial xMaterial) {
        Material material = xMaterial.parseMaterial();
        ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(material);
        
        if (itemMeta == null) {
            return new BasicItemBuilder(xMaterial);
        }
        
        Class<? extends ItemBuilder<?, ?>> builderClass = META_TO_BUILDERS.get(itemMeta.getClass());
        
        if (builderClass != null) {
            try {
                Constructor<? extends ItemBuilder<?, ?>> matConstructor = builderClass.getDeclaredConstructor(XMaterial.class);
                matConstructor.setAccessible(true);
                return matConstructor.newInstance(xMaterial);
            } catch (Exception e) {
                try {
                    Constructor<? extends ItemBuilder<?, ?>> noArgsConstructor = builderClass.getDeclaredConstructor();
                    noArgsConstructor.setAccessible(true);
                    return noArgsConstructor.newInstance();
                } catch (Exception ex) { }
            }
        }
        
        return new BasicItemBuilder(xMaterial);
    }
    
    public static <M extends ItemMeta, I extends ItemBuilder<I, M>> I of(Class<I> builderClass, ItemStack itemStack) {
        return (I) of(itemStack);
    }
    
    public static ItemBuilder<?, ?> of(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return new BasicItemBuilder(itemStack);
        }
        
        Class<? extends ItemBuilder<?, ?>> builderClass = META_TO_BUILDERS.get(itemMeta.getClass());
        if (builderClass != null) {
            try {
                Constructor<? extends ItemBuilder<?, ?>> stackConstructor = builderClass.getDeclaredConstructor(ItemStack.class);
                stackConstructor.setAccessible(true);
                return stackConstructor.newInstance(itemStack);
            } catch (Exception e) {
                try {
                    Constructor<? extends ItemBuilder<?, ?>> noArgsConstructor = builderClass.getDeclaredConstructor();
                    noArgsConstructor.setAccessible(true);
                    return noArgsConstructor.newInstance();
                } catch (Exception ex) {}
            }
        }
        
        return new BasicItemBuilder(itemStack);
    }
    
    private static MCWrappers getMCWrappers() {
        return Bukkit.getServicesManager().getRegistration(MCWrappers.class).getProvider();
    }
    
//    public static ItemBuilder<?> fromItemStack(ItemStack itemStack) {
//        ItemMeta itemMeta = itemStack.getItemMeta();
//        ItemBuilder<?> itemBuilder = getSubClassFromMeta(itemMeta, itemStack);
//        
//        itemBuilder.displayName(itemMeta.getDisplayName()).amount(itemStack.getAmount())
//                .addItemFlags(itemMeta.getItemFlags().toArray(new ItemFlag[0]))
//                .setLore(itemMeta.getLore()).setEnchants(itemMeta.getEnchants());
//        
//        NBT.get(itemStack, nbt -> {
//            itemBuilder.unbreakable(nbt.getBoolean("Unbreakable")).damage(nbt.getInteger("Damage"));
//        });
//        Map<String, AttributeModifierWrapper> attributeModifiers = getMCWrappers().getItemWrapper().getAttributeModifiers(itemStack);
//        if (attributeModifiers != null) {
//            itemBuilder.attributes.putAll(attributeModifiers);
//        }
//        
//        if (itemMeta instanceof Repairable repairable) {
//            itemBuilder.repairCost(repairable.getRepairCost());
//        }
//        
//        ReadableNBT compund = NBT.get(itemStack, nbt -> {
//            return nbt.getCompound("custom");
//        });
//        
//        if (compund != null) {
//            Set<String> keys = compund.getKeys();
//            if (keys != null && !keys.isEmpty()) {
//                for (String key : keys) {
//                    switch (compund.getType(key)) {
//                        case NBTTagByte -> itemBuilder.addCustomNBT(key, compund.getByte(key));
//                        case NBTTagShort -> itemBuilder.addCustomNBT(key, compund.getShort(key));
//                        case NBTTagInt -> itemBuilder.addCustomNBT(key, compund.getInteger(key));
//                        case NBTTagLong -> itemBuilder.addCustomNBT(key, compund.getLong(key));
//                        case NBTTagFloat -> itemBuilder.addCustomNBT(key, compund.getFloat(key));
//                        case NBTTagDouble -> itemBuilder.addCustomNBT(key, compund.getDouble(key));
//                        case NBTTagString -> itemBuilder.addCustomNBT(key, compund.getString(key));
//                        default -> {
//                        }
//                    }
//                }
//            }
//        }
//        
//        return itemBuilder;
//    }
}
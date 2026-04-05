package com.stardevllc.starcore;

import com.stardevllc.minecraft.itembuilder.BasicItemBuilder;
import com.stardevllc.minecraft.itembuilder.ItemBuilder;
import com.stardevllc.minecraft.v1_13.FishBucketBuilder;
import com.stardevllc.minecraft.v1_13_2.MapItemBuilder;
import com.stardevllc.minecraft.v1_14_4.*;
import com.stardevllc.minecraft.v1_16_1.BookItemBuilder;
import com.stardevllc.minecraft.v1_16_1.CompassItemBuilder;
import com.stardevllc.minecraft.v1_17_1.AxolotlItemBuilder;
import com.stardevllc.minecraft.v1_19_3.GoatHornBuilder;
import com.stardevllc.minecraft.v1_20_1.ArmorItemBuilder;
import com.stardevllc.minecraft.v1_21_1.OminousBottleBuilder;
import com.stardevllc.minecraft.v1_21_1.WritableBookBuilder;
import com.stardevllc.minecraft.v1_8.*;
import com.stardevllc.minecraft.smaterial.SMaterial;
import com.stardevllc.starlib.serialization.StarSerializable;
import com.stardevllc.starlib.serialization.StarSerialization;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public final class ItemBuilders {
    private static final Map<Class<? extends ItemMeta>, Class<? extends ItemBuilder<?, ?>>> META_TO_BUILDERS = new HashMap<>();
    
    private static <S extends StarSerializable & ConfigurationSerializable> void registerSerialization(Class<S> c) {
        StarSerialization.registerClass(c);
        ConfigurationSerialization.registerClass(c);
    }
    
    private static <M extends ItemMeta> void registerBuilder(Class<M> meta, Class<? extends ItemBuilder<?, M>> c) {
        try {
            ItemBuilders.mapMetaToBuilder(meta, c);
            ItemBuilders.registerSerialization(c);
        } catch (Throwable t) {}
    }
    
    public static void init() {
        //Due to how java class files are, the imports are not a major problem and this should be fine
        try {
            ItemBuilders.registerBuilder(BannerMeta.class, BannerItemBuilder.class);
            ItemBuilders.registerBuilder(EnchantmentStorageMeta.class, EnchantedBookBuilder.class);
            ItemBuilders.registerBuilder(FireworkMeta.class, FireworkItemBuilder.class);
            ItemBuilders.registerBuilder(FireworkEffectMeta.class, FireworkStarBuilder.class);
            ItemBuilders.registerBuilder(SkullMeta.class, SkullItemBuilder.class);
            ItemBuilders.registerBuilder(LeatherArmorMeta.class, LeatherArmorBuilder.class);
        } catch (Throwable e) {}
        
        try {
            ItemBuilders.registerBuilder(TropicalFishBucketMeta.class, FishBucketBuilder.class);
        } catch (Throwable e) {}
        
        try {
            ItemBuilders.registerBuilder(MapMeta.class, MapItemBuilder.class);
        } catch (Throwable e) {}
        
        try {
            ItemBuilders.registerBuilder(CrossbowMeta.class, CrossbowItemBuilder.class);
            ItemBuilders.registerBuilder(SuspiciousStewMeta.class, StewItemBuilder.class);
            ItemBuilders.registerBuilder(BlockDataMeta.class, BlockDataItemBuilder.class);
        } catch (Throwable e) {}
        
        try {
            ItemBuilders.registerBuilder(BookMeta.class, BookItemBuilder.class);
            ItemBuilders.registerBuilder(CompassMeta.class, CompassItemBuilder.class);
        } catch (Throwable e) {}
        
        try {
            ItemBuilders.registerBuilder(AxolotlBucketMeta.class, AxolotlItemBuilder.class);
        } catch (Throwable e) {}
        
        try {
            ItemBuilders.registerBuilder(MusicInstrumentMeta.class, GoatHornBuilder.class);
        } catch (Throwable e) {}
        
        try {
            ItemBuilders.registerBuilder(ArmorMeta.class, ArmorItemBuilder.class);
        } catch (Throwable e) {}
        
        try {
            ItemBuilders.registerBuilder(OminousBottleMeta.class, OminousBottleBuilder.class);
            ItemBuilders.registerBuilder(WritableBookMeta.class, WritableBookBuilder.class);
        } catch (Throwable e) {}
    }
    
    public static <M extends ItemMeta> void mapMetaToBuilder(Class<M> meta, Class<? extends ItemBuilder<?, M>> builder) {
        META_TO_BUILDERS.put(meta, builder);
    }
    
    public static <M extends ItemMeta, I extends ItemBuilder<I, M>> I of(Class<I> builderClass, SMaterial material) {
        return (I) of(material);
    }
    
    public static ItemBuilder<?, ?> of(SMaterial xMaterial) {
        Material material = xMaterial.parseMaterial();
        ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(material);
        
        if (itemMeta == null) {
            return new BasicItemBuilder(xMaterial);
        }
        
        Class<? extends ItemBuilder<?, ?>> builderClass = META_TO_BUILDERS.get(itemMeta.getClass());
        
        if (builderClass != null) {
            try {
                Constructor<? extends ItemBuilder<?, ?>> matConstructor = builderClass.getDeclaredConstructor(Material.class);
                matConstructor.setAccessible(true);
                return matConstructor.newInstance(material);
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
    
//    private static MCWrappers getMCWrappers() {
//        return Bukkit.getServicesManager().getRegistration(MCWrappers.class).getProvider();
//    }
    
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
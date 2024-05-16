package com.stardevllc.starcore.v1_19_3;

import com.stardevllc.starcore.item.ItemBuilder;
import com.stardevllc.starcore.xseries.XMaterial;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.Bukkit;
import org.bukkit.MusicInstrument;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MusicInstrumentMeta;

public class GoatHornBuilder extends ItemBuilder {
    
    static {
        ItemBuilder.META_TO_BUILDERS.put(MusicInstrumentMeta.class, GoatHornBuilder.class); //Will probably have to change this at some point
    }
    
    private MusicInstrument instrument;
    
    public GoatHornBuilder() {
        super(XMaterial.GOAT_HORN);
    }

    public GoatHornBuilder(MusicInstrument instrument) {
        instrument(instrument);
    }
    
    protected static GoatHornBuilder createFromItemStack(ItemStack itemStack) {
        GoatHornBuilder builder = new GoatHornBuilder();
        MusicInstrumentMeta meta = (MusicInstrumentMeta) itemStack.getItemMeta();
        builder.instrument(meta.getInstrument());
        return builder;
    }

    protected static GoatHornBuilder createFromConfig(Section section) {
        GoatHornBuilder builder = new GoatHornBuilder();
        builder.instrument(Bukkit.getRegistry(MusicInstrument.class).get(NamespacedKey.fromString(section.getString("instrument"))));
        return builder;
    }

    @Override
    public void saveToConfig(Section section) {
        super.saveToConfig(section);
        section.set("instrument", this.instrument.getKey());
    }
    
    public GoatHornBuilder instrument(MusicInstrument instrument) {
        this.instrument = instrument;
        return this;
    }

    @Override
    protected MusicInstrumentMeta createItemMeta() {
        MusicInstrumentMeta itemMeta = (MusicInstrumentMeta) super.createItemMeta();
        itemMeta.setInstrument(this.instrument);
        return itemMeta;
    }

    @Override
    public GoatHornBuilder clone() {
        GoatHornBuilder clone = (GoatHornBuilder) super.clone();
        clone.instrument = this.instrument;
        return clone;
    }
}

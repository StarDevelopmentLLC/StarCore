package com.stardevllc.minecraft.v1_19_3;

import com.stardevllc.minecraft.itembuilder.ItemBuilder;
import com.stardevllc.minecraft.smaterial.SMaterial;
import org.bukkit.MusicInstrument;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MusicInstrumentMeta;

public class GoatHornBuilder extends ItemBuilder<GoatHornBuilder, MusicInstrumentMeta> {
    
    private MusicInstrument instrument;
    
    public GoatHornBuilder() {
        super(SMaterial.GOAT_HORN);
    }
    
    public GoatHornBuilder(ItemStack itemStack) {
        super(itemStack);
        
        MusicInstrumentMeta itemMeta = (MusicInstrumentMeta) itemStack.getItemMeta();
        if (itemMeta != null) {
            instrument = itemMeta.getInstrument();
        }
    }
    
    public GoatHornBuilder(GoatHornBuilder builder) {
        super(builder);
        this.instrument = builder.instrument;
    }
    
    public GoatHornBuilder(MusicInstrument instrument) {
        instrument(instrument);
    }
    
    public GoatHornBuilder instrument(MusicInstrument instrument) {
        this.instrument = instrument;
        return this;
    }

    @Override
    protected MusicInstrumentMeta createItemMeta() {
        MusicInstrumentMeta itemMeta = super.createItemMeta();
        itemMeta.setInstrument(this.instrument);
        return itemMeta;
    }

    @Override
    public GoatHornBuilder clone() {
        return new GoatHornBuilder(this);
    }
}

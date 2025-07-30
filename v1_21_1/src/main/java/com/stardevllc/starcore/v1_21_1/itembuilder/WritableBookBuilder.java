package com.stardevllc.starcore.v1_21_1.itembuilder;

import com.stardevllc.starcore.api.itembuilder.ItemBuilder;
import com.stardevllc.starmclib.XMaterial;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.WritableBookMeta;

import java.util.LinkedList;
import java.util.List;

public class WritableBookBuilder extends ItemBuilder<WritableBookBuilder, WritableBookMeta> {

    protected List<String> pages = new LinkedList<>();
    
    public WritableBookBuilder() {
        super(XMaterial.WRITABLE_BOOK);
    }
    
    public WritableBookBuilder(ItemStack itemStack) {
        super(itemStack);
        
        WritableBookMeta itemMeta = (WritableBookMeta) itemStack.getItemMeta();
        if (itemMeta != null) {
            this.pages.addAll(itemMeta.getPages());
        }
    }
    
    public WritableBookBuilder(WritableBookBuilder builder) {
        super(builder);
        this.pages.addAll(builder.pages);
    }
    
    public WritableBookBuilder addPages(String... pages) {
        if (pages != null) {
            this.pages.addAll(List.of(pages));
        }
        
        return this;
    }
    
    public WritableBookBuilder addPage(int index, String page) {
        this.pages.add(index, page);
        return this;
    }
    
    public WritableBookBuilder pages(List<String> pages) {
        this.pages.addAll(pages);
        return this;
    }

    @Override
    protected WritableBookMeta createItemMeta() {
        WritableBookMeta itemMeta = super.createItemMeta();
        itemMeta.setPages(this.pages);
        return itemMeta;
    }
    
    @Override
    public WritableBookBuilder clone() {
        return new WritableBookBuilder(this);
    }
}

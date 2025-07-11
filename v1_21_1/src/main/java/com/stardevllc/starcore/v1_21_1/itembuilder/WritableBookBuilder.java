package com.stardevllc.starcore.v1_21_1.itembuilder;

import com.stardevllc.starcore.api.itembuilder.ItemBuilder;
import com.stardevllc.starmclib.XMaterial;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.WritableBookMeta;

import java.util.LinkedList;
import java.util.List;

public class WritableBookBuilder extends ItemBuilder {

    static {
        ItemBuilder.META_TO_BUILDERS.put(WritableBookMeta.class, WritableBookBuilder.class);
    }
    
    protected List<String> pages = new LinkedList<>();
    
    public WritableBookBuilder() {
    }

    public WritableBookBuilder(XMaterial material) {
        super(material);
    }
    
    protected static WritableBookBuilder createFromItemStack(ItemStack itemStack) {
        WritableBookBuilder itemBuilder = new WritableBookBuilder(XMaterial.matchXMaterial(itemStack));
        WritableBookMeta itemMeta = (WritableBookMeta) itemStack.getItemMeta();
        itemBuilder.pages(itemMeta.getPages());
        return itemBuilder;
    }

    protected static WritableBookBuilder createFromConfig(ConfigurationSection section) {
        WritableBookBuilder builder = new WritableBookBuilder();
        builder.pages(section.getStringList("pages"));
        return builder;
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
    public void saveToConfig(ConfigurationSection section) {
        super.saveToConfig(section);
        section.set("pages", this.pages);
    }

    @Override
    protected WritableBookMeta createItemMeta() {
        WritableBookMeta itemMeta = (WritableBookMeta) super.createItemMeta();
        itemMeta.setPages(this.pages);
        return itemMeta;
    }
}

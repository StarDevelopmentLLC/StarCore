package com.stardevllc.minecraft.v1_16_1;

import com.stardevllc.minecraft.itembuilder.ItemBuilder;
import com.stardevllc.minecraft.smaterial.SMaterial;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.BookMeta.Generation;

import java.util.LinkedList;
import java.util.List;

public class BookItemBuilder extends ItemBuilder<BookItemBuilder, BookMeta> {
    
    private String author;
    private List<BaseComponent[]> pages = new LinkedList<>();
    private Generation generation;
    private String title;
    
    public BookItemBuilder(SMaterial material) {
        super(material);
    }
    
    public BookItemBuilder(ItemStack itemStack) {
        super(itemStack);
        
        BookMeta itemMeta = (BookMeta) itemStack.getItemMeta();
        if (itemMeta != null) {
            this.author = itemMeta.getAuthor();
            this.pages.addAll(itemMeta.spigot().getPages());
            this.generation = itemMeta.getGeneration();
            this.title = itemMeta.getTitle();
        }
    }
    
    public BookItemBuilder(BookItemBuilder builder) {
        super(builder);
        this.author = builder.author;
        this.pages.addAll(builder.pages);
        this.generation = builder.generation;
        this.title = builder.title;
    }
    
    public BookItemBuilder addPage(String page) {
        this.pages.add(TextComponent.fromLegacyText(page));
        return this;
    }
    
    public BookItemBuilder setPages(List<String> pages) {
        this.pages.clear();
        pages.forEach(page -> this.pages.add(TextComponent.fromLegacyText(page)));
        return this;
    }
    
    public BookItemBuilder addPage(BaseComponent[] component) {
        this.pages.add(component);
        return this;
    }
    
    public BookItemBuilder setPagesComponents(List<BaseComponent[]> components) {
        this.pages.clear();
        this.pages.addAll(components);
        return this;
    }
    
    public BookItemBuilder author(String author) {
        this.author = author;
        return this;
    }
    
    public BookItemBuilder title(String title) {
        this.title = title;
        return this;
    }
    
    public BookItemBuilder generation(Generation generation) {
        this.generation = generation;
        return this;
    }

    @Override
    protected BookMeta createItemMeta() {
        BookMeta itemMeta = super.createItemMeta();
        itemMeta.setTitle(title);
        itemMeta.setAuthor(author);
        itemMeta.setGeneration(generation);
        itemMeta.spigot().setPages(this.pages);
        return itemMeta;
    }

    @Override
    public BookItemBuilder clone() {
        return new BookItemBuilder(this);
    }
}

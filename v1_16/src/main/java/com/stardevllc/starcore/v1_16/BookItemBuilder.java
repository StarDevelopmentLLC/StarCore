package com.stardevllc.starcore.v1_16;

import com.stardevllc.starcore.base.itembuilder.ItemBuilder;
import com.stardevllc.starcore.base.itembuilder.XMaterial;
import com.stardevllc.starcore.base.itembuilder.enums.BookType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.LinkedList;
import java.util.List;

public class BookItemBuilder extends ItemBuilder {
    
    static {
        ItemBuilder.META_TO_BUILDERS.put(BookMeta.class, BookItemBuilder.class);
    }
    
    private String author;
    private List<BaseComponent[]> pages = new LinkedList<>();
    private BookMeta.Generation generation;
    private String title;
    
    public BookItemBuilder(XMaterial material) {
        super(material);
    }
    
    protected BookItemBuilder() {
        
    }

    public BookItemBuilder(BookType bookType, String title, String author) {
        super(XMaterial.valueOf(bookType.name() + "_BOOK"));
        title(title).author(author);
    }

    protected static BookItemBuilder createFromItemStack(ItemStack itemStack) {
        BookItemBuilder itemBuilder = new BookItemBuilder();
        BookMeta itemMeta = (BookMeta) itemStack.getItemMeta();
        itemBuilder.setPagesComponents(itemMeta.spigot().getPages()).author(itemMeta.getAuthor()).title(itemMeta.getTitle()).generation(itemMeta.getGeneration());
        return itemBuilder;
    }

    protected static BookItemBuilder createFromConfig(ConfigurationSection section) {
        BookItemBuilder builder = new BookItemBuilder();
        builder.title(section.getString("title"));
        builder.author(section.getString("author"));
        builder.generation(BookMeta.Generation.valueOf(section.getString("generation")));
        ConfigurationSection pagesSection = section.getConfigurationSection("pages");
        if (pagesSection != null) {
            for (Object key : pagesSection.getKeys(false)) {
                builder.addPage(ComponentSerializer.parse(pagesSection.getString(key.toString())));
            }
        }
        return builder;
    }

    @Override
    public void saveToConfig(ConfigurationSection section) {
        super.saveToConfig(section);
        section.set("author", this.author);
        section.set("title", this.title);
        section.set("generation", this.generation.name());
        for (int i = 0; i < pages.size(); i++) {
            section.set("pages." + i, ComponentSerializer.toString(pages.get(i)));
        }
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
    
    public BookItemBuilder generation(BookMeta.Generation generation) {
        this.generation = generation;
        return this;
    }

    @Override
    protected BookMeta createItemMeta() {
        BookMeta itemMeta = (BookMeta) super.createItemMeta();
        itemMeta.setTitle(title);
        itemMeta.setAuthor(author);
        itemMeta.setGeneration(generation);
        itemMeta.spigot().setPages(this.pages);
        return itemMeta;
    }

    @Override
    public BookItemBuilder clone() {
        BookItemBuilder clone = (BookItemBuilder) super.clone();
        clone.title = this.title;
        clone.author = this.author;
        clone.pages.addAll(this.pages);
        return clone;
    }
}

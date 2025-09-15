package com.stardevllc.starcore.api.ui.element;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;

public class Element {
    
    protected Function<Player, ItemStack> iconCreator;
    protected boolean allowInsert;
    protected boolean isReplaceable;
    protected boolean deleteOnUpdate = true;
    
    public Element() {}
    
    public Element(Function<Player, ItemStack> iconCreator, boolean allowInsert, boolean isReplaceable, boolean deleteOnUpdate) {
        this.iconCreator = iconCreator;
        this.allowInsert = allowInsert;
        this.isReplaceable = isReplaceable;
        this.deleteOnUpdate = deleteOnUpdate;
    }
    
    public Element(Function<Player, ItemStack> iconCreator) {
        this.iconCreator = iconCreator;
    }

    public Element iconCreator(Function<Player, ItemStack> iconCreator) {
        this.iconCreator = iconCreator;
        return this;
    }

    public Function<Player, ItemStack> getIconCreator() {
        return iconCreator;
    }

    public boolean isReplaceable() {
        return isReplaceable;
    }

    public boolean isAllowInsert() {
        return allowInsert;
    }

    public boolean isDeleteOnUpdate() {
        return deleteOnUpdate;
    }

    public Element setAllowInsert(boolean allowInsert) {
        this.allowInsert = allowInsert;
        return this;
    }
    
    public Element setReplaceable(boolean replaceable) {
        isReplaceable = replaceable;
        return this;
    }

    public Element setDeleteOnUpdate(boolean deleteOnUpdate) {
        this.deleteOnUpdate = deleteOnUpdate;
        return this;
    }
}
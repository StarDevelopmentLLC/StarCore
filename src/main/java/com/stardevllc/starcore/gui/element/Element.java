package com.stardevllc.starcore.gui.element;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;

public class Element {
    
    protected Function<Player, ItemStack> iconCreator;
    protected boolean allowInsert;
    protected boolean isReplaceable;

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
    
    public Element setAllowInsert(boolean allowInsert) {
        this.allowInsert = allowInsert;
        return this;
    }
    
    public Element setReplaceable(boolean replaceable) {
        isReplaceable = replaceable;
        return this;
    }
}
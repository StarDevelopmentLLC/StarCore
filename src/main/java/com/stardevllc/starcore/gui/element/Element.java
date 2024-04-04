package com.stardevllc.starcore.gui.element;

import com.stardevllc.starcore.gui.gui.InventoryGUI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;

/**
 * Represents an Element in a Gui
 */
public class Element {
    
    protected Function<Player, ItemStack> iconCreator;
    protected boolean allowInsert;
    protected boolean isReplaceable;

    /**
     * The icon creator for this element.
     * @param iconCreator The fuction instance
     * @return Instance of this class (Builder style)
     */
    public Element iconCreator(Function<Player, ItemStack> iconCreator) {
        this.iconCreator = iconCreator;
        return this;
    }

    /**
     * @return The IconCreator instance
     */
    public Function<Player, ItemStack> getIconCreator() {
        return iconCreator;
    }

    /**
     * Future use, used in the {@link InventoryGUI#addElement(Element)} method
     * @return If this is a replaceable element
     */
    public boolean isReplaceable() {
        return isReplaceable;
    }

    /**
     * Future use
     * @return If items are allowed to be inserted into this element via a player
     */
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
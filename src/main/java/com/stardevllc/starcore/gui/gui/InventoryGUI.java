package com.stardevllc.starcore.gui.gui;

import com.stardevllc.starcore.gui.element.Element;
import com.stardevllc.starcore.gui.element.FillerElement;
import com.stardevllc.starcore.gui.element.button.Button;
import com.stardevllc.starcore.gui.handler.InventoryHandler;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.Map;
import java.util.TreeMap;

/**
 * A parent class for guis that provide some default behavior
 */
public class InventoryGUI implements InventoryHandler {
    
    protected final Inventory inventory;
    protected final Map<Integer, Slot> slots = new TreeMap<>();
    protected final int rows;

    /**
     * Constructs a new InventoryGUI
     * @param rows The amount of rows, 1-6
     * @param title The title of the Inventory - Only uses default Bukkit color code translation
     */
    public InventoryGUI(int rows, String title) {
        this.rows = rows;
        if (rows < 1 || rows > 6) {
            throw new IllegalArgumentException("Rows value is out of range, it can only be between 1 to 6.");
        }
        
        this.inventory = Bukkit.createInventory(null, rows * 9, ChatColor.translateAlternateColorCodes('&', title));
        for (int i = 0; i < rows * 9; i++) {
            slots.put(i, new Slot(i));
        }
    }

    /**
     * Sets an element in the Gui
     * @param index The index starting from 0
     * @param element The element to set
     */
    public void setElement(int index, Element element) {
        Slot slot = this.slots.get(index);
        if (slot != null) {
            slot.setElement(element);
        }
    }

    /**
     * Sets an element in the Gui
     * @param row The row of the element 0 to rows -1
     * @param column The column of the element 0 to 8
     * @param element The element
     */
    public void setElement(int row, int column, Element element) {
        setSlotValue(row, column, element);
    }

    /**
     * Removes an element
     * @param row The row of the element 0 to rows -1
     * @param column The column of the element 0 to 8
     */
    public void removeElement(int row, int column) {
        setSlotValue(row, column, null);
    }

    /**
     * Adds an element to the Gui, finding the first empty slot or the first slot with an element with the replacable flag set to true.
     * @param element The element to add
     */
    public void addElement(Element element) {
        for (Slot slot : this.slots.values()) {
            if (slot.getElement() == null) {
                slot.setElement(element);
                break;
            } else if (slot.getElement() instanceof FillerElement fillerElement) {
                if (fillerElement.isReplaceable()) {
                    slot.setElement(element);
                }
            }
        }
    }
    
    private void setSlotValue(int row, int column, Element value) {
        if (row < 0 || row >= this.rows) {
            throw new IllegalArgumentException("Invalid row value. Supports 0-" + (this.rows - 1));
        }
        
        if (column < 0 || column > 8) {
            throw new IllegalArgumentException("Invalid column value. Supports 0-8");
        }
        
        Slot slot = slots.get(row * 9 + column);
        slot.setElement(value);
    }

    /**
     * Method that populates the actual {@link Inventory}, called when it is opened by a player
     * @param player The player
     */
    public void decorate(Player player) {
        this.slots.forEach((index, slot) -> {
            if (slot.getElement() != null) {
                this.inventory.setItem(index, slot.getElement().getIconCreator().apply(player));
            } else {
                this.inventory.setItem(index, null);
            }
        });
    }

    /**
     * @return The Inventory Instance
     */
    public Inventory getInventory() {
        return inventory;
    }
    
    @Override
    public void onClick(InventoryClickEvent e) {
        e.setCancelled(true); //TODO This will have to be changed when the InsertElement is implemented
        Slot slot = this.slots.get(e.getRawSlot());
        if (slot == null) {
            return;
        }
        
        if (slot.getElement() == null) {
            return;
        }
        
        if (slot.getElement() instanceof Button button) {
            if (button.getEventConsumer() != null) {
                button.getEventConsumer().accept(e);
                button.playSound((Player) e.getWhoClicked());
            }
        }
    }
    
    @Override
    public void onDrag(InventoryDragEvent e) {
        
    }
    
    @Override
    public void onOpen(InventoryOpenEvent e) {
        decorate((Player) e.getPlayer());
    }
    
    @Override
    public void onClose(InventoryCloseEvent e) {
        
    }
}

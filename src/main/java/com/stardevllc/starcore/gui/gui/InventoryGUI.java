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

public class InventoryGUI implements InventoryHandler {
    
    protected final Inventory inventory;
    protected final Slot[] slots;
    protected final int rows;

    public InventoryGUI(int rows, String title) {
        this.rows = rows;
        if (rows < 1 || rows > 6) {
            throw new IllegalArgumentException("Rows value is out of range, it can only be between 1 to 6.");
        }
        
        this.inventory = Bukkit.createInventory(null, rows * 9, ChatColor.translateAlternateColorCodes('&', title));
        
        this.slots = new Slot[rows * 9];
        for (int i = 0; i < this.slots.length; i++) {
            slots[i] = new Slot(i);
        }
    }

    public void setElement(int index, Element element) {
        Slot slot = this.slots[index];
        if (slot != null) {
            slot.setElement(element);
        }
    }

    public void setElement(int row, int column, Element element) {
        setSlotValue(row, column, element);
    }

    public void removeElement(int row, int column) {
        setSlotValue(row, column, null);
    }

    public void addElement(Element element) {
        for (Slot slot : this.slots) {
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
        
        Slot slot = slots[row * 9 + column];
        slot.setElement(value);
    }

    public void decorate(Player player) {
        for (int index = 0; index < this.slots.length; index++) {
            if (slots[index].getElement() != null) {
                this.inventory.setItem(index, slots[index].getElement().getIconCreator().apply(player));
            } else {
                this.inventory.setItem(index, null);
            }
        }
    }

    public Inventory getInventory() {
        return inventory;
    }
    
    @Override
    public void onClick(InventoryClickEvent e) {
        if (e.getSlot() != e.getRawSlot()) {
            return;
        }
        
        e.setCancelled(true); //TODO This will have to be changed when the InsertElement is implemented
        
        Slot slot = this.slots[e.getRawSlot()];
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

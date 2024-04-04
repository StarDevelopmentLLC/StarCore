package com.stardevllc.starcore.gui.handler;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

/**
 * This class is a template for what a UI should handle. Methods should be self explanitory <br>
 * Checks are done before passing the event to the method to ensure that the proper inventory is being clicked in.
 */
public interface InventoryHandler {
    void onClick(InventoryClickEvent e);
    void onDrag(InventoryDragEvent e);
    void onOpen(InventoryOpenEvent e);
    void onClose(InventoryCloseEvent e);
}
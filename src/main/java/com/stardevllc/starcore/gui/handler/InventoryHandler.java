package com.stardevllc.starcore.gui.handler;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public interface InventoryHandler {
    void onClick(InventoryClickEvent e);
    void onDrag(InventoryDragEvent e);
    void onOpen(InventoryOpenEvent e);
    void onClose(InventoryCloseEvent e);
}
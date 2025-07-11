package com.stardevllc.starcore.api.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;

public class InventoryEvents implements Listener {
    @EventHandler
    public void onBrewEvent(BrewEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onCraftItem(CraftItemEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onFurnaceBurn(FurnaceBurnEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onFurnaceExtract(FurnaceExtractEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onFurnaceSmelt(FurnaceSmeltEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onInventory(InventoryEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onInventoryCreative(InventoryCreativeEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onInventoryInteract(InventoryInteractEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onInventoryPickupItem(InventoryPickupItemEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent e) {
        StarEvents.callEvent(e);
    }
}
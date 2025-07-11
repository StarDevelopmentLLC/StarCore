package com.stardevllc.starcore.v1_17_1.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceStartSmeltEvent;

public class InventoryEvents_1_17_1 implements Listener {
    @EventHandler
    public void onFurnaceStarSmelt(FurnaceStartSmeltEvent e) {
        StarEvents.callEvent(e);
    }
}
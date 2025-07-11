package com.stardevllc.starcore.v1_19_3.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareGrindstoneEvent;

public class InventoryEvents_1_19_3 implements Listener {
    @EventHandler
    public void onPrepareGrindstone(PrepareGrindstoneEvent e) {
        StarEvents.callEvent(e);
    }
}
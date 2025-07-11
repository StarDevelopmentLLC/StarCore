package com.stardevllc.starcore.v1_16_1.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareSmithingEvent;

public class InventoryEvents_1_16_1 implements Listener {
    @EventHandler
    public void onPrepareSmithing(PrepareSmithingEvent e) {
        StarEvents.callEvent(e);
    }
}
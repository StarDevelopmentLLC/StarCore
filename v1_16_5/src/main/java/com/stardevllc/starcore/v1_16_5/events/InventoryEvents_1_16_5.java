package com.stardevllc.starcore.v1_16_5.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.SmithItemEvent;

public class InventoryEvents_1_16_5 implements Listener {
    @EventHandler
    public void onSmithItem(SmithItemEvent e) {
        StarEvents.callEvent(e);
    }
}
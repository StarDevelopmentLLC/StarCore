package com.stardevllc.starcore.v1_11_2.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewingStandFuelEvent;

public class InventoryEvents_1_11_2 implements Listener {
    @EventHandler
    public void onBrewingStandFuel(BrewingStandFuelEvent e) {
        StarEvents.callEvent(e);
    }
}
package com.stardevllc.starcore.v1_12_2.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class EntityEvents_1_12_2 implements Listener {
    @EventHandler
    public void onEntityItemPickup(EntityPickupItemEvent e) {
        StarEvents.callEvent(e);
    }
}
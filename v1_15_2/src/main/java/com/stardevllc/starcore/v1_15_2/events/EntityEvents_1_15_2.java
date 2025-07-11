package com.stardevllc.starcore.v1_15_2.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityEnterBlockEvent;

public class EntityEvents_1_15_2 implements Listener {
    @EventHandler
    public void onEntityEnterBlock(EntityEnterBlockEvent e) {
        StarEvents.callEvent(e);
    }
}
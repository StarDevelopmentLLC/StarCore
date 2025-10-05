package com.stardevllc.starcore.v1_21_6.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRemoveEvent;

public class EntityEvents_1_21_6 implements Listener {
    @EventHandler
    public void onEntityRemove(EntityRemoveEvent e) {
        StarEvents.callEvent(e);
    }
}
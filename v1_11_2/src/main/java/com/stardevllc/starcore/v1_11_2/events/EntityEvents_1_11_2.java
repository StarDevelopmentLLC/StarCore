package com.stardevllc.starcore.v1_11_2.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;

public class EntityEvents_1_11_2 implements Listener {
    @EventHandler
    public void onEntityResurrect(EntityResurrectEvent e) {
        StarEvents.callEvent(e);
    }
}
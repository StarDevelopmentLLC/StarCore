package com.stardevllc.starcore.v1_10_2.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.bukkit.event.entity.EntityBreedEvent;

public class EntityEvents_1_10_2 implements Listener {
    @EventHandler
    public void onEntityAirChange(EntityAirChangeEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onEntityBreed(EntityBreedEvent e) {
        StarEvents.callEvent(e);
    }
}
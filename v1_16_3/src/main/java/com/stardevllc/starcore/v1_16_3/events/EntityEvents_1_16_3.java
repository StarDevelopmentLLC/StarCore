package com.stardevllc.starcore.v1_16_3.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ArrowBodyCountChangeEvent;
import org.bukkit.event.entity.EntitySpellCastEvent;

public class EntityEvents_1_16_3 implements Listener {
    @EventHandler
    public void onArrowBodyCountChange(ArrowBodyCountChangeEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onEntitySpellCast(EntitySpellCastEvent e) {
        StarEvents.callEvent(e);
    }
}
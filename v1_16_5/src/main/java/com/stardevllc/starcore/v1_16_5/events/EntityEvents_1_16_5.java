package com.stardevllc.starcore.v1_16_5.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExhaustionEvent;
import org.bukkit.event.entity.PiglinBarterEvent;

public class EntityEvents_1_16_5 implements Listener {
    @EventHandler
    public void onEntityExhaustion(EntityExhaustionEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPiglinBarter(PiglinBarterEvent e) {
        StarEvents.callEvent(e);
    }
}
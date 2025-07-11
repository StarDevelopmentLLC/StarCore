package com.stardevllc.starcore.v1_13_2.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.BatToggleSleepEvent;
import org.bukkit.event.entity.EntityTransformEvent;

public class EntityEvents_1_13_2 implements Listener {
    @EventHandler
    public void onBatToggleSleep(BatToggleSleepEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onEntityTransform(EntityTransformEvent e) {
        StarEvents.callEvent(e);
    }
}

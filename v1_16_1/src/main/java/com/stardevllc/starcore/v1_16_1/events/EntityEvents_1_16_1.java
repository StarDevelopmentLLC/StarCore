package com.stardevllc.starcore.v1_16_1.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityEnterLoveModeEvent;
import org.bukkit.event.entity.StriderTemperatureChangeEvent;

public class EntityEvents_1_16_1 implements Listener {
    @EventHandler
    public void onEntityEnterLoveMode(EntityEnterLoveModeEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onStriderTemperatureChange(StriderTemperatureChangeEvent e) {
        StarEvents.callEvent(e);
    }
}
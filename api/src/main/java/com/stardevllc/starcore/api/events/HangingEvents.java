package com.stardevllc.starcore.api.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.*;

public class HangingEvents implements Listener {
    @EventHandler
    public void onHangingBreakByEntity(HangingBreakByEntityEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onHangingBreak(HangingBreakEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onHangingPlace(HangingPlaceEvent e) {
        StarEvents.callEvent(e);
    }
}
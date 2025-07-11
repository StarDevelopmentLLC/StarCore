package com.stardevllc.starcore.api.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.*;

public class VehicleEvents implements Listener {
    @EventHandler
    public void onVehicleBlockCollision(VehicleBlockCollisionEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onVehicleDamage(VehicleDamageEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onVehicleDestroy(VehicleDestroyEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onVehicleEnter(VehicleEnterEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onVehicleEntityCollision(VehicleEntityCollisionEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onVehicleExit(VehicleExitEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onVehicleMove(VehicleMoveEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onVehicleUpdate(VehicleUpdateEvent e) {
        StarEvents.callEvent(e);
    }
}
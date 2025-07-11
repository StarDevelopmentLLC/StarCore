package com.stardevllc.starcore.v1_13.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;

public class EntityEvents_1_13 implements Listener {
    @EventHandler
    public void onEntityDropItem(EntityDropItemEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onEntityPotionEffect(EntityPotionEffectEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onEntityToggleSwim(EntityToggleSwimEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPigZombieAnger(PigZombieAngerEvent e) {
        StarEvents.callEvent(e);
    }
}
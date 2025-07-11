package com.stardevllc.starcore.v1_14_4.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPoseChangeEvent;
import org.bukkit.event.entity.VillagerCareerChangeEvent;

public class EntityEvents_1_14_4 implements Listener {
    @EventHandler
    public void onEntityPoseChange(EntityPoseChangeEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onVillageCareerChange(VillagerCareerChangeEvent e) {
        StarEvents.callEvent(e);
    }
}
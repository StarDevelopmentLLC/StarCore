package com.stardevllc.starcore.v1_21_1.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.TrialSpawnerSpawnEvent;

public class EntityEvents_1_21_1 implements Listener {
    @EventHandler
    public void onTrailSpawnerSpawn(TrialSpawnerSpawnEvent e) {
        StarEvents.callEvent(e);
    }
}
package com.stardevllc.starcore.v1_20_1.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;

public class EntityEvents_1_20_1 implements Listener {
    @EventHandler
    public void onSpawnerSpawn(SpawnerSpawnEvent e) {
        StarEvents.callEvent(e);
    }
}
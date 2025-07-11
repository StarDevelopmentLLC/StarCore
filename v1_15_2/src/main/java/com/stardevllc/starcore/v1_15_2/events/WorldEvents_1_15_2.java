package com.stardevllc.starcore.v1_15_2.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.event.world.TimeSkipEvent;

public class WorldEvents_1_15_2 implements Listener {
    @EventHandler
    public void onTimeSkip(TimeSkipEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onLootGenerate(LootGenerateEvent e) {
        StarEvents.callEvent(e);
    }
}
package com.stardevllc.starcore.v1_8_8.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FireworkExplodeEvent;
import org.bukkit.event.entity.ItemMergeEvent;

public class EntityEvents_1_8_8 implements Listener {
    @EventHandler
    public void onFireworkExplode(FireworkExplodeEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onItemMerge(ItemMergeEvent e) {
        StarEvents.callEvent(e);
    }
}
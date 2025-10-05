package com.stardevllc.starcore.v1_21_4.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CrafterCraftEvent;
import org.bukkit.event.entity.VillagerReputationChangeEvent;

public class EntityEvents_1_21_4 implements Listener {
    @EventHandler
    public void onVillagerReputationChange(VillagerReputationChangeEvent e) {
        StarEvents.callEvent(e);
    }
}

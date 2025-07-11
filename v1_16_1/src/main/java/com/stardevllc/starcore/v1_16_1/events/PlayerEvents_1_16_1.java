package com.stardevllc.starcore.v1_16_1.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerHarvestBlockEvent;

public class PlayerEvents_1_16_1 implements Listener {
    @EventHandler
    public void onPlayerHarvestBlock(PlayerHarvestBlockEvent e) {
        StarEvents.callEvent(e);
    }
}
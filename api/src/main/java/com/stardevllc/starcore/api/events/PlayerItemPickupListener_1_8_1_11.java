package com.stardevllc.starcore.api.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerItemPickupListener_1_8_1_11 implements Listener {
    @EventHandler
    public void onPlayerItemPickup(PlayerPickupItemEvent e) {
        StarEvents.callEvent(e);
    }
}
package com.stardevllc.starcore.v1_8_8.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

public class PlayerEvents_1_8_8 implements Listener {
    @EventHandler
    public void onPlayerResourcePackStatus(PlayerResourcePackStatusEvent e) {
        StarEvents.callEvent(e);
    }
}
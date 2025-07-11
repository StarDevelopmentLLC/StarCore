package com.stardevllc.starcore.v1_21_3.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInputEvent;

public class PlayerEvents_1_21_3 implements Listener {
    @EventHandler
    public void onPlayerInput(PlayerInputEvent e) {
        StarEvents.callEvent(e);
    }
}
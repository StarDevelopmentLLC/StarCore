package com.stardevllc.starcore.v1_9_4.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedMainHandEvent;

public class PlayerEvents_1_9_4 implements Listener {
    @EventHandler
    public void onPlayerChangedMainHand(PlayerChangedMainHandEvent e) {
        StarEvents.callEvent(e);
    }
}
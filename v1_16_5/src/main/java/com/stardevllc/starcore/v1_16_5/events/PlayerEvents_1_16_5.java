package com.stardevllc.starcore.v1_16_5.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEntityEvent;

public class PlayerEvents_1_16_5 implements Listener {
    @EventHandler
    public void onPlayerBucketEntity(PlayerBucketEntityEvent e) {
        StarEvents.callEvent(e);
    }
}
package com.stardevllc.starcore.v1_13_2.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerRecipeDiscoverEvent;

public class PlayerEvents_1_13_2 implements Listener {
    @EventHandler
    public void onPlayerRecipeDiscover(PlayerRecipeDiscoverEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerItemDamage(PlayerItemDamageEvent e) {
        StarEvents.callEvent(e);
    }
}
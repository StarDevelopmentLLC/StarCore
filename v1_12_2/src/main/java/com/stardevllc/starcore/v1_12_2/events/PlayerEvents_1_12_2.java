package com.stardevllc.starcore.v1_12_2.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class PlayerEvents_1_12_2 implements Listener {
    @EventHandler
    public void onPlayerItemMend(PlayerItemMendEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerAdvancementDone(PlayerAdvancementDoneEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerLocaleChangeEvent(PlayerLocaleChangeEvent e) {
        StarEvents.callEvent(e);
    }
}
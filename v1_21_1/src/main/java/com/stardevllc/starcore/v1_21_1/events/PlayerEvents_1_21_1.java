package com.stardevllc.starcore.v1_21_1.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class PlayerEvents_1_21_1 implements Listener {
    @EventHandler
    public void onPlayerExpCooldownChange(PlayerExpCooldownChangeEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerLinksSend(PlayerLinksSendEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerSignOpen(PlayerSignOpenEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerSpawnChange(PlayerSpawnChangeEvent e) {
        StarEvents.callEvent(e);
    }
}
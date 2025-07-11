package com.stardevllc.starcore.v1_13.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerRiptideEvent;

public class PlayerEvents_1_13 implements Listener {
    @EventHandler
    public void onPlayerCommandSend(PlayerCommandSendEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerRiptide(PlayerRiptideEvent e) {
        StarEvents.callEvent(e);
    }
}
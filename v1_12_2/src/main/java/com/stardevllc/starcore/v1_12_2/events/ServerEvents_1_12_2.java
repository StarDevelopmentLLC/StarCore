package com.stardevllc.starcore.v1_12_2.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.BroadcastMessageEvent;

public class ServerEvents_1_12_2 implements Listener {
    @EventHandler
    public void onBroadcastMessage(BroadcastMessageEvent e) {
        StarEvents.callEvent(e);
    }
}
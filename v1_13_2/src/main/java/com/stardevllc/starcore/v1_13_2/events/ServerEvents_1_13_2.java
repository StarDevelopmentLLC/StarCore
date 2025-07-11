package com.stardevllc.starcore.v1_13_2.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

public class ServerEvents_1_13_2 implements Listener {
    @EventHandler
    public void onServerLoad(ServerLoadEvent e) {
        StarEvents.callEvent(e);
    }
}
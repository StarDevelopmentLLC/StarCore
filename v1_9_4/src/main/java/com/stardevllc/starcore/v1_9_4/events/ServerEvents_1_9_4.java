package com.stardevllc.starcore.v1_9_4.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

public class ServerEvents_1_9_4 implements Listener {
    @EventHandler
    public void onTabComplete(TabCompleteEvent e) {
        StarEvents.callEvent(e);
    }
}
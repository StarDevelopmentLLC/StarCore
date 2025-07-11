package com.stardevllc.starcore.api.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCreatePortalEvent;

public class EntityCreatePortalListener_1_8_1_14_4 implements Listener {
    @EventHandler
    public void onEntityCreatePortal(EntityCreatePortalEvent e) {
        StarEvents.callEvent(e);
    }
}
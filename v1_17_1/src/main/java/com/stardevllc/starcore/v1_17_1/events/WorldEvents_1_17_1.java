package com.stardevllc.starcore.v1_17_1.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.*;

public class WorldEvents_1_17_1 implements Listener {
    @EventHandler
    public void onEntitiesLoad(EntitiesLoadEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onEntitiesUnload(EntitiesUnloadEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onGenericGameEvent(GenericGameEvent e) {
        StarEvents.callEvent(e);
    }
}
package com.stardevllc.starcore.api;

import com.stardevllc.starlib.eventbus.IEventBus;
import com.stardevllc.starmclib.StarMCLib;
import org.bukkit.event.Event;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class StarEvents {
    //Map to control the same instance of the event firing multiple times. The long is to remove it from the map
    private static final Map<Event, Long> recentEvents = new ConcurrentHashMap<>();
    
    public static void callEvent(Event event) {
        if (recentEvents.containsKey(event)) {
            return;
        }
        
        StarMCLib.GLOBAL_BUKKIT_EVENT_BUS.post(event);
        recentEvents.put(event, System.currentTimeMillis());
        //Remove the event instance after 1 second
        recentEvents.entrySet().removeIf(entry -> System.currentTimeMillis() >= entry.getValue() + 1000L);
    }
    
    public static void addChildBus(IEventBus<Object> eventBus) {
        StarMCLib.GLOBAL_BUKKIT_EVENT_BUS.addChildBus(eventBus);
    }
}

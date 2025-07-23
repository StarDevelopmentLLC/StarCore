package com.stardevllc.starcore.api;

import com.stardevllc.starlib.eventbus.IEventBus;
import com.stardevllc.starlib.eventbus.impl.SimpleEventBus;
import org.bukkit.event.Event;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class StarEvents {
    private static final IEventBus<Event> spigotEventBus = new SimpleEventBus<>();
    
    private static final Set<IEventBus<Object>> subBusses = new HashSet<>();
    
    //Map to control the same instance of the event firing multiple times. The long is to remove it from the map
    private static final Map<Event, Long> recentEvents = new ConcurrentHashMap<>();
    
    public static void callEvent(Event event) {
        if (recentEvents.containsKey(event)) {
            return;
        }
        
        spigotEventBus.post(event);
        for (IEventBus<Object> subBuss : subBusses) {
            try {
                subBuss.post(event);
            } catch (Exception e) {}
        }
        
        recentEvents.put(event, System.currentTimeMillis());
        
        //Remove the event instance after 5 seconds
        recentEvents.entrySet().removeIf(entry -> System.currentTimeMillis() >= entry.getValue() + 5000L);
    }
    
    public static void subscribe(IEventBus<Object> eventBus) {
        subBusses.add(eventBus);
    }
}

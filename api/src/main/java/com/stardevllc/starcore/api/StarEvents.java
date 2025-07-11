package com.stardevllc.starcore.api;

import com.stardevllc.eventbus.IEventBus;
import com.stardevllc.eventbus.impl.SimpleEventBus;
import org.bukkit.event.Event;

import java.util.HashSet;
import java.util.Set;

public final class StarEvents {
    private static final IEventBus<Event> spigotEventBus = new SimpleEventBus<>();
    
    private static final Set<IEventBus<Object>> subBusses = new HashSet<>();
    
    public static void callEvent(Event event) {
        spigotEventBus.post(event);
        for (IEventBus<Object> subBuss : subBusses) {
            try {
                subBuss.post(event);
            } catch (Exception e) {}
        }
    }
    
    public static void subscribe(IEventBus<Object> eventBus) {
        subBusses.add(eventBus);
    }
}

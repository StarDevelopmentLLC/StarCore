package com.stardevllc.minecraft.starevents;

import com.stardevllc.starlib.event.bus.SubscribeEvent;
import org.bukkit.event.Event;

/**
 * Represents a listener for Bukkit Events
 * @param <E> The Event Type
 */
@SubscribeEvent
@FunctionalInterface
public interface EventListener<E extends Event> {
    /**
     * Called when a change occurs
     * @param event The event information
     */
    void onEvent(E event);
    
    /**
     * Registers an EventListener to the EventBus in StarEvents. Just an alternative to {@link StarEvents#registerListener(Class, EventListener)}
     * @param eventType The event class
     * @param listener The listener
     * @param <E> The event type
     */
    static <E extends Event> void register(Class<E> eventType, EventListener<E> listener) {
        StarEvents.registerListener(eventType, listener);
    }
}

package com.stardevllc.minecraft.starevents;

import com.stardevllc.starlib.event.bus.ReflectionEventBus;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This is a bus used for StarEvents related purposes that prevents duplicate events from triggering as multiple child events might be fired for the same event
 */
public class BukkitEventBus extends ReflectionEventBus {
    
    private final Map<Event, Instant> recentEvents = new ConcurrentHashMap<>();
    
    /**
     * Constructs a new BukkitEventBus
     */
    public BukkitEventBus() {
        super(Event.class);
        this.addCancelHandler(Cancellable.class, Cancellable::isCancelled);
        
        Thread.ofVirtual().start(() -> {
            while (true) {
                if (StarEvents.getPlugin() == null) {
                    continue;
                }
                
                Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(StarEvents.getPlugin(), () -> {
                    Instant now = Instant.now();
                    new HashMap<>(recentEvents).forEach((event, expire) -> {
                        if (now.isAfter(expire)) {
                            recentEvents.remove(event);
                        }
                    });
                }, 1L, 600L);
                break;
            }
        });
    }
    
    @Override
    public <E> E post(E e) {
        if (!e.getClass().isAssignableFrom(Event.class)) {
            return e;
        }

        Event event = (Event) e;

        if (recentEvents.containsKey(event)) {
            return e;
        }

        Instant currentTime = Instant.now();
        recentEvents.put(event, currentTime.plusSeconds(1));
        return super.post(e);
    }
}
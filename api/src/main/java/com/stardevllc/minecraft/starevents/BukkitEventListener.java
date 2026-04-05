package com.stardevllc.minecraft.starevents;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;

/**
 * Represents a {@link Listener} to have a base class for code generation
 */
public class BukkitEventListener implements Listener {
    /**
     * Creates a new BukkitEventListener
     */
    public BukkitEventListener() {}
    
    protected void handleEvent(Event e) {
        StarEvents.BUS.post(e);
    }
}
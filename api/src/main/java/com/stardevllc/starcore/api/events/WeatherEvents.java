package com.stardevllc.starcore.api.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.*;

public class WeatherEvents implements Listener {
    @EventHandler
    public void onLightningStrike(LightningStrikeEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onThunderChange(ThunderChangeEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        StarEvents.callEvent(e);
    }
}
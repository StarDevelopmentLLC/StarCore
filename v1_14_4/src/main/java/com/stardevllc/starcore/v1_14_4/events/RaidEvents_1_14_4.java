package com.stardevllc.starcore.v1_14_4.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.raid.*;

public class RaidEvents_1_14_4 implements Listener {
    @EventHandler
    public void onRaidFinish(RaidFinishEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onRaidSpawnWave(RaidSpawnWaveEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onRaidStop(RaidStopEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onRaidTrigger(RaidTriggerEvent e) {
        StarEvents.callEvent(e);
    }
}
package com.stardevllc.starcore.api.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.*;

public class WorldEvents implements Listener {
    @EventHandler
    public void onChunkLoad(ChunkLoadEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onChunkPopulate(ChunkPopulateEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onChunkUnload(ChunkLoadEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPortalCreate(PortalCreateEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onSpawnChange(SpawnChangeEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onStructureGrow(StructureGrowEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onWorldInit(WorldInitEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onWorldLoad(WorldLoadEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onWorldSave(WorldSaveEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onWorldUnload(WorldUnloadEvent e) {
        StarEvents.callEvent(e);
    }
}
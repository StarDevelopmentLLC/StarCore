package com.stardevllc.starcore.v1_14_4.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;

public class BlockEvents_1_14_4 implements Listener {
    @EventHandler
    public void onBlockCook(BlockCookEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onBlockShearEntity(BlockShearEntityEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onBlockDropItem(BlockDropItemEvent e) {
        StarEvents.callEvent(e);
    }
}
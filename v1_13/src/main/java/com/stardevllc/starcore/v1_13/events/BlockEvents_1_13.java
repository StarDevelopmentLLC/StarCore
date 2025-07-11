package com.stardevllc.starcore.v1_13.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFertilizeEvent;
import org.bukkit.event.block.SpongeAbsorbEvent;

public class BlockEvents_1_13 implements Listener {
    @EventHandler
    public void onBlockFertilize(BlockFertilizeEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onSpongeAbsorb(SpongeAbsorbEvent e) {
        StarEvents.callEvent(e);
    }
}
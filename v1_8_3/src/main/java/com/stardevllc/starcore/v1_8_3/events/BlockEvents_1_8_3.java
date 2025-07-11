package com.stardevllc.starcore.v1_8_3.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;

public class BlockEvents_1_8_3 implements Listener {
    @EventHandler
    public void onBlockExplode(BlockExplodeEvent e) {
        StarEvents.callEvent(e);
    }
}
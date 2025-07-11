package com.stardevllc.starcore.v1_18_1.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageAbortEvent;

public class BlockEvents_1_18_1 implements Listener {
    @EventHandler
    public void onBlockDamageAbort(BlockDamageAbortEvent e) {
        StarEvents.callEvent(e);
    }
}
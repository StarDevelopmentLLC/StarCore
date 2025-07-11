package com.stardevllc.starcore.v1_17_1.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockReceiveGameEvent;

public class BlockEvents_1_17_1 implements Listener {
    @EventHandler
    public void onBlockReceiveGameEvent(BlockReceiveGameEvent e) {
        StarEvents.callEvent(e);
    }
}
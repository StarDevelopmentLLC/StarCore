package com.stardevllc.starcore.v1_21_4.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CrafterCraftEvent;

public class BlockEvents_1_21_4 implements Listener {
    @EventHandler
    public void onCrafterCraft(CrafterCraftEvent e) {
        StarEvents.callEvent(e);
    }
}

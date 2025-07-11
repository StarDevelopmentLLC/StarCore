package com.stardevllc.starcore.v1_21_1.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;

public class BlockEvents_1_21_1 implements Listener {
    @EventHandler
    public void onBlockDispenseLoot(BlockDispenseLootEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onCrafterCraft(CrafterCraftEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onVaultDisplayItem(VaultDisplayItemEvent e) {
        StarEvents.callEvent(e);
    }
}
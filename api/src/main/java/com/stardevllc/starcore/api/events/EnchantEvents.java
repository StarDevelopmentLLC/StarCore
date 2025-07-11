package com.stardevllc.starcore.api.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

public class EnchantEvents implements Listener {
    @EventHandler
    public void onEnchantItem(EnchantItemEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPrepareItemEnchant(PrepareItemEnchantEvent e) {
        StarEvents.callEvent(e);
    }
}

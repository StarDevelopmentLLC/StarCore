package com.stardevllc.starcore.listener;

import com.stardevllc.starcore.StarCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
    
    public StarCore plugin;
    
    public PlayerListener(StarCore plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.getPlayerCache().addEntry(event.getPlayer().getUniqueId(), event.getPlayer().getName());
    }
}

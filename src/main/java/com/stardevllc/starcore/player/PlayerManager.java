package com.stardevllc.starcore.player;

import com.stardevllc.config.Section;
import com.stardevllc.mojang.MojangAPI;
import com.stardevllc.mojang.MojangProfile;
import com.stardevllc.registry.UUIDRegistry;
import com.stardevllc.starcore.StarCore;
import com.stardevllc.starcore.config.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.util.*;

public class PlayerManager implements Listener {
    private final UUIDRegistry<StarPlayer> playerRegistry = new UUIDRegistry.Builder<StarPlayer>().keyRetriever(StarPlayer::getUniqueId).build();

    private Configuration playersConfig;
    
    private StarCore plugin;

    public PlayerManager(StarCore plugin) {
        this.plugin = plugin;
        if (plugin.getMainConfig().getBoolean("save-player-info")) {
            playersConfig = new Configuration(new File(plugin.getDataFolder(), "players.yml"));
        }
    }

    public void addPlayer(StarPlayer player) {
        playerRegistry.put(player.getUniqueId(), player);
    }
    
    public StarPlayer getPlayer(UUID uuid) {
        if (this.playerRegistry.contains(uuid)) {
            StarPlayer starPlayer = playerRegistry.get(uuid);
            if (plugin.getMainConfig().getBoolean("use-mojang-api")) {
                if (starPlayer.getMojangProfile() == null) {
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                        MojangProfile profile = MojangAPI.getProfile(uuid);
                        StarPlayer sp = playerRegistry.get(uuid);
                        if (sp != null) {
                            sp.setMojangProfile(profile);
                        }
                    });
                }
            }
            
            return starPlayer;
        }
        
        return null;
    }

    public void save() {
        if (this.playersConfig == null) {
            return;
        }

        for (Map.Entry<UUID, StarPlayer> entry : this.playerRegistry.entrySet()) {
            this.playersConfig.set("players." + entry.getKey().toString(), entry.getValue().serialize());
        }

        this.playersConfig.save();
    }

    public void load() {
        if (this.playersConfig == null) {
            return;
        }

        if (!this.playersConfig.contains("players")) {
            return;
        }
        
        Section playersSection = this.playersConfig.getConfigurationSection("players");
        if (playersSection != null) {
            for (Object key : playersSection.getKeys()) {
                Section dataSection = playersSection.getSection(key.toString());
                if (dataSection != null) {
                    Map<String, Object> serialized = new HashMap<>();
                    for (Object dataKey : dataSection.getKeys()) {
                        serialized.put(dataKey.toString(), dataSection.get(dataKey.toString()));    
                    }

                    this.addPlayer(new StarPlayer(serialized));
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!this.playerRegistry.contains(e.getPlayer().getUniqueId())) {
            this.playerRegistry.register(new StarPlayer(e.getPlayer()));        
        }

        StarPlayer starPlayer = getPlayer(e.getPlayer().getUniqueId());
        starPlayer.setLastLogin(System.currentTimeMillis());
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        StarPlayer starPlayer = this.playerRegistry.get(e.getPlayer().getUniqueId());
        starPlayer.setLastLogout(System.currentTimeMillis());
        starPlayer.setPlaytime(starPlayer.getPlaytime() + (starPlayer.getLastLogout() - starPlayer.getLastLogin()));
    }
}

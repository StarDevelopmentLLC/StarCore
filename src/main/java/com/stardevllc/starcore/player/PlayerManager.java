package com.stardevllc.starcore.player;

import com.stardevllc.starcore.StarCore;
import com.stardevllc.starcore.config.Config;
import com.stardevllc.starlib.registry.UUIDRegistry;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager implements Listener {
    private final UUIDRegistry<StarPlayer> playerRegistry = new UUIDRegistry.Builder<StarPlayer>().keyRetriever(StarPlayer::getUniqueId).build();

    private Config playersConfig;

    public PlayerManager(StarCore plugin) {
        if (plugin.getMainConfig().getBoolean("save-player-info")) {
            playersConfig = new Config(new File(plugin.getDataFolder(), "players.yml"));
        }
    }

    public void addPlayer(StarPlayer player) {
        playerRegistry.put(player.getUniqueId(), player);
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
        
        Section playersSection = this.playersConfig.getSection("players");
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

        StarPlayer starPlayer = this.playerRegistry.get(e.getPlayer().getUniqueId());
        starPlayer.setLastLogin(System.currentTimeMillis());
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        StarPlayer starPlayer = this.playerRegistry.get(e.getPlayer().getUniqueId());
        starPlayer.setLastLogin(System.currentTimeMillis());
        starPlayer.setPlaytime(starPlayer.getPlaytime() + (starPlayer.getLastLogout() - starPlayer.getLastLogin()));
    }
}

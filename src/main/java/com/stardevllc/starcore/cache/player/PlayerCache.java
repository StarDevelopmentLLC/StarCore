package com.stardevllc.starcore.cache.player;

import com.stardevllc.starcore.config.Config;
import dev.dejvokep.boostedyaml.block.implementation.Section;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerCache {
    private File file;
    private Config playersDB;
    
    private final Map<UUID, CachedPlayer> players = new HashMap<>();
    
    public PlayerCache(File file) {
        this.file = file;
    }
    
    public CachedPlayer getPlayer(UUID uuid) {
        return players.get(uuid);
    }
    
    public void addPlayer(CachedPlayer player) {
        if (player != null) {
            players.put(player.getUniqueId(), player);
        }
    }
    
    public String getPlayerName(UUID uuid) {
        CachedPlayer player = getPlayer(uuid);
        if (player != null) {
            return player.getName();
        }
        
        return "";
    }
    
    public UUID getPlayerUniqueId(String playerName) {
        for (Map.Entry<UUID, CachedPlayer> entry : this.players.entrySet()) {
            if (entry.getValue() != null && entry.getValue().getName().equals(playerName)) {
                return entry.getKey();
            }
        }
        
        return null;
    }

    public Map<UUID, CachedPlayer> getPlayers() {
        return new HashMap<>(players);
    }
    
    public void save() {
        if (this.playersDB != null) {
            for (Map.Entry<UUID, CachedPlayer> entry : this.players.entrySet()) {
                this.playersDB.set("players." + entry.getKey().toString(), entry.getValue().getName());
            }

            this.playersDB.save();
        }
    }

    public void load() {
        this.playersDB = new Config(file);
        if (this.playersDB.contains("players")) {
            Section playersSection = this.playersDB.getSection("players");
            if (playersSection != null) {
                for (Object key : playersSection.getKeys()) {
                    this.addPlayer(new CachedPlayer(UUID.fromString(key.toString()), playersSection.getString(key.toString())));
                }
            }
        }
    }
}

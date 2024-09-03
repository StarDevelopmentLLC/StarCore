package com.stardevllc.starcore.player;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StarPlayer {
    private final UUID uniqueId;
    private String name;
    
    //The custom data map is used only for other plugins. Default info provided by StarCore are their own fields.
    private Map<String, Object> customData = new HashMap<>();

    public StarPlayer(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }
    
    public StarPlayer(UUID uniqueId, String name) {
        this.uniqueId = uniqueId;
        this.name = name;
    }
    
    public StarPlayer(Player player) {
        this.uniqueId = player.getUniqueId();
        this.name = player.getName();
    }
    
    public StarPlayer(OfflinePlayer offlinePlayer) {
        this.uniqueId = offlinePlayer.getUniqueId();
        this.name = offlinePlayer.getName();
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getCustomData() {
        return customData;
    }
    
    public Map<String, Object> serialize() {
        Map<String, Object> serialized = new HashMap<>();
        serialized.put("uniqueId", uniqueId.toString());
        serialized.put("name", name);
        serialized.put("customData", customData);
        return serialized;
    }

    void setCustomData(Map<String, Object> customData) {
        this.customData = customData;
    }
}

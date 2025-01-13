package com.stardevllc.starcore.player;

import com.stardevllc.config.Section;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StarPlayer {
    private final UUID uniqueId;
    private String name;
    
    private long playtime;
    private long lastLogin, lastLogout;
    
    //The custom data map is used only for other plugins. Default info provided by StarCore are their own fields.
    private Map<String, Object> customData = new HashMap<>();

    public StarPlayer(UUID uniqueId, String name) {
        this.uniqueId = uniqueId;
        this.name = name;
    }
    
    public StarPlayer(UUID uniqueId) {
        this(uniqueId, null);
    }
    
    public StarPlayer(Player player) {
        this(player.getUniqueId(), player.getName());
    }
    
    public StarPlayer(OfflinePlayer offlinePlayer) {
        this(offlinePlayer.getUniqueId(), offlinePlayer.getName());
    }
    
    StarPlayer(Map<String, Object> serialized) {
        this(UUID.fromString(serialized.get("uniqueId").toString()));
        this.name = serialized.getOrDefault("name", "").toString();
        this.playtime = Long.parseLong(serialized.getOrDefault("playtime", "0").toString());
        this.lastLogin = Long.parseLong(serialized.getOrDefault("lastLogin", "0").toString());
        this.lastLogout = Long.parseLong(serialized.getOrDefault("lastLogout", "0").toString());
        Object rawCustomData = serialized.get("customData");
        if (rawCustomData != null) {
            if (rawCustomData instanceof Map) {
                customData.putAll((Map<String, Object>) rawCustomData);
            } else if (rawCustomData instanceof Section dataSection) {
                for (String key : dataSection.getKeys()) {
                    this.customData.put(key, dataSection.get(key));
                }
            }
        }
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

    public long getPlaytime() {
        if (lastLogout < lastLogin) {
            return playtime + (System.currentTimeMillis() - lastLogin);
        }
        
        return playtime;
    }

    public long getLastLogin() {
        return lastLogin;
    }

    public long getLastLogout() {
        return lastLogout;
    }

    public void setLastLogin(long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setLastLogout(long lastLogout) {
        this.lastLogout = lastLogout;
    }

    public void setPlaytime(long playtime) {
        this.playtime = playtime;
    }

    public Map<String, Object> serialize() {
        Map<String, Object> serialized = new HashMap<>();
        serialized.put("uniqueId", uniqueId.toString());
        serialized.put("name", name);
        serialized.put("customData", customData);
        serialized.put("playtime", playtime);
        serialized.put("lastLogin", lastLogin);
        serialized.put("lastLogout", lastLogout);
        return serialized;
    }

    void setCustomData(Map<String, Object> customData) {
        this.customData = customData;
    }
}

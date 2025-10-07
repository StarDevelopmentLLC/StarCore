package com.stardevllc.starcore.player;

import com.stardevllc.starmclib.mojang.MojangProfile;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class StarPlayer {
    private final UUID uniqueId;
    private String name;
    
    private MojangProfile mojangProfile;
    
    private long playtime;
    private long firstLogin = 1, lastLogin = 1, lastLogout = 1;
    
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
        this.firstLogin = Long.parseLong(serialized.getOrDefault("firstLogin", "0").toString());
        this.playtime = Long.parseLong(serialized.getOrDefault("playtime", "0").toString());
        this.lastLogin = Long.parseLong(serialized.getOrDefault("lastLogin", "0").toString());
        this.lastLogout = Long.parseLong(serialized.getOrDefault("lastLogout", "0").toString());
    }
    
    public MojangProfile getMojangProfile() {
        return mojangProfile;
    }
    
    public void setMojangProfile(MojangProfile mojangProfile) {
        this.mojangProfile = mojangProfile;
    }
    
    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        if (this.mojangProfile != null && !this.mojangProfile.getName().equals(this.name)) {
            this.name = this.mojangProfile.getName();
        }
        
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPlaytime() {
        if (lastLogout < lastLogin) {
            return playtime + System.currentTimeMillis() - lastLogin;
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
    
    public long getFirstLogin() {
        return firstLogin;
    }
    
    public void setFirstLogin(long firstLogin) {
        this.firstLogin = firstLogin;
    }
    
    public Map<String, Object> serialize() {
        Map<String, Object> serialized = new HashMap<>();
        serialized.put("uniqueId", uniqueId.toString());
        serialized.put("name", name);
        serialized.put("playtime", playtime);
        serialized.put("lastLogin", lastLogin);
        serialized.put("lastLogout", lastLogout);
        serialized.put("firstLogin", this.firstLogin);
        return serialized;
    }
}
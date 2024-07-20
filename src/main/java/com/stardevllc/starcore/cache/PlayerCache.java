package com.stardevllc.starcore.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerCache {
    private final Map<UUID, String> uuidToName = new HashMap<>();
    
    public UUID getUUIDFromName(String name) {
        for (Map.Entry<UUID, String> entry : uuidToName.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(name)) {
                return entry.getKey();
            }
        }
        
        return null;
    }
    
    public String getNameFromUUID(UUID uuid) {
        return uuidToName.get(uuid);
    }
    
    public void addEntry(UUID uuid, String name) {
        uuidToName.put(uuid, name);
    }

    public Map<UUID, String> getEntries() {
        return new HashMap<>(uuidToName);
    }
}

package com.stardevllc.starcore.player;

import com.stardevllc.starlib.registry.AbstractRegistry;
import com.stardevllc.starlib.registry.RegistryKey;

import java.util.*;

public class PlayerRegistry extends AbstractRegistry<StarPlayer> {
    public PlayerRegistry() {
        super(RegistryKey.of("starplayers"), "StarPlayers", new HashMap<>());
    }
    
    public StarPlayer register(UUID uuid, StarPlayer starPlayer) {
        return this.register(RegistryKey.of(uuid.toString()), starPlayer);
    }
    
    public boolean containsKey(UUID uuid) {
        return this.containsKey(uuid.toString());
    }
    
    public StarPlayer get(UUID uuid) {
        return this.get(uuid.toString());
    }
    
    @Override
    public Map<RegistryKey, StarPlayer> toMapCopy() {
        return new HashMap<>(this.backingMap);
    }
}

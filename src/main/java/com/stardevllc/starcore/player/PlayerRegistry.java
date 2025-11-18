package com.stardevllc.starcore.player;

import com.stardevllc.starlib.objects.registry.Registry;

import java.util.UUID;

public class PlayerRegistry extends Registry<UUID, StarPlayer> {
    public PlayerRegistry() {
        super(StarPlayer::getUniqueId);
    }
}

package com.stardevllc.starcore.player;

import com.stardevllc.starlib.objects.id.impl.StringId;
import com.stardevllc.starlib.repository.AbstractRepository;

import java.util.HashMap;
import java.util.UUID;

public class PlayerRepository extends AbstractRepository<UUID, StarPlayer> {
    public PlayerRepository() {
        super(UUID.class, StarPlayer.class, new HashMap<>(), new StringId("starplayers"), "StarPlayers");
    }
}

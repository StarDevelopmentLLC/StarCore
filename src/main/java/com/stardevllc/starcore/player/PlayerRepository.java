package com.stardevllc.starcore.player;

import com.stardevllc.starcore.StarCore;
import com.stardevllc.starlib.objects.key.impl.StringKey;
import com.stardevllc.starlib.repository.AbstractRepository;
import com.stardevllc.minecraft.mojang.MojangAPI;
import com.stardevllc.minecraft.mojang.MojangProfile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerRepository extends AbstractRepository<UUID, StarPlayer> {
    public PlayerRepository() {
        super(UUID.class, StarPlayer.class, new HashMap<>(), new StringKey("starplayers"), "StarPlayers");
        this.setValueFetcher(uuid -> {
            Player spigotPlayer = Bukkit.getPlayer(uuid);
            StarPlayer starPlayer;
            if (spigotPlayer != null) {
                starPlayer = new StarPlayer(spigotPlayer);
            } else {
                starPlayer = new StarPlayer(uuid);
            }
            
            if (StarCore.getPlugin(StarCore.class).isUseMojangAPI()) {
                if (starPlayer.getMojangProfile() == null) {
                    Bukkit.getScheduler().runTaskAsynchronously(StarCore.getPlugin(StarCore.class), () -> {
                        MojangProfile profile = MojangAPI.getProfile(uuid);
                        starPlayer.setMojangProfile(profile);
                    });
                }
            }
            return starPlayer;
        });
    }
}

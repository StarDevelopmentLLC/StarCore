package com.stardevllc.starcore.player;

import com.stardevllc.config.Section;
import com.stardevllc.config.file.FileConfig;
import com.stardevllc.config.file.yaml.YamlConfig;
import com.stardevllc.starcore.StarCore;
import com.stardevllc.starlib.dependency.Inject;
import com.stardevllc.starlib.registry.UUIDRegistry;
import com.stardevllc.starmclib.mojang.MojangAPI;
import com.stardevllc.starmclib.mojang.MojangProfile;
import com.stardevllc.starsql.statements.SqlInsertUpdate;
import com.stardevllc.starsql.statements.SqlSelect;
import org.bukkit.Bukkit;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.*;

import java.io.File;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class PlayerManager implements Listener {
    private final UUIDRegistry<StarPlayer> playerRegistry = new UUIDRegistry.Builder<StarPlayer>().keyRetriever(StarPlayer::getUniqueId).build();
    
    private FileConfig playersConfig;
    
    @Inject
    private StarCore plugin;
    @Inject
    private PluginManager pluginManager;
    @Inject
    private ServicesManager servicesManager;
    
    public PlayerManager init() {
        if (plugin.isSavePlayerInfo() && plugin.getDatabase() == null) {
            playersConfig = new YamlConfig(new File(plugin.getDataFolder(), "players.yml"));
        }
        
        this.pluginManager.registerEvents(this, plugin);
        this.servicesManager.register(PlayerManager.class, this, plugin, ServicePriority.Normal);
        return this;
    }
    
    public void addPlayer(StarPlayer player) {
        playerRegistry.put(player.getUniqueId(), player);
    }
    
    public StarPlayer getPlayer(UUID uuid) {
        if (this.playerRegistry.contains(uuid)) {
            StarPlayer starPlayer = playerRegistry.get(uuid);
            if (plugin.isUseMojangAPI()) {
                if (starPlayer.getMojangProfile() == null) {
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                        MojangProfile profile = MojangAPI.getProfile(uuid);
                        StarPlayer sp = playerRegistry.get(uuid);
                        if (sp != null) {
                            sp.setMojangProfile(profile);
                        }
                    });
                }
            }
            
            return starPlayer;
        }
        
        return null;
    }
    
    public void save() {
        if (plugin.getDatabase() == null) {
            if (this.playersConfig == null) {
                return;
            }

            for (Map.Entry<UUID, StarPlayer> entry : this.playerRegistry.entrySet()) {
                this.playersConfig.set("players." + entry.getKey().toString(), entry.getValue().serialize());
            }

            this.playersConfig.save();
        } else {
            for (StarPlayer player : this.playerRegistry.values()) {
                savePlayer(player);
            }
        }
    }
    
    public void savePlayer(StarPlayer player) {
        SqlInsertUpdate insert = new SqlInsertUpdate(plugin.getMainConfig().get("mysql.table-prefix") + "players").primaryKeyColumn("uniqueid")
                .columns("uniqueid", "name", "playtime", "firstlogin", "lastlogin", "lastlogout")
                .row(player.getUniqueId().toString(), player.getName(), player.getPlaytime(), new Timestamp(player.getFirstLogin()), new Timestamp(player.getLastLogin()), new Timestamp(player.getLastLogout()));
        plugin.getDatabase().executeUpdate(insert.build());
    }
    
    public void load() {
        if (plugin.getDatabase() == null) {
            if (this.playersConfig == null) {
                return;
            }

            if (!this.playersConfig.contains("players")) {
                return;
            }

            Section playersSection = this.playersConfig.getConfigurationSection("players");
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
        } else {
            SqlSelect select = plugin.getPlayersTable().select().columns("uniqueid", "name", "playtime", "firstlogin", "lastlogin", "lastlogout");
            plugin.getDatabase().executeQuery(select.build(), rs -> {
                try {
                    while (rs.next()) {
                        UUID uuid = UUID.fromString(rs.getString("uniqueid"));
                        String name = rs.getString("name");
                        long playtime = rs.getLong("playtime");
                        long firstlogin = rs.getTimestamp("firstlogin").getTime();
                        long lastlogin = rs.getTimestamp("lastlogin").getTime();
                        long lastlogout = rs.getTimestamp("lastlogout").getTime();
                        
                        StarPlayer starPlayer = new StarPlayer(uuid, name);
                        starPlayer.setPlaytime(playtime);
                        starPlayer.setFirstLogin(firstlogin);
                        starPlayer.setLastLogin(lastlogin);
                        starPlayer.setLastLogout(lastlogout);
                        addPlayer(starPlayer);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!this.playerRegistry.contains(e.getPlayer().getUniqueId())) {
            this.playerRegistry.register(new StarPlayer(e.getPlayer()));
        }
        
        StarPlayer starPlayer = getPlayer(e.getPlayer().getUniqueId());
        if (starPlayer.getFirstLogin() == 0) {
            starPlayer.setFirstLogin(System.currentTimeMillis());
        }
        starPlayer.setLastLogin(System.currentTimeMillis());
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        StarPlayer starPlayer = this.playerRegistry.get(e.getPlayer().getUniqueId());
        starPlayer.setLastLogout(System.currentTimeMillis());
        starPlayer.setPlaytime(starPlayer.getPlaytime() + starPlayer.getLastLogout() - starPlayer.getLastLogin());
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> savePlayer(starPlayer));
    }
}

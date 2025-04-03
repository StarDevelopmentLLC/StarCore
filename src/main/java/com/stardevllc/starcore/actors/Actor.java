package com.stardevllc.starcore.actors;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public abstract class Actor {
    public static final Map<Object, Actor> CACHE = new HashMap<>();

    private static Function<String, String> COLOR_FUNCTION = text -> ChatColor.translateAlternateColorCodes('&', text);

    public static Function<String, String> getColorFunction() {
        return COLOR_FUNCTION;
    }

    public static void setColorFunction(Function<String, String> colorFunction) {
        if (colorFunction == null) {
            return;
        }

        COLOR_FUNCTION = colorFunction;
    }
    
    public abstract boolean equals(Object object);

    public abstract int hashcode();

    public abstract void sendMessage(String message);

    public void sendColoredMessage(String message) {
        sendMessage(Actor.getColorFunction().apply(message));
    }

    public abstract String getName();
    
    public abstract boolean hasPermission(String permission);
    
    public abstract String getConfigString();

    public boolean isPlayer() {
        return false;
    }

    public boolean isServer() {
        return false;
    }

    public boolean isPlugin() {
        return false;
    }

    public boolean isOnline() {
        return false;
    }

    public static Actor create(Object object) {
        Actor actor = CACHE.get(object);
        if (actor != null) {
            return actor;
        }

        if (object instanceof Player player) {
            actor = of(player);
        } else if (object instanceof UUID uniqueId) {
            actor = of(uniqueId);
        } else if (object instanceof JavaPlugin plugin) {
            actor = of(plugin);
        } else if (object instanceof ConsoleCommandSender) {
            actor = getServerActor();
        } else if (object instanceof String str) {
            if (str.equalsIgnoreCase("console") || str.equalsIgnoreCase("server")) {
                actor = getServerActor();
            }

            try {
                return of(UUID.fromString(str));
            } catch (Exception e) {
            }

            Player player = Bukkit.getPlayer(str);
            if (player != null) {
                return of(player);
            }

            Plugin plugin = Bukkit.getPluginManager().getPlugin(str);
            if (plugin != null) {
                return of((JavaPlugin) plugin);
            }
        }

        CACHE.put(object, actor);
        return actor;
    }

    public static PlayerActor of(Player player) {
        return new PlayerActor(player);
    }

    public static Actor of(UUID uniqueId) {
        if (uniqueId.equals(ServerActor.serverUUID)) {
            return getServerActor();
        }

        return new PlayerActor(uniqueId);
    }

    public static PluginActor of(JavaPlugin plugin) {
        return new PluginActor(plugin);
    }

    public static ServerActor getServerActor() {
        return ServerActor.instance;
    }
}
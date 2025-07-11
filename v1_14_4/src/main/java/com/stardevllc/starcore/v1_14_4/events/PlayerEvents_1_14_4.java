package com.stardevllc.starcore.v1_14_4.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTakeLecternBookEvent;

public class PlayerEvents_1_14_4 implements Listener {
    @EventHandler
    public void onPlayerTakeLecturnBook(PlayerTakeLecternBookEvent e) {
        StarEvents.callEvent(e);
    }
}
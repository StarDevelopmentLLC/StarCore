package com.stardevllc.starcore.api.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class PlayerEvents implements Listener {
    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerAnimation(PlayerAnimationEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerArmorStandManipulate(PlayerArmorStandManipulateEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerBedLeave(PlayerBedLeaveEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerBucketFill(PlayerBucketFillEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerChannel(PlayerChannelEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerChatTabComplete(PlayerChatTabCompleteEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerDropItemEvent(PlayerDropItemEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerEditBook(PlayerEditBookEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerEggThrow(PlayerEggThrowEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerExpChange(PlayerExpChangeEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerFish(PlayerFishEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerGameModeChange(PlayerGameModeChangeEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerItemBreak(PlayerItemBreakEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerKick(PlayerKickEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerLevelChange(PlayerLevelChangeEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerRegisterChannel(PlayerRegisterChannelEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerShearEntity(PlayerShearEntityEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerStatisticIncrement(PlayerStatisticIncrementEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerToggleSprint(PlayerToggleSprintEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerUnleashEntity(PlayerUnleashEntityEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerUnregisterChannel(PlayerUnregisterChannelEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerVelocity(PlayerVelocityEvent e) {
        StarEvents.callEvent(e);
    }
}
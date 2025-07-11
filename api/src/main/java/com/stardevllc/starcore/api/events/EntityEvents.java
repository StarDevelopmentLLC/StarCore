package com.stardevllc.starcore.api.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;

public class EntityEvents implements Listener {
    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onCreeperPower(CreeperPowerEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onEntityBreakDoor(EntityBreakDoorEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onEntityCombustByBlock(EntityCombustByBlockEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onEntityCombustByEntity(EntityCombustByEntityEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onEntityDamageByBlock(EntityDamageByBlockEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onEntityInteract(EntityInteractEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onEntityPortalEnter(EntityPortalEnterEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onEntityPortal(EntityPortalEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onEntityPortalExit(EntityPortalExitEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onEntityRegainHealth(EntityRegainHealthEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onEntityTame(EntityTameEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onEntityTarget(EntityTargetEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onEntityTeleport(EntityTeleportEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onEntityUnleash(EntityUnleashEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onExplosionPrime(ExplosionPrimeEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onHorseJump(HorseJumpEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onItemDespawn(ItemDespawnEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onItemSpawn(ItemSpawnEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPigZap(PigZapEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPlayerLeashEntity(PlayerLeashEntityEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPotionSplash(PotionSplashEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onSheepDyeWool(SheepDyeWoolEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onSheepRegrowWool(SheepRegrowWoolEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onSlimeSplit(SlimeSplitEvent e) {
        StarEvents.callEvent(e);
    }
}
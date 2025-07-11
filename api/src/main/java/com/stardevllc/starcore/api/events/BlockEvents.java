package com.stardevllc.starcore.api.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;

public class BlockEvents implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onBlockBurn(BlockBurnEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onBlockCanBuild(BlockCanBuildEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onBlockDamage(BlockDamageEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onBlockDispense(BlockDispenseEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onBlockExp(BlockExpEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onBlockFade(BlockFadeEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onBlockForm(BlockFormEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onBlockFromTo(BlockFromToEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onBlockGrow(BlockGrowEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onBlockMultiPlace(BlockMultiPlaceEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onBlockPistonExtend(BlockPistonExtendEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onBlockPistonRetract(BlockPistonRetractEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onBlockRedstone(BlockRedstoneEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onBlockSpread(BlockSpreadEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onEntityBlockForm(EntityBlockFormEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onLeavesDecayEvent(LeavesDecayEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onNotePlateEvent(NotePlayEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        StarEvents.callEvent(e);
    }
}
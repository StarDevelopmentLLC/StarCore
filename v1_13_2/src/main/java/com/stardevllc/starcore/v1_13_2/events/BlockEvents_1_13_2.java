package com.stardevllc.starcore.v1_13_2.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;

public class BlockEvents_1_13_2 implements Listener {
    @EventHandler
    public void onBlockDispenseArmor(BlockDispenseArmorEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onMoistureChange(MoistureChangeEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onFluidLevelChange(FluidLevelChangeEvent e) {
        StarEvents.callEvent(e);
    }
}
package com.stardevllc.starcore.api.events;

import com.stardevllc.starcore.api.StarEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.*;

public class ServerEvents implements Listener {
    @EventHandler
    public void onMapInit(MapInitializeEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPluginDisable(PluginDisableEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onPluginEnable(PluginEnableEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onRemoteSErverCommand(RemoteServerCommandEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onServerListPing(ServerListPingEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onServiceRegister(ServiceRegisterEvent e) {
        StarEvents.callEvent(e);
    }
    
    @EventHandler
    public void onServiceUnregister(ServiceUnregisterEvent e) {
        StarEvents.callEvent(e);
    }
}
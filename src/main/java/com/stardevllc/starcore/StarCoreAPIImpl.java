package com.stardevllc.starcore;

import com.stardevllc.starcore.api.StarCoreAPI;
import com.stardevllc.starcore.api.wrappers.MCWrappers;

import java.util.logging.Logger;

public class StarCoreAPIImpl extends StarCoreAPI {
    
    private StarCore plugin;
    
    public StarCoreAPIImpl(StarCore plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public MCWrappers getWrappers() {
        return plugin.getMcWrappers();
    }
    
    @Override
    public Logger getLogger() {
        return plugin.getLogger();
    }
}

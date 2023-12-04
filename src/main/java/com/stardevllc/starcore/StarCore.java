package com.stardevllc.starcore;

import com.stardevllc.starclock.ClockManager;
import com.stardevllc.starcore.cmds.StarCoreCmd;
import com.stardevllc.starlib.task.TaskFactory;
import com.stardevllc.starmclib.task.SpigotTaskFactory;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class StarCore extends JavaPlugin {
    public void onEnable() {
        getServer().getServicesManager().register(TaskFactory.class, new SpigotTaskFactory(this), this, ServicePriority.Normal);
        ClockManager clockManager = new ClockManager(getLogger(), 50L);
        getServer().getServicesManager().register(ClockManager.class, clockManager, this, ServicePriority.Normal);
        getServer().getScheduler().runTaskTimer(this, clockManager.getRunnable(), 1L, 1L);

        getCommand("starcore").setExecutor(new StarCoreCmd(this));
    }
}
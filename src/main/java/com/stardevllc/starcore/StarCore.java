package com.stardevllc.starcore;

import com.stardevllc.starclock.ClockManager;
import com.stardevllc.starcore.cmds.StarCoreCmd;
import com.stardevllc.starlib.task.TaskFactory;
import com.stardevllc.starmclib.Config;
import com.stardevllc.starmclib.actor.ServerActor;
import com.stardevllc.starmclib.color.ColorUtils;
import com.stardevllc.starmclib.color.CustomColor;
import com.stardevllc.starmclib.task.SpigotTaskFactory;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.UUID;

public class StarCore extends JavaPlugin {
    
    private UUID consoleUnqiueId;
    private Config colorsConfig;
    private Config mainConfig;
    
    public void onEnable() {
        Config config = new Config(new File(getDataFolder(), "config.yml"));
        config.addDefault("console-uuid", UUID.randomUUID().toString(), "This is the unique id that is assigned to the console.", "Please do not change this manually.");
        this.consoleUnqiueId = UUID.fromString(config.getString("console-uuid"));
        ServerActor.serverUUID = this.consoleUnqiueId;
        Bukkit.getServer().getServicesManager().register(ServerActor.class, ServerActor.getServerActor(), this, ServicePriority.Highest);
        
        config.addDefault("save-colors", false, "This allows the plugin to save colors to colors.yml.", "Colors are defined using the command or by plugins.", "Only colors created by StarCore are saved to the file.");
        if (config.getBoolean("save-colors")) {
            loadColors();
        }
        
        config.save();
        
        getServer().getServicesManager().register(TaskFactory.class, new SpigotTaskFactory(this), this, ServicePriority.Normal);
        ClockManager clockManager = new ClockManager(getLogger(), 50L);
        getServer().getServicesManager().register(ClockManager.class, clockManager, this, ServicePriority.Normal);
        getServer().getScheduler().runTaskTimer(this, clockManager.getRunnable(), 1L, 1L);

        getCommand("starcore").setExecutor(new StarCoreCmd(this));
    }
    
    public void reload(boolean save) {
        if (save) {
            saveColors();
        }
        
        ColorUtils.getCustomColors().forEach((code, color) -> {
            if (color.getOwner().getName().equalsIgnoreCase(getName())) {
                ColorUtils.removeColor(code);
            }
        });
        
        loadColors();
        
        this.mainConfig = new Config(new File(getDataFolder(), "config.yml"));
        this.consoleUnqiueId = UUID.fromString(mainConfig.getString("console-uuid"));
        ServerActor.serverUUID = this.consoleUnqiueId;
    }
    
    public void loadColors() {
        this.colorsConfig = new Config(new File(getDataFolder(), "colors.yml"));
        if (this.colorsConfig.contains("colors")) {
            Section colorsSection = this.colorsConfig.getSection("colors");
            if (colorsSection != null) {
                for (Object key : colorsSection.getKeys()) {
                    CustomColor customColor = new CustomColor(this);
                    customColor.symbolCode((String) key);
                    customColor.hexValue(colorsConfig.getString("colors." + key + ".hex"));
                    if (this.colorsConfig.contains("colors." + key + ".permission")) {
                        customColor.permission(colorsConfig.getString("colors." + key + ".permission"));
                    }
                    ColorUtils.addCustomColor(customColor);
                }
            }
        }
    }
    
    public void saveColors() {
        if (colorsConfig != null) {
            colorsConfig.set("colors", null);
            for (CustomColor color : ColorUtils.getCustomColors().values()) {
                if (color.getOwner().getName().equalsIgnoreCase(getName())) {
                    colorsConfig.set("colors." + color.getChatCode() + ".hex", color.getHex());
                    if (color.getPermission() != null) {
                        colorsConfig.set("colors." + color.getChatCode() + ".permission", color.getPermission());
                    }
                }
            }
            this.colorsConfig.save();
        }
    }

    @Override
    public void onDisable() {
        saveColors();
    }

    public UUID getConsoleUnqiueId() {
        return consoleUnqiueId;
    }
}
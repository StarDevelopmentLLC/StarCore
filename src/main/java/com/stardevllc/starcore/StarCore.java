package com.stardevllc.starcore;

import com.stardevllc.config.Section;
import com.stardevllc.starcore.api.StarColors;
import com.stardevllc.starcore.api.StarCoreAPI;
import com.stardevllc.starcore.api.actors.ServerActor;
import com.stardevllc.starcore.api.colors.CustomColor;
import com.stardevllc.starcore.api.wrappers.MCWrappers;
import com.stardevllc.starcore.base.NMSVersion;
import com.stardevllc.starcore.base.itembuilder.ItemBuilder;
import com.stardevllc.starcore.cmds.StarCoreCmd;
import com.stardevllc.starcore.config.Configuration;
import com.stardevllc.starcore.player.PlayerManager;
import com.stardevllc.starcore.skins.SkinManager;
import com.stardevllc.starcore.v1_16.ColorHandler_1_16;
import com.stardevllc.starcore.v1_8.ColorHandler_1_8;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.UUID;

public class StarCore extends JavaPlugin {

    static {
        ItemBuilder.colorFunction = StarColors::color;
    }
    
    private UUID consoleUnqiueId;
    private Configuration colorsConfig;
    private Configuration mainConfig;
    private SkinManager skinManager;

    private PlayerManager playerManager;
    
    private MCWrappers mcWrappers;

    public void onEnable() {
        mainConfig = new Configuration(new File(getDataFolder(), "config.yml"));
        mainConfig.addDefault("console-uuid", UUID.randomUUID().toString(), " This is the unique id that is assigned to the console.", " Please do not change this manually.");
        this.consoleUnqiueId = UUID.fromString(mainConfig.getString("console-uuid"));
        ServerActor.serverUUID = this.consoleUnqiueId;
        Bukkit.getServer().getServicesManager().register(ServerActor.class, ServerActor.getServerActor(), this, ServicePriority.Highest);

        mainConfig.addDefault("save-colors", false, " This allows the plugin to save colors to colors.yml.", "Colors are defined using the command or by plugins.", "Only colors created by StarCore are saved to the file.");
        if (mainConfig.getBoolean("save-colors")) {
            loadColors();
        }
        
        this.playerManager = new PlayerManager(this);
        getServer().getPluginManager().registerEvents(playerManager, this);
        
        mainConfig.addDefault("save-player-info", true, " This allows the plugin to save a cache of player UUIDs to Names for offline fetching.", "Players must still join at least once though");
        if (mainConfig.getBoolean("save-player-info")) {
            this.playerManager.load();
        }
        
        mainConfig.addDefault("use-mojang-api", true, "Use the Mojang API to get Skin Info for players.", "This is retrieved when a player joins, and done async to prevent lag.", "Disabling this could break plugins that rely on this.");
        
        Bukkit.getServer().getServicesManager().register(PlayerManager.class, playerManager, this, ServicePriority.High);
        
        mainConfig.addDefault("messages.command.reload", "&aSuccessfully reloaded configs.", " The message sent when /starcore reload is a success");
        mainConfig.addDefault("messages.command.invalidsubcommand", "&cInvalid subcommand.", " The message sent when an invalid sub-command is provided to /starcore");
        mainConfig.addDefault("messages.command.nopermission", "&cYou do not have permission to use that command.", " The message displayed when no permission is detected for the /starcore command.");
        
        if (mainConfig.contains("messages.command.color.listsymbols.header")) {
            mainConfig.set("messages.command.color.listsymbols", null);
        }

        if (mainConfig.contains("messages.command.color.listcolors")) {
            mainConfig.set("messages.command.color.listcolors", null);
        }

        mainConfig.addDefault("messages.command.color.list.symbols", "&eList of valid color prefix characters: &r{symbols}", " The format to use for the list of color symbols.");
        mainConfig.addDefault("messages.command.color.list.colors", "&eList of registered colors: ", " The header text for the /starcore colors list codes command");
        mainConfig.addDefault("messages.command.color.add.cannot-override-spigot", "&cYou cannot override default spigot colors. Please choose a different code.", " The error message for when someone tries to override a spigot color in /starcore color add command");
        mainConfig.addDefault("messages.command.color.add.invalid-code", "&cThe code you provided is not valid. Codes must be 2 characters and start with a valid symbol. &eUse the /starcore color listsymbols &ccommand.", " The error message for when someone tries to use an invalid 2 character code in the /starcore color add command.");
        mainConfig.addDefault("messages.command.color.remove.not-registered", "&cThe code you specified is not a registered color.", " The message sent when the code is not registered in the /starcore color remove command.");
        mainConfig.addDefault("messages.command.color.remove.success", "&eYou removed &b{OLDCODE} &eas a custom color.", " The message sent when the code is removed successfully in /starcore color remove command");
        mainConfig.save();

        this.skinManager = new SkinManager();
        getServer().getServicesManager().register(SkinManager.class, this.skinManager, this, ServicePriority.Normal);
        
        this.mcWrappers = new MCWrappersImpl();
        getServer().getServicesManager().register(MCWrappers.class, this.mcWrappers, this, ServicePriority.Highest);
        
        StarCoreCmd starCoreCmd = new StarCoreCmd(this);
        PluginCommand pluginStarCoreCmd = getCommand("starcore");
        pluginStarCoreCmd.setExecutor(starCoreCmd);
        pluginStarCoreCmd.setTabCompleter(starCoreCmd);
        
        StarCoreAPI.setAPI(new StarCoreAPIImpl(this));
        
        NMSVersion currentVersion = NMSVersion.CURRENT_VERSION;
        if (currentVersion.ordinal() < NMSVersion.v1_16_R1.ordinal()) {
            StarColors.setColorHandler(new ColorHandler_1_8());
        } else {
            StarColors.setColorHandler(new ColorHandler_1_16());
        }
    }

    public void reload(boolean save) {
        if (save) {
            saveColors();
            this.playerManager.save();
        }

        StarColors.getCustomColors().forEach((code, color) -> {
            if (color.getOwner().getName().equalsIgnoreCase(getName())) {
                StarColors.removeColor(code);
            }
        });
    
        if (mainConfig.getBoolean("save-colors")) {
            loadColors();
        }
        
        if (mainConfig.getBoolean("save-player-info")) {
            this.playerManager.load();
        }

        this.mainConfig = new Configuration(new File(getDataFolder(), "config.yml"));
        this.consoleUnqiueId = UUID.fromString(mainConfig.getString("console-uuid"));
        ServerActor.serverUUID = this.consoleUnqiueId;
    }
    
    public void loadColors() {
        this.colorsConfig = new Configuration(new File(getDataFolder(), "colors.yml"));
        if (this.colorsConfig.contains("colors")) {
            Section colorsSection = this.colorsConfig.getConfigurationSection("colors");
            if (colorsSection != null) {
                for (String key : colorsSection.getKeys()) {
                    CustomColor customColor = new CustomColor(this);
                    customColor.symbolCode(key);
                    customColor.hexValue(colorsConfig.getString("colors." + key + ".hex"));
                    if (this.colorsConfig.contains("colors." + key + ".permission")) {
                        customColor.permission(colorsConfig.getString("colors." + key + ".permission"));
                    }
                    StarColors.addCustomColor(customColor);
                }
            }
        }
    }

    public void saveColors() {
        if (colorsConfig != null) {
            colorsConfig.set("colors", null);
            for (CustomColor color : StarColors.getCustomColors().values()) {
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
        this.playerManager.save();
    }
    
    public MCWrappers getMcWrappers() {
        return mcWrappers;
    }
    
    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public UUID getConsoleUnqiueId() {
        return consoleUnqiueId;
    }

    public Configuration getMainConfig() {
        return mainConfig;
    }

    public SkinManager getSkinManager() {
        return skinManager;
    }
}
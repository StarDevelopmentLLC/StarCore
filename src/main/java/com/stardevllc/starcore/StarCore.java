package com.stardevllc.starcore;

import com.stardevllc.config.Section;
import com.stardevllc.starcore.api.StarColors;
import com.stardevllc.starcore.api.StarEvents;
import com.stardevllc.starcore.api.colors.CustomColor;
import com.stardevllc.starcore.api.events.*;
import com.stardevllc.starcore.api.itembuilder.ItemBuilder;
import com.stardevllc.starcore.api.wrappers.*;
import com.stardevllc.starcore.cmds.StarCoreCmd;
import com.stardevllc.starcore.config.Configuration;
import com.stardevllc.starcore.player.PlayerManager;
import com.stardevllc.starcore.v1_10_2.events.Module_1_10_2;
import com.stardevllc.starcore.v1_11_2.events.Module_1_11_2;
import com.stardevllc.starcore.v1_12_2.events.Module_1_12_2;
import com.stardevllc.starcore.v1_13.Module_1_13;
import com.stardevllc.starcore.v1_13_2.Module_1_13_2;
import com.stardevllc.starcore.v1_14_4.Module_1_14_4;
import com.stardevllc.starcore.v1_15_2.events.Module_1_15_2;
import com.stardevllc.starcore.v1_16_1.Module_1_16_1;
import com.stardevllc.starcore.v1_16_3.events.Module_1_16_3;
import com.stardevllc.starcore.v1_16_5.events.Module_1_16_5;
import com.stardevllc.starcore.v1_17_1.Module_1_17_1;
import com.stardevllc.starcore.v1_18_1.events.Module_1_18_1;
import com.stardevllc.starcore.v1_19_3.Module_1_19_3;
import com.stardevllc.starcore.v1_20_1.Module_1_20_1;
import com.stardevllc.starcore.v1_21_1.Module_1_21_1;
import com.stardevllc.starcore.v1_21_3.events.Module_1_21_3;
import com.stardevllc.starcore.v1_8.Module_1_8;
import com.stardevllc.starcore.v1_8_3.events.Module_1_8_3;
import com.stardevllc.starcore.v1_8_8.events.Module_1_8_8;
import com.stardevllc.starcore.v1_9_4.events.Module_1_9_4;
import com.stardevllc.starmclib.MinecraftVersion;
import com.stardevllc.starmclib.StarMCLib;
import com.stardevllc.starmclib.actors.ServerActor;
import com.stardevllc.starmclib.plugin.ExtendedJavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;

import java.io.File;
import java.util.UUID;

public class StarCore extends ExtendedJavaPlugin {

    static {
        ItemBuilder.colorFunction = StarColors::color;
    }
    
    private UUID consoleUnqiueId;
    private Configuration mainConfig;
    private Configuration colorsConfig;
    private Configuration messagesConfig;

    private PlayerManager playerManager;
    
    private MCWrappers mcWrappers;
    
    public void onEnable() {
        super.onEnable();
        StarMCLib.init();
        StarMCLib.registerPluginEventBus(getEventBus());
        StarMCLib.registerPluginInjector(this, getInjector());
        StarEvents.addChildBus(getEventBus());
        mainConfig = new Configuration(new File(getDataFolder(), "config.yml"));
        getLogger().info("Initialized the main config");
        
        mainConfig.addDefault("console-uuid", UUID.randomUUID().toString(), " This is the unique id that is assigned to the console.", " Please do not change this manually.");
        this.consoleUnqiueId = UUID.fromString(mainConfig.getString("console-uuid"));
        ServerActor.serverUUID = this.consoleUnqiueId;
        Bukkit.getServer().getServicesManager().register(ServerActor.class, ServerActor.getServerActor(), this, ServicePriority.Highest);
        StarMCLib.GLOBAL_INJECTOR.setInstance(ServerActor.class, ServerActor.getServerActor());
        getLogger().info("Set the Console UUID to " + this.consoleUnqiueId);
        
        mainConfig.addDefault("save-colors", false, " This allows the plugin to save colors to colors.yml.", "Colors are defined using the command or by plugins.", "Only colors created by StarCore are saved to the file.");
        if (mainConfig.getBoolean("save-colors")) {
            loadColors();
            getLogger().info("Loaded custom colors");
        }
        
        this.playerManager = injector.inject(new PlayerManager()).init();
        StarMCLib.GLOBAL_INJECTOR.setInstance(PlayerManager.class, this.playerManager);
        
        mainConfig.addDefault("save-player-info", true, " This allows the plugin to save a cache of player UUIDs to Names for offline fetching.", "Players must still join at least once though");
        if (mainConfig.getBoolean("save-player-info")) {
            this.playerManager.load();
            getLogger().info("Loaded player data");
        }
        
        mainConfig.addDefault("use-mojang-api", true, "Use the Mojang API to get Skin Info for players.", "This is retrieved when a player joins, and done async to prevent lag.", "Disabling this could break plugins that rely on this.");
        
        messagesConfig = new Configuration(new File(getDataFolder(), "messages.yml"));
        getLogger().info("Initialized the messages.yml file");
        
        messagesConfig.addDefault("command.reload", "&aSuccessfully reloaded configs.", " The message sent when /starcore reload is a success");
        messagesConfig.addDefault("command.invalidsubcommand", "&cInvalid subcommand.", " The message sent when an invalid sub-command is provided to /starcore");
        messagesConfig.addDefault("command.nopermission", "&cYou do not have permission to use that command.", " The message displayed when no permission is detected for the /starcore command.");
        
        if (messagesConfig.contains("command.color.listsymbols.header")) {
            messagesConfig.set("command.color.listsymbols", null);
        }

        if (messagesConfig.contains("command.color.listcolors")) {
            messagesConfig.set("command.color.listcolors", null);
        }
        
        messagesConfig.addDefault("command.color.list.symbols", "&eList of valid color prefix characters: &r", " The format to use for the list of color symbols.");
        messagesConfig.addDefault("command.color.list.colors", "&eList of registered colors: ", " The header text for the /starcore colors list codes command");
        messagesConfig.addDefault("command.color.add.cannot-override-spigot", "&cYou cannot override default spigot colors. Please choose a different code.", " The error message for when someone tries to override a spigot color in /starcore color add command");
        messagesConfig.addDefault("command.color.add.invalid-code", "&cThe code you provided is not valid. Codes must be 2 characters and start with a valid symbol. &eUse the /starcore color listsymbols &ccommand.", " The error message for when someone tries to use an invalid 2 character code in the /starcore color add command.");
        messagesConfig.addDefault("command.color.remove.not-registered", "&cThe code you specified is not a registered color.", " The message sent when the code is not registered in the /starcore color remove command.");
        messagesConfig.addDefault("command.color.remove.success", "&eYou removed &b{OLDCODE} &eas a custom color.", " The message sent when the code is removed successfully in /starcore color remove command");
        
        if (mainConfig.contains("messages")) {
            Section messagesSection = mainConfig.getConfigurationSection("messages");
            for (String key : messagesSection.getKeys(true)) {
                messagesConfig.set(key, messagesSection.get(key));
            }
            
            mainConfig.set("messages", null);
        }
        
        messagesConfig.save();
        mainConfig.save();
        
        this.mcWrappers = new MCWrappersImpl();
        getServer().getServicesManager().register(MCWrappers.class, this.mcWrappers, this, ServicePriority.Highest);
        
        StarMCLib.GLOBAL_INJECTOR.setInstance(MCWrappers.class, this.mcWrappers);
        StarMCLib.GLOBAL_INJECTOR.setInstance(ItemWrapper.class, this.mcWrappers.getItemWrapper());
        StarMCLib.GLOBAL_INJECTOR.setInstance(EnchantWrapper.class, this.mcWrappers.getEnchantWrapper());
        StarMCLib.GLOBAL_INJECTOR.setInstance(PlayerHandWrapper.class, this.mcWrappers.getPlayerHandWrapper());
        
        getLogger().info("Initialized the MCWrapper utility");
        
        registerCommand("starcore", new StarCoreCmd());
        
        MinecraftVersion currentVersion = MinecraftVersion.CURRENT_VERSION;
        
        new Module_1_8(this);
        new Module_1_8_3(this);
        new Module_1_8_8(this);
        new Module_1_9_4(this);
        new Module_1_10_2(this);
        new Module_1_11_2(this);
        new Module_1_12_2(this);
        new Module_1_13(this);
        new Module_1_13_2(this);
        new Module_1_14_4(this);
        new Module_1_15_2(this);
        new Module_1_16_1(this);
        new Module_1_16_3(this);
        new Module_1_16_5(this);
        new Module_1_17_1(this);
        new Module_1_18_1(this);
        new Module_1_19_3(this);
        new Module_1_20_1(this);
        new Module_1_21_1(this);
        new Module_1_21_3(this);
        
        registerListeners(new BlockEvents(), new EnchantEvents(), new EntityEvents(), new HangingEvents(), new InventoryEvents(), 
                new PlayerEvents(), new ServerEvents(), new VehicleEvents(), new WeatherEvents(), new WorldEvents());
        
        PluginManager pluginManager = getServer().getPluginManager();
        
        //The pickup item event was deprecated and replaced in 1.12
        if (MinecraftVersion.CURRENT_VERSION.ordinal() < MinecraftVersion.v1_12.ordinal()) {
            registerListeners(new PlayerItemPickupListener_1_8_1_11(), new PlayerAchievmentListener_1_8_1_11());
        }
        
        if (MinecraftVersion.CURRENT_VERSION.ordinal() < MinecraftVersion.v1_14_4.ordinal()) {
            registerListeners(new EntityCreatePortalListener_1_8_1_14_4());
        }
        
        getLogger().info("Initialized the Event Passing listeners");
        getLogger().info("StarCore finished loading");
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
    
    public Configuration getMessagesConfig() {
        return messagesConfig;
    }
}
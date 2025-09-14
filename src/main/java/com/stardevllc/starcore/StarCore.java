package com.stardevllc.starcore;

import com.stardevllc.config.Config;
import com.stardevllc.config.Section;
import com.stardevllc.config.file.FileConfig;
import com.stardevllc.config.file.yaml.YamlConfig;
import com.stardevllc.starcore.api.StarColors;
import com.stardevllc.starcore.api.StarEvents;
import com.stardevllc.starcore.api.colors.CustomColor;
import com.stardevllc.starcore.api.events.*;
import com.stardevllc.starcore.api.itembuilder.ItemBuilder;
import com.stardevllc.starcore.api.wrappers.*;
import com.stardevllc.starcore.cmds.StarCoreCmd;
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
import com.stardevllc.starcore.v1_8_3.Module_1_8_3;
import com.stardevllc.starcore.v1_8_8.events.Module_1_8_8;
import com.stardevllc.starcore.v1_9_4.events.Module_1_9_4;
import com.stardevllc.starlib.observable.property.readwrite.ReadWriteBooleanProperty;
import com.stardevllc.starlib.observable.property.readwrite.ReadWriteUUIDProperty;
import com.stardevllc.starmclib.MinecraftVersion;
import com.stardevllc.starmclib.StarMCLib;
import com.stardevllc.starmclib.actors.Actors;
import com.stardevllc.starmclib.actors.ServerActor;
import com.stardevllc.starmclib.plugin.ExtendedJavaPlugin;
import com.stardevllc.starsql.model.*;
import com.stardevllc.starsql.model.Column.Option;
import com.stardevllc.starsql.model.Column.Type;
import com.stardevllc.starsql.statements.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;

import java.io.File;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.UUID;
import java.util.logging.Level;

public class StarCore extends ExtendedJavaPlugin {
    
    static {
        ItemBuilder.colorFunction = StarColors::color;
    }
    
    private FileConfig mainConfig;
    private FileConfig colorsConfig;
    private FileConfig messagesConfig;
    
    private PlayerManager playerManager;
    
    private MCWrappers mcWrappers;
    
    private Database database;
    
    //Default Settings
    private final ReadWriteUUIDProperty consoleUnqiueId;
    private final ReadWriteBooleanProperty saveColors;
    private final ReadWriteBooleanProperty savePlayerInfo;
    private final ReadWriteBooleanProperty useMojangAPI;
    
    private Table configTable;
    private Table playersTable;
    
    public StarCore() {
        this.consoleUnqiueId = new ReadWriteUUIDProperty(this, "consoleUniqueId", UUID.randomUUID());
        this.saveColors = new ReadWriteBooleanProperty(this, "saveColors", false);
        this.savePlayerInfo = new ReadWriteBooleanProperty(this, "savePlayerInfo", true);
        this.useMojangAPI = new ReadWriteBooleanProperty(this, "useMojangAPI", true);
    }
    
    public void onEnable() {
        super.onEnable();
        StarMCLib.init();
        StarMCLib.registerPluginEventBus(getEventBus());
        StarMCLib.registerPluginInjector(this, getInjector());
        StarEvents.addChildBus(getEventBus());
        
        this.mainConfig = new YamlConfig(new File(getDataFolder(), "config.yml"));
        this.mainConfig.load();
        getLogger().info("Initialized the main config");
        
        mainConfig.addDefault("mysql.enabled", false, "If true, mysql saving is enabled, if false, it is not enabled");
        mainConfig.addDefault("mysql.host", "127.0.0.1", "The host for the MySQL Database");
        mainConfig.addDefault("mysql.username", "username", "The username for the MySQL Database");
        mainConfig.addDefault("mysql.password", "password", "The password for the MySQL Database");
        mainConfig.addDefault("mysql.database", "database", "The database name for the MySQL Connection");
        mainConfig.addDefault("mysql.table-prefix", "starcore_", "The prefix used for the tables in the database");
        mainConfig.addDefault("mysql.config.name", "main", "The name of this MySQL Config.", "This allows switching between different loadouts and stores the same config as the standard config.yml", "This information is stored in the table {mysql.table-prefix}config");
        mainConfig.addDefault("mysql.port", 3306, "The port for the MySQL Connection");
        
        if (mainConfig.getBoolean("mysql.enabled")) {
            getLogger().info("Found that MySQL is enabled, running MySQL Set Up");
            String databaseName = mainConfig.getString("mysql.database");
            String url = "jdbc:mysql://" + mainConfig.get("mysql.host") + ":" + mainConfig.getInt("mysql.port") + "/" + databaseName;
            this.database = new Database(databaseName, url, mainConfig.getString("mysql.username"), mainConfig.getString("mysql.password"));
            getLogger().info("Attempting to connect to " + url);
            
            try {
                this.database.connect().close();
                getLogger().info("Connection successful");
            } catch (Throwable t) {
                getLogger().log(Level.SEVERE, "Error while trying to test the connection to the configured database", t);
                this.database = null;
            }
            
            if (this.database != null) {
                try {
                    this.database.retrieveDatabaseInformation();
                } catch (SQLException e) {
                    getLogger().warning("There was an SQLException while trying to automatically retrieve the database information");
                    getLogger().warning("This could be caused by a connection issue, or by the user that is used not having read access to the information_schema database");
                    getLogger().warning("You can safely ignore this warning if you do not wish for StarCore to do this automatically, however this may cause issues with missing columns when updating the plugin and you may manually have to update your database");
                    getLogger().warning("The changes between versions will be documented in changelogs, however you must know how to modify a MySQL database");
                }
                
                String tablePrefix = mainConfig.getString("mysql.table-prefix");
                configTable = this.database.getOrCreateTable(tablePrefix + "config");
                configTable.getOrCreateColumn("name", new Type("varchar", 25), 1, Option.PRIMARY_KEY);
                configTable.getOrCreateColumn("consoleuuid", new Type("varchar", 36), 2, Option.NULLABLE);
                configTable.getOrCreateColumn("savecolors", new Type("varchar", 5), 3);
                configTable.getOrCreateColumn("saveplayerinfo", new Type("varchar", 5), 4);
                configTable.getOrCreateColumn("usemojangapi", new Type("varchar", 5), 5);
                
                this.database.execute(new CreateTable(configTable.getName(), new HashSet<>(configTable.getColumns().values())).build());
                
                String configSelect = configTable.select().where(wc -> wc.addCondition("name", "=", mainConfig.get("mysql.config.name"))).build();
                this.database.executeQuery(configSelect, rs -> {
                    try {
                        if (!rs.next()) {
                            SqlInsert insert = new SqlInsert(configTable).columns("name", "consoleuuid", "savecolors", "saveplayerinfo", "usemojangapi").row(mainConfig.get("mysql.config.name"), consoleUnqiueId.get().toString(), String.valueOf(saveColors.get()), String.valueOf(savePlayerInfo.get()), String.valueOf(useMojangAPI.get()));
                            this.database.execute(insert.build());
                        } else {
                            consoleUnqiueId.set(UUID.fromString(rs.getString("consoleuuid")));
                            saveColors.set(Boolean.parseBoolean(rs.getString("savecolors")));
                            savePlayerInfo.set(Boolean.parseBoolean(rs.getString("saveplayerinfo")));
                            useMojangAPI.set(Boolean.parseBoolean(rs.getString("usemojangapi")));
                        }
                    } catch (SQLException e) {
                    }
                });
                
                playersTable = this.database.getOrCreateTable(tablePrefix + "players");
                playersTable.getOrCreateColumn("uniqueid", new Type("varchar", 36), 1, Option.PRIMARY_KEY);
                playersTable.getOrCreateColumn("name", new Type("varchar", 16), 2, Option.NULLABLE);
                playersTable.getOrCreateColumn("playtime", new Type("bigint"), 3);
                playersTable.getOrCreateColumn("firstlogin", new Type("timestamp"), 4);
                playersTable.getOrCreateColumn("lastlogin", new Type("timestamp"), 5);
                playersTable.getOrCreateColumn("lastlogout", new Type("timestamp"), 6);
                
                this.database.execute(new CreateTable(playersTable.getName(), new HashSet<>(playersTable.getColumns().values())).build());
            }
            getLogger().info("MySQL Set Up Finished (check for errors above)");
        }
        
        mainConfig.addDefault("console-uuid", this.consoleUnqiueId.get().toString(), "This is the unique id that is assigned to the console.", "Please do not change this manually.");
        if (this.database == null) {
            this.consoleUnqiueId.set(UUID.fromString(mainConfig.getString("console-uuid")));
        }
        ServerActor.serverUUID = this.consoleUnqiueId.get();
        Bukkit.getServer().getServicesManager().register(ServerActor.class, Actors.getServerActor(), this, ServicePriority.Highest);
        StarMCLib.GLOBAL_INJECTOR.setInstance(ServerActor.class, Actors.getServerActor());
        getLogger().info("Set the Console UUID to " + this.consoleUnqiueId.get());
        
        mainConfig.addDefault("save-colors", this.saveColors.get(), "This allows the plugin to save colors to colors.yml.", "Colors are defined using the command or by plugins.", "Only colors created by StarCore are saved to the file.");
        if (this.database == null) {
            this.saveColors.set(mainConfig.getBoolean("save-colors"));
        }
        
        if (saveColors.get()) {
            loadColors();
            getLogger().info("Loaded custom colors");
        }
        
        this.playerManager = injector.inject(new PlayerManager()).init();
        StarMCLib.GLOBAL_INJECTOR.setInstance(PlayerManager.class, this.playerManager);
        
        mainConfig.addDefault("save-player-info", this.savePlayerInfo.get(), "This allows the plugin to save a cache of player UUIDs to Names for offline fetching.", "Players must still join at least once though");
        
        if (this.database == null) {
            this.savePlayerInfo.set(mainConfig.getBoolean("save-player-info"));
        }
        
        if (savePlayerInfo.get()) {
            this.playerManager.load();
            getLogger().info("Loaded player data");
        }
        
        mainConfig.addDefault("use-mojang-api", this.useMojangAPI.get(), "Use the Mojang API to get Skin Info for players.", "This is retrieved when a player joins, and done async to prevent lag.", "Disabling this could break plugins that rely on this.");
        
        if (this.database == null) {
            this.useMojangAPI.set(mainConfig.getBoolean("use-mojang-api"));
        }
        
        mainConfig.save();
        
        messagesConfig = new YamlConfig(new File(getDataFolder(), "messages.yml"));
        this.messagesConfig.load();
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
        
        messagesConfig.save();
        
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
    
    public Database getDatabase() {
        return database;
    }
    
    public Table getConfigTable() {
        return configTable;
    }
    
    public Table getPlayersTable() {
        return playersTable;
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
        
        if (saveColors.get()) {
            loadColors();
        }
        
        if (savePlayerInfo.get()) {
            this.playerManager.load();
        }
        
        this.mainConfig.load();
        this.consoleUnqiueId.set(UUID.fromString(mainConfig.getString("console-uuid")));
        ServerActor.serverUUID = this.consoleUnqiueId.get();
    }
    
    public void loadColors() {
        this.colorsConfig = new YamlConfig(new File(getDataFolder(), "colors.yml"));
        if (this.colorsConfig.contains("colors")) {
            Section colorsSection = this.colorsConfig.getSection("colors");
            if (colorsSection != null) {
                for (String key : colorsSection.getKeys(false)) {
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
        if (this.database != null) {
            SqlInsertUpdate insert = new SqlInsertUpdate(mainConfig.get("mysql.table-prefix") + "config").primaryKeyColumn("name").columns("name", "consoleuuid", "savecolors", "saveplayerinfo", "usemojangapi").row(mainConfig.get("mysql.config.name"), consoleUnqiueId.get().toString(), String.valueOf(saveColors.get()), String.valueOf(savePlayerInfo.get()), String.valueOf(useMojangAPI.get()));
            this.database.execute(insert.build());
        }
        
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
        return consoleUnqiueId.get();
    }
    
    public Config getMainConfig() {
        return mainConfig;
    }
    
    public Config getMessagesConfig() {
        return messagesConfig;
    }
    
    public boolean isSaveColors() {
        return saveColors.get();
    }
    
    public boolean isSavePlayerInfo() {
        return savePlayerInfo.get();
    }
    
    public boolean isUseMojangAPI() {
        return useMojangAPI.get();
    }
}
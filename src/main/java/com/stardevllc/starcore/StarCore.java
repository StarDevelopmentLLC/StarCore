package com.stardevllc.starcore;

import com.stardevllc.starcore.actor.ServerActor;
import com.stardevllc.starcore.cmds.StarCoreCmd;
import com.stardevllc.starcore.color.ColorHandler;
import com.stardevllc.starcore.color.CustomColor;
import com.stardevllc.starcore.config.Config;
import com.stardevllc.starcore.gui.GuiManager;
import com.stardevllc.starcore.player.PlayerManager;
import com.stardevllc.starcore.skins.SkinManager;
import com.stardevllc.starcore.task.SpigotTaskFactory;
import com.stardevllc.starcore.v1_11.ItemWrapper_1_11;
import com.stardevllc.starcore.v1_13_R2.EnchantWrapper_1_13_R2;
import com.stardevllc.starcore.v1_13_R2.ItemWrapper_1_13_R2;
import com.stardevllc.starcore.v1_16.ColorHandler_1_16;
import com.stardevllc.starcore.v1_8.ColorHandler_1_8;
import com.stardevllc.starcore.v1_8.EnchantWrapper_1_8;
import com.stardevllc.starcore.v1_8.ItemWrapper_1_8;
import com.stardevllc.starcore.wrapper.EnchantWrapper;
import com.stardevllc.starcore.wrapper.ItemWrapper;
import com.stardevllc.starlib.clock.ClockManager;
import com.stardevllc.starlib.task.TaskFactory;
import com.stardevllc.starsql.StarSQL;
import com.stardevllc.starsql.model.DatabaseRegistry;
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
    private GuiManager guiManager;
    private SkinManager skinManager;

    private ItemWrapper itemWrapper;
    private EnchantWrapper enchantWrapper;
    private ColorHandler colorHandler;
    
    private PlayerManager playerManager;
    
    public void onEnable() {
        mainConfig = new Config(new File(getDataFolder(), "config.yml"));
        mainConfig.addDefault("console-uuid", UUID.randomUUID().toString(), " This is the unique id that is assigned to the console.", " Please do not change this manually.");
        this.consoleUnqiueId = UUID.fromString(mainConfig.getString("console-uuid"));
        ServerActor.serverUUID = this.consoleUnqiueId;
        Bukkit.getServer().getServicesManager().register(ServerActor.class, ServerActor.getServerActor(), this, ServicePriority.Highest);
        NMSVersion version = NMSVersion.CURRENT_VERSION;
        
        switch (version) {
            case v1_8_R1, v1_8_R2, v1_8_R3, v1_9_R1, v1_9_R2, v1_10_R1 -> {
                itemWrapper = new ItemWrapper_1_8();
                enchantWrapper = new EnchantWrapper_1_8();
                colorHandler = new ColorHandler_1_8();
            }
            case v1_11_R1, v1_12_R1, v1_13_R1 -> {
                itemWrapper = new ItemWrapper_1_11();
                enchantWrapper = new EnchantWrapper_1_8();
                colorHandler = new ColorHandler_1_8();
            }
            case v1_13_R2, v1_14_R1, v1_15_R1 -> {
                itemWrapper = new ItemWrapper_1_13_R2();
                enchantWrapper = new EnchantWrapper_1_13_R2();
                colorHandler = new ColorHandler_1_8();
            }
            default -> {
                itemWrapper = new ItemWrapper_1_13_R2();
                enchantWrapper = new EnchantWrapper_1_13_R2();
                colorHandler = new ColorHandler_1_16();
            }
        }
        
        ColorHandler.setInstance(colorHandler);
        
        Bukkit.getServer().getServicesManager().register(ColorHandler.class, colorHandler, this, ServicePriority.Highest);
        Bukkit.getServer().getServicesManager().register(ItemWrapper.class, itemWrapper, this, ServicePriority.Highest);
        Bukkit.getServer().getServicesManager().register(EnchantWrapper.class, enchantWrapper, this, ServicePriority.Highest);

        mainConfig.addDefault("save-colors", false, " This allows the plugin to save colors to colors.yml.", "Colors are defined using the command or by plugins.", "Only colors created by StarCore are saved to the file.");
        if (mainConfig.getBoolean("save-colors")) {
            loadColors();
        }
        
        this.playerManager = new PlayerManager(this);
        Bukkit.getServer().getPluginManager().registerEvents(playerManager, this);
        
        mainConfig.addDefault("save-player-info", true, " This allows the plugin to save a cache of player UUIDs to Names for offline fetching.", "Players must still join at least once though");
        if (mainConfig.getBoolean("save-player-info")) {
            playerManager.load();
        }
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

        getServer().getServicesManager().register(TaskFactory.class, new SpigotTaskFactory(this), this, ServicePriority.Normal);
        ClockManager clockManager = new ClockManager(getLogger(), 50L);
        getServer().getServicesManager().register(ClockManager.class, clockManager, this, ServicePriority.Normal);
        getServer().getScheduler().runTaskTimer(this, clockManager.getRunnable(), 1L, 1L);
        
        this.skinManager = new SkinManager();
        getServer().getServicesManager().register(SkinManager.class, this.skinManager, this, ServicePriority.Normal);

        guiManager = new GuiManager(this);
        guiManager.setup();
        getServer().getServicesManager().register(GuiManager.class, guiManager, this, ServicePriority.Normal);

        StarSQL.setLogger(getLogger());
        getServer().getServicesManager().register(DatabaseRegistry.class, StarSQL.createDatabaseRegistry(), this, ServicePriority.Highest);

        getCommand("starcore").setExecutor(new StarCoreCmd(this));
    }

    public void reload(boolean save) {
        if (save) {
            saveColors();
            this.playerManager.save();
        }

        colorHandler.getCustomColors().forEach((code, color) -> {
            if (color.getOwner().getName().equalsIgnoreCase(getName())) {
                colorHandler.removeColor(code);
            }
        });
    
        if (mainConfig.getBoolean("save-colors")) {
            loadColors();
        }
        
        if (mainConfig.getBoolean("save-player-info")) {
            this.playerManager.load();
        }

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
                    colorHandler.addCustomColor(customColor);
                }
            }
        }
    }

    public ItemWrapper getItemWrapper() {
        return itemWrapper;
    }

    public EnchantWrapper getEnchantWrapper() {
        return enchantWrapper;
    }

    public void saveColors() {
        if (colorsConfig != null) {
            colorsConfig.set("colors", null);
            for (CustomColor color : colorHandler.getCustomColors().values()) {
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

    public UUID getConsoleUnqiueId() {
        return consoleUnqiueId;
    }

    public Config getMainConfig() {
        return mainConfig;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

    public SkinManager getSkinManager() {
        return skinManager;
    }
}
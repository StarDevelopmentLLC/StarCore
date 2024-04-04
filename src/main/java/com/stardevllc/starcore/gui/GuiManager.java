package com.stardevllc.starcore.gui;

import com.stardevllc.starcore.gui.gui.InventoryGUI;
import com.stardevllc.starcore.gui.handler.InventoryHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

/**
 * The main class of the library that handles the logic and main fuction <br>
 * Please see the methods and {@link InventoryHandler} and {@link InventoryGUI} for full usage information
 */
public class GuiManager implements Listener {
    private JavaPlugin plugin;
    private Map<Inventory, InventoryHandler> activeHandlers = new HashMap<>();

    /**
     * Constructs a new GuiManager instnace
     * @param plugin The plugin for the listeners
     */
    public GuiManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Registers the listeners to the provided plugin
     */
    public void setup() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * Registers a tracked inventory and handler
     * @param inventory The inventory
     * @param handler The handler for the inventory
     */
    public void registerInventory(Inventory inventory, InventoryHandler handler) {
        this.activeHandlers.put(inventory, handler);
    }

    /**
     * Deregisters a tracked inventory - Note: Inventories are automatically unregistered when closed
     * @param inventory The inventory to remove
     */
    public void unregisterInventory(Inventory inventory) {
        this.activeHandlers.remove(inventory);
    }

    /**
     * @param inventory The inventory to check
     * @return If the inventory is tracked by this GuiManager
     */
    public boolean isRegisteredInventory(Inventory inventory) {
        return this.activeHandlers.containsKey(inventory);
    }
    
    @EventHandler
    public void onOpen(InventoryOpenEvent e) {
        InventoryHandler handler = this.activeHandlers.get(e.getInventory());
        if (handler != null) {
            handler.onOpen(e);
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClose(InventoryCloseEvent e) {
        Inventory inventory = e.getInventory();
        InventoryHandler handler = this.activeHandlers.get(inventory);
        if (handler != null) {
            handler.onClose(e);
            this.unregisterInventory(inventory);
        }
    }
    
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        InventoryHandler handler = this.activeHandlers.get(e.getInventory());
        if (handler != null) {
            handler.onClick(e);
        }
    }
    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        InventoryHandler handler = this.activeHandlers.get(e.getInventory());
        if (handler != null) {
            handler.onDrag(e);
        }
    }

    /**
     * @param inventory The inventory
     * @return The Handler instance for an Inventory
     */
    public InventoryHandler getHandler(Inventory inventory) {
        return this.activeHandlers.get(inventory);
    }

    /**
     * Opens a Gui for a player
     * @param gui The Gui to open
     * @param player The player to open the gui to
     */
    public void openGUI(InventoryGUI gui, Player player) {
        gui.decorate(player);
        Inventory inventory = gui.getInventory();
        this.activeHandlers.put(inventory, gui);
        player.openInventory(inventory);
    }

    /**
     * @return All activily tracked inventories and handlers
     */
    public Map<Inventory, InventoryHandler> getActiveHandlers() {
        return new HashMap<>(this.activeHandlers);
    }
}
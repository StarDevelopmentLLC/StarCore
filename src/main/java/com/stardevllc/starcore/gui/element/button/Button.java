package com.stardevllc.starcore.gui.element.button;

import com.stardevllc.starcore.gui.element.Element;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * An element that has an action attacked to it
 */
public class Button extends Element {
    
    protected Consumer<InventoryClickEvent> eventConsumer;
    protected Sound clickSound;
    protected float pitch;

    /**
     * Sets the action for this button. It is checked for slot already and correct Inventory
     * @param consumer The consumer containing the event
     * @return An instance of this class - Builder Style
     */
    public Button consumer(Consumer<InventoryClickEvent> consumer) {
        this.eventConsumer = consumer;
        return this;
    }

    /**
     * Sets the sound for the click
     * @param sound The sound
     * @param pitch The pitch of the sound
     * @return An instance of this class - Builder Style
     */
    public Button clickSound(Sound sound, float pitch) {
        this.clickSound = sound;
        this.pitch = pitch;
        return this;
    }

    /**
     * @return The event consumber
     */
    public Consumer<InventoryClickEvent> getEventConsumer() {
        return eventConsumer;
    }

    /**
     * Utility method to play the sound
     * @param player The player to play the sound
     */
    public void playSound(Player player) {
        if (clickSound != null) {
            player.playSound(player.getLocation(), clickSound, 1F, pitch);
        }
    }

    /**
     * Overridden method to change the return type to this class 
     * @param iconCreator The fuction instance
     * @return An instance of this class - Builder Style
     */
    @Override
    public Button iconCreator(Function<Player, ItemStack> iconCreator) {
        super.iconCreator(iconCreator);
        return this;
    }
}
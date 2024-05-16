package com.stardevllc.starcore.gui.element.button;

import com.stardevllc.starcore.gui.element.Element;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;
import java.util.function.Function;

public class Button extends Element {
    
    protected Consumer<InventoryClickEvent> eventConsumer;
    protected Sound clickSound;
    protected float pitch;

    public Button consumer(Consumer<InventoryClickEvent> consumer) {
        this.eventConsumer = consumer;
        return this;
    }

    public Button clickSound(Sound sound, float pitch) {
        this.clickSound = sound;
        this.pitch = pitch;
        return this;
    }

    public Consumer<InventoryClickEvent> getEventConsumer() {
        return eventConsumer;
    }

    public void playSound(Player player) {
        if (clickSound != null) {
            player.playSound(player.getLocation(), clickSound, 1F, pitch);
        }
    }

    @Override
    public Button iconCreator(Function<Player, ItemStack> iconCreator) {
        super.iconCreator(iconCreator);
        return this;
    }
}
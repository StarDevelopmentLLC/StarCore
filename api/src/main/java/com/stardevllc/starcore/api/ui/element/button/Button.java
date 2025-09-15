package com.stardevllc.starcore.api.ui.element.button;

import com.stardevllc.starcore.api.ui.element.Element;
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
    
    public Button() {}
    
    public Button(Function<Player, ItemStack> iconCreator, Consumer<InventoryClickEvent> eventConsumer, Sound clickSound, float pitch) {
        this.iconCreator = iconCreator;
        this.eventConsumer = eventConsumer;
        this.clickSound = clickSound;
        this.pitch = pitch;
    }
    
    public Button(Function<Player, ItemStack> iconCreator, Consumer<InventoryClickEvent> eventConsumer) {
        this.iconCreator = iconCreator;
        this.eventConsumer = eventConsumer;
    }

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
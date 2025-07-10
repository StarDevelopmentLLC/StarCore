package com.stardevllc.starcore.api.wrappers;

import org.bukkit.inventory.EquipmentSlot;

import java.util.UUID;

public class AttributeModifierWrapper {
    private final UUID uuid;
    private final String name;
    private final double amount;
    private final String operation;
    private final EquipmentSlot slot;

    public AttributeModifierWrapper(UUID uuid, String name, double amount, String operation, EquipmentSlot slot) {
        this.uuid = uuid;
        this.name = name;
        this.amount = amount;
        this.operation = operation;
        this.slot = slot;
    }

    public AttributeModifierWrapper(UUID uuid, String attribute, String name, double amount, String operation) {
        this(uuid, name, amount, operation, EquipmentSlot.HAND);
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getOperation() {
        return operation;
    }

    public EquipmentSlot getSlot() {
        return slot;
    }
}

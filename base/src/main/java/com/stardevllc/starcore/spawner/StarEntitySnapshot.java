package com.stardevllc.starcore.spawner;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public interface StarEntitySnapshot {
    Entity createEntity(Location location);
    Entity createEntity(World world);
    String getAsString();
    EntityType getEntityType();
}
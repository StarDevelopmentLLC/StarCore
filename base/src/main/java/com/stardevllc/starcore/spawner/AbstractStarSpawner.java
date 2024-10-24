package com.stardevllc.starcore.spawner;

import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

import java.util.Collection;
import java.util.List;

public abstract class AbstractStarSpawner implements StarSpawner {

    protected Location spawnerLocation;

    public AbstractStarSpawner(Location spawnerLocation) {
        this.spawnerLocation = spawnerLocation;
    }

    public EntityType getSpawnedType() {
        BlockState blockState = spawnerLocation.getBlock().getState();
        if (blockState instanceof CreatureSpawner creatureSpawner) {
            return creatureSpawner.getSpawnedType();
        }
        
        return null;
    }

    public void setSpawnedType(EntityType entityType) {
        BlockState blockState = spawnerLocation.getBlock().getState();
        if (blockState instanceof CreatureSpawner creatureSpawner) {
            creatureSpawner.setSpawnedType(entityType);
        }
    }

    public int getDelay() {
        BlockState blockState = spawnerLocation.getBlock().getState();
        if (blockState instanceof CreatureSpawner creatureSpawner) {
            return creatureSpawner.getDelay();
        }

        return 0;
    }

    public void setDelay(int delay) {
        BlockState blockState = spawnerLocation.getBlock().getState();
        if (blockState instanceof CreatureSpawner creatureSpawner) {
            creatureSpawner.setDelay(delay);
        }
    }

    @Override
    public int getMinSpawnDelay() {
        return 0;
    }

    @Override
    public void setMinSpawnDelay(int var1) {

    }

    @Override
    public int getMaxSpawnDelay() {
        return 0;
    }

    @Override
    public void setMaxSpawnDelay(int var1) {

    }

    @Override
    public int getSpawnCount() {
        return 0;
    }

    @Override
    public void setSpawnCount(int var1) {

    }

    @Override
    public int getMaxNearbyEntities() {
        return 0;
    }

    @Override
    public void setMaxNearbyEntities(int var1) {

    }

    @Override
    public int getRequiredPlayerRange() {
        return 0;
    }

    @Override
    public void setRequiredPlayerRange(int var1) {

    }

    @Override
    public int getSpawnRange() {
        return 0;
    }

    @Override
    public void setSpawnRange(int var1) {

    }

    @Override
    public StarEntitySnapshot getSpawnedEntity() {
        return null;
    }

    @Override
    public void setSpawnedEntity(StarEntitySnapshot var1) {

    }

    @Override
    public void setSpawnedEntity(StarSpawnerEntry var1) {

    }

    @Override
    public void addPotentialSpawn(StarEntitySnapshot var1, int var2, StarSpawnRule var3) {

    }

    @Override
    public void addPotentialSpawn(StarSpawnerEntry var1) {

    }

    @Override
    public void setPotentialSpawns(Collection<StarSpawnerEntry> var1) {

    }

    @Override
    public List<StarSpawnerEntry> getPotentialSpawns() {
        return List.of();
    }
}

package com.stardevllc.starcore.v1_13;

import com.stardevllc.starcore.spawner.AbstractStarSpawner;
import org.bukkit.Location;
import org.bukkit.block.CreatureSpawner;

public class StarSpawner_1_13 extends AbstractStarSpawner {
    public StarSpawner_1_13(Location spawnerLocation) {
        super(spawnerLocation);
    }

    @Override
    public int getMinSpawnDelay() {
        if (spawnerLocation.getBlock().getState() instanceof CreatureSpawner creatureSpawner) {
            return creatureSpawner.getMinSpawnDelay();
        }
        return super.getMinSpawnDelay();
    }

    @Override
    public void setMinSpawnDelay(int var1) {
        if (spawnerLocation.getBlock().getState() instanceof CreatureSpawner creatureSpawner) {
            creatureSpawner.setMinSpawnDelay(var1);
        }
    }

    @Override
    public int getMaxSpawnDelay() {
        if (spawnerLocation.getBlock().getState() instanceof CreatureSpawner creatureSpawner) {
            return creatureSpawner.getMaxSpawnDelay();
        }
        return super.getMaxSpawnDelay();
    }

    @Override
    public void setMaxSpawnDelay(int var1) {
        if (spawnerLocation.getBlock().getState() instanceof CreatureSpawner creatureSpawner) {
            creatureSpawner.setMaxSpawnDelay(var1);
        }
    }

    @Override
    public int getSpawnCount() {
        if (spawnerLocation.getBlock().getState() instanceof CreatureSpawner creatureSpawner) {
            return creatureSpawner.getSpawnCount();
        }
        return super.getSpawnCount();
    }

    @Override
    public void setSpawnCount(int var1) {
        if (spawnerLocation.getBlock().getState() instanceof CreatureSpawner creatureSpawner) {
            creatureSpawner.setSpawnCount(var1);
        }
    }

    @Override
    public int getMaxNearbyEntities() {
        if (spawnerLocation.getBlock().getState() instanceof CreatureSpawner creatureSpawner) {
            return creatureSpawner.getMaxNearbyEntities();
        }
        return super.getMaxNearbyEntities();
    }

    @Override
    public void setMaxNearbyEntities(int var1) {
        if (spawnerLocation.getBlock().getState() instanceof CreatureSpawner creatureSpawner) {
            creatureSpawner.setMaxNearbyEntities(var1);
        }
    }

    @Override
    public int getRequiredPlayerRange() {
        if (spawnerLocation.getBlock().getState() instanceof CreatureSpawner creatureSpawner) {
            return creatureSpawner.getRequiredPlayerRange();
        }
        return super.getRequiredPlayerRange();
    }

    @Override
    public void setRequiredPlayerRange(int var1) {
        if (spawnerLocation.getBlock().getState() instanceof CreatureSpawner creatureSpawner) {
            creatureSpawner.setRequiredPlayerRange(var1);
        }
    }

    @Override
    public int getSpawnRange() {
        if (spawnerLocation.getBlock().getState() instanceof CreatureSpawner creatureSpawner) {
            return creatureSpawner.getSpawnRange();
        }
        return super.getSpawnRange();
    }

    @Override
    public void setSpawnRange(int var1) {
        if (spawnerLocation.getBlock().getState() instanceof CreatureSpawner creatureSpawner) {
            creatureSpawner.setSpawnRange(var1);
        }
    }
}

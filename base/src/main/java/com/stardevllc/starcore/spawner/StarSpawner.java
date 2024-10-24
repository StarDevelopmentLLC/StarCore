package com.stardevllc.starcore.spawner;

import org.bukkit.entity.EntityType;

import java.util.Collection;
import java.util.List;

public interface StarSpawner {
    EntityType getSpawnedType();
    void setSpawnedType(EntityType var1);
    int getDelay();
    void setDelay(int var1);
    int getMinSpawnDelay();
    void setMinSpawnDelay(int var1);
    int getMaxSpawnDelay();
    void setMaxSpawnDelay(int var1);
    int getSpawnCount();
    void setSpawnCount(int var1);
    int getMaxNearbyEntities();
    void setMaxNearbyEntities(int var1);
    int getRequiredPlayerRange();
    void setRequiredPlayerRange(int var1);
    int getSpawnRange();
    void setSpawnRange(int var1);
    StarEntitySnapshot getSpawnedEntity();
    void setSpawnedEntity(StarEntitySnapshot var1);
    void setSpawnedEntity(StarSpawnerEntry var1);
    void addPotentialSpawn(StarEntitySnapshot var1, int var2, StarSpawnRule var3);
    void addPotentialSpawn(StarSpawnerEntry var1);
    void setPotentialSpawns(Collection<StarSpawnerEntry> var1);
    List<StarSpawnerEntry> getPotentialSpawns();
}

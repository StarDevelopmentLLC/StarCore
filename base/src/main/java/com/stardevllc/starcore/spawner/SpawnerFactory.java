package com.stardevllc.starcore.spawner;

import org.bukkit.Location;

import java.util.function.Function;

public final class SpawnerFactory {
    private static Function<Location, StarSpawner> spawnerFunction;

    public static void setSpawnerFunction(Function<Location, StarSpawner> spawnerFunction) {
        SpawnerFactory.spawnerFunction = spawnerFunction;
    }
    
    public static StarSpawner getSpawner(Location entityType) {
        if (spawnerFunction == null) {
            return null;
        }
        return spawnerFunction.apply(entityType);
    }
}

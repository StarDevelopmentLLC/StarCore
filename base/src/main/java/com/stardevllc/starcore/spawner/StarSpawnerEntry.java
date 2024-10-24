package com.stardevllc.starcore.spawner;

public class StarSpawnerEntry {
    private StarEntitySnapshot snapshot;
    private int spawnWeight;
    private StarSpawnRule spawnRule;

    public StarSpawnerEntry(StarEntitySnapshot snapshot, int spawnWeight, StarSpawnRule spawnRule) {
        this.snapshot = snapshot;
        this.spawnWeight = spawnWeight;
        this.spawnRule = spawnRule;
    }

    public StarEntitySnapshot getSnapshot() {
        return snapshot;
    }

    public int getSpawnWeight() {
        return spawnWeight;
    }

    public StarSpawnRule getSpawnRule() {
        return spawnRule;
    }

    public void setSnapshot(StarEntitySnapshot snapshot) {
        this.snapshot = snapshot;
    }

    public void setSpawnWeight(int spawnWeight) {
        this.spawnWeight = spawnWeight;
    }

    public void setSpawnRule(StarSpawnRule spawnRule) {
        this.spawnRule = spawnRule;
    }
}
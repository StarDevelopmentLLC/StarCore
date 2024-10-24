package com.stardevllc.starcore.spawner;

public class StarSpawnRule {
    private int minBlockLight;
    private int maxBlockLight;
    private int minSkyLight;
    private int maxSkyLight;

    public StarSpawnRule(int minBlockLight, int maxBlockLight, int minSkyLight, int maxSkyLight) {
        this.minBlockLight = minBlockLight;
        this.maxBlockLight = maxBlockLight;
        this.minSkyLight = minSkyLight;
        this.maxSkyLight = maxSkyLight;
    }

    public int getMinBlockLight() {
        return minBlockLight;
    }

    public int getMaxBlockLight() {
        return maxBlockLight;
    }

    public int getMinSkyLight() {
        return minSkyLight;
    }

    public int getMaxSkyLight() {
        return maxSkyLight;
    }

    public void setMinBlockLight(int minBlockLight) {
        this.minBlockLight = minBlockLight;
    }

    public void setMaxBlockLight(int maxBlockLight) {
        this.maxBlockLight = maxBlockLight;
    }

    public void setMinSkyLight(int minSkyLight) {
        this.minSkyLight = minSkyLight;
    }

    public void setMaxSkyLight(int maxSkyLight) {
        this.maxSkyLight = maxSkyLight;
    }
}

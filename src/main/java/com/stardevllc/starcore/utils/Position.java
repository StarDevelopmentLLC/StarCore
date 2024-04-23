package com.stardevllc.starcore.utils;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.Objects;

public class Position {
    protected double x, y, z;
    protected float yaw, pitch;

    public static Position fromLocation(Location location) {
        return new Position(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public Position() {
        this(0, 0, 0);
    }

    public Position(double x, double y, double z) {
        this(x, y, z, 0, 0);
    }

    public Position(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public double getX() {
        return x;
    }
    
    public int getBlockX() {
        return (int) x;
    }

    public double getY() {
        return y;
    }
    
    public int getBlockY() {
        return (int) y;
    }

    public double getZ() {
        return z;
    }
    
    public int getBlockZ() {
        return (int) z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public Location toLocation(World world) {
        return new Location(world, x, y, z, yaw, pitch);
    }
    
    public Location toBlockLocation(World world) {
        return new Location(world, getBlockX(), getBlockY(), getBlockZ());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return x == position.x && y == position.y && z == position.z;
    }

    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
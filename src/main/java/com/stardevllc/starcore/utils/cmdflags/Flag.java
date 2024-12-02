package com.stardevllc.starcore.utils.cmdflags;

public interface Flag {
    String id();
    FlagType type();
    String name();
    boolean equals(Object other);

    //Complex flag
    default Object defaultValue() {
        return null;
    }

    //Presence flag
    default Object valueIfPresent() {
        return true;
    }

    default Object valueIfNotPresent() {
        return false;
    }
}

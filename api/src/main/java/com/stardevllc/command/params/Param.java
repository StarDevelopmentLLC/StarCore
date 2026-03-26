package com.stardevllc.command.params;

import java.util.Objects;
import java.util.function.Predicate;

public record Param<T>(String id, String name, Class<T> type, T defaultValue, Predicate<T> emptyPredicate) {
    public Param(String id, String name, Class<T> type, T defaultValue, Predicate<T> emptyPredicate) {
        this.id = id;
        this.name = name;
        this.type = type;
        if (defaultValue == null) {
            throw new IllegalArgumentException("Default value cannot be null");
        }
        this.defaultValue = defaultValue;
        if (emptyPredicate != null) {
            this.emptyPredicate = emptyPredicate;
        } else {
            this.emptyPredicate = Objects::isNull;
        }
    }
    
    public Param(String id, String name, Class<T> type, T defaultValue) {
        this(id, name, type, defaultValue, null);
    }
    
    public boolean isEmpty(T value) {
        return emptyPredicate.test(value);
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof Param<?> otherFlag) {
            return id().equalsIgnoreCase(otherFlag.id());
        }
        
        return false;
    }
}
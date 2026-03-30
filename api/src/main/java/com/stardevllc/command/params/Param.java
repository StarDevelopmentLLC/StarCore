package com.stardevllc.command.params;

import com.stardevllc.starlib.objects.builder.IBuilder;

import java.util.Objects;
import java.util.function.Predicate;

public record Param<T>(String id, String name, String[] aliases, Class<T> type, T defaultValue, Predicate<T> emptyPredicate) {
    public Param(String id, String name, String[] aliases, Class<T> type, T defaultValue, Predicate<T> emptyPredicate) {
        this.id = id;
        this.name = name;
        this.aliases = aliases;
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
    
    public Param(String id, String name, String[] aliases, Class<T> type, T defaultValue) {
        this(id, name, aliases, type, defaultValue, null);
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
    
    public static <T> Builder<T> builder(Class<T> type) {
        return new Builder<>(type);
    }
    
    public static final class Builder<T> implements IBuilder<Param<T>, Builder<T>> {
        private final Class<T> type;
        private String id, name;
        private String[] aliases;
        private T defaultValue;
        private Predicate<T> emptyPredicate;
        
        public Builder(Class<T> type) {
            this.type = type;
        }
        
        public Builder(Builder<T> builder) {
            this.type = builder.type;
            this.id = builder.id;
            this.name = builder.name;
            this.aliases = builder.aliases;
            this.defaultValue = builder.defaultValue;
            this.emptyPredicate = builder.emptyPredicate;
        }
        
        public Builder<T> id(String id) {
            this.id = id;
            return self();
        }
        
        public Builder<T> name(String name) {
            this.name = name;
            return self();
        }
        
        public Builder<T> aliases(String... aliases) {
            this.aliases = aliases;
            return self();
        }
        
        public Builder<T> defaultValue(T defaultValue) {
            this.defaultValue = defaultValue;
            return self();
        }
        
        public Builder<T> emptyPredicate(Predicate<T> predicate) {
            this.emptyPredicate = predicate;
            return self();
        }
        
        @Override
        public Param<T> build() {
            return new Param<>(id, name, aliases, type, defaultValue, emptyPredicate);
        }
        
        @Override
        public Builder<T> clone() {
            return new Builder<>(this);
        }
    }
}
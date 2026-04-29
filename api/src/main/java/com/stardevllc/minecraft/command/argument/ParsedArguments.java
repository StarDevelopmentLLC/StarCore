package com.stardevllc.minecraft.command.argument;

import com.stardevllc.starlib.tuple.pair.ImmutablePair;
import com.stardevllc.starlib.tuple.pair.Pair;

import java.util.*;

public class ParsedArguments {
    
    private final Map<Integer, Pair<Argument<?>, Object>> values = new TreeMap<>();
    
    public void add(int index, Argument<?> argument, Object value) {
        values.put(index, ImmutablePair.of(argument, value));
    }
    
    public boolean has(int index) {
        Pair<Argument<?>, Object> pair = values.get(index);
        
        if (pair == null) {
            return false;
        }
        
        if (pair.getLeft() == null) {
            values.remove(index);
            return false;
        }
        
        if (pair.getRight() == null) {
            values.remove(index);
            return false;
        }
        
        return true;
    }
    
    public <T> T get(int index) {
        Pair<Argument<?>, Object> pair = values.get(index);
        
        if (pair == null) {
            return null;
        }
        
        Argument<?> argument = pair.getLeft();
        
        if (argument == null) {
            values.remove(index);
            return null;
        }
        
        Object value = pair.getRight();
        
        if (value == null) {
            values.remove(index);
            return null;
        }
        
        if (!argument.getValueType().isInstance(value)) {
            return null;
        }
        
        try {
            return (T) value;
        } catch (ClassCastException e) {
            return null;
        }
    }
}
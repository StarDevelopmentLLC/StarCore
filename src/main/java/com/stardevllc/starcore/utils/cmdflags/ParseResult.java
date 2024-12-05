package com.stardevllc.starcore.utils.cmdflags;

import java.util.Map;

public record ParseResult(String[] args, Map<Flag, Object> flagValues) {
    public boolean isPresent(Flag flag) {
        if (flag.type() == FlagType.COMPLEX) {
            return flagValues.containsKey(flag);
        } else {
            Object flagValue = flagValues.get(flag);
            if (flagValue != null) {
                return (boolean) flagValue;
            }
        }
        
        return false;
    }
    
    public Object getValue(Flag flag) {
        return flagValues.get(flag);
    }
}

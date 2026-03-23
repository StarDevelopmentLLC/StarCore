package com.stardevllc.starmclib.command.params;

import java.util.Map;

public record ParamResult(String[] args, Map<Param<?>, Object> paramValues) {
    public boolean isPresent(Param<?> param) {
        return paramValues.containsKey(param);
    }
    
    public void addFrom(ParamResult otherResults) {
        paramValues.putAll(otherResults.paramValues);
    }
    
    public <T> T getValue(Param<T> param) {
        T value = (T) paramValues.get(param);
        return param.isEmpty(value) ? param.defaultValue() : value;
    }
}
package com.stardevllc.starmclib.command.params;

import com.stardevllc.starlib.converter.string.StringConverter;
import com.stardevllc.starlib.converter.string.StringConverters;

import java.util.*;

@SuppressWarnings("DuplicatedCode")
public class CmdParams {
    protected final Set<Param<?>> params = new HashSet<>();
    
    public CmdParams(Set<Param<?>> params) {
        this.params.addAll(params);
    }
    
    public CmdParams(Param<?>... params) {
        if (params != null) {
            this.params.addAll(Set.of(params));
        }
    }
    
    public void addFlag(Param<?> param, Param<?>... moreParams) {
        this.params.add(param);
        if (moreParams != null) {
            this.params.addAll(Set.of(moreParams));
        }
    }
    
    public ParamResult parse(String[] args) {
        LinkedList<String> argsList = new LinkedList<>(List.of(args));
        ListIterator<String> iterator = argsList.listIterator();
        
        Map<Param<?>, Object> paramValues = new HashMap<>();
        
        Set<Param<?>> params = new HashSet<>(this.params);
        
        iterLoop:
        while (iterator.hasNext()) {
            String arg = iterator.next().toLowerCase();
            
            for (Param<?> param : new HashSet<>(params)) {
                if (arg.startsWith(param.id().toLowerCase() + ":")) {
                    iterator.remove();
                    params.remove(param);
                    String[] split = arg.split(":");
                    if (split.length != 2) {
                        continue iterLoop;
                    }
                    StringBuilder value = new StringBuilder(split[1]);
                    StringConverter<?> converter = StringConverters.getConverter(param.type());
                    if (converter == null || param.defaultValue().getClass().equals(String.class)) {
                        paramValues.put(param, value.toString());
                    } else {
                        paramValues.put(param, converter.convertTo(value.toString()));
                    }
                }
            }
        }
        
        return new ParamResult(argsList.toArray(new String[0]), paramValues);
    }
}
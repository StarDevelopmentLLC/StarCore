package com.stardevllc.minecraft.command.argument;

import com.stardevllc.minecraft.command.CommandContext;

import java.util.List;

public interface Argument<T> {
    String id();
    
    String name();
    
    String description();
    
    T parse(String str);
    
    Class<T> getValueType();
    
    List<String> getCompletions(CommandContext context);
    
    boolean optional();
}
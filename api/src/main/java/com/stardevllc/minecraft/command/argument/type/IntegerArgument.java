package com.stardevllc.minecraft.command.argument.type;

import com.stardevllc.minecraft.command.CommandContext;
import com.stardevllc.minecraft.command.argument.Argument;

import java.util.List;

public record IntegerArgument(String id, String name, String description, boolean optional) implements Argument<Integer> {
    
    public static IntegerArgument of(String id, String name, String description, boolean optional) {
        return new IntegerArgument(id, name, description, optional);
    }
    
    public static IntegerArgument of(String id, String name, String description) {
        return new IntegerArgument(id, name, description, false);
    }
    
    public static IntegerArgument of(String id, String name) {
        return new IntegerArgument(id, name, "", false);
    }
    
    @Override
    public Integer parse(String str) {
        if (str == null || str.isBlank()) {
            return 0;
        }
        
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return Integer.MIN_VALUE;
        }
    }
    
    @Override
    public Class<Integer> getValueType() {
        return int.class;
    }
    
    @Override
    public List<String> getCompletions(CommandContext context) {
        return List.of();
    }
}
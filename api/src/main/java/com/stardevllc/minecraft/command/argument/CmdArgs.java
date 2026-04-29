package com.stardevllc.minecraft.command.argument;

import java.util.Map;
import java.util.TreeMap;

public class CmdArgs {
    private final Map<Integer, Argument<?>> arguments = new TreeMap<>();
    
    public void add(int index, Argument<?> argument) {
        this.arguments.put(index, argument);
    }
    
    public ParsedArguments parse(String[] args) {
        ParsedArguments parsedArguments = new ParsedArguments();
        
        for (Map.Entry<Integer, Argument<?>> entry : arguments.entrySet()) {
            int index = entry.getKey();
            Argument<?> argument = entry.getValue();
            
            
            if (index < args.length) {
                String arg = args[index];
                Object value = argument.parse(arg);
                if (value != null) {
                    parsedArguments.add(index, argument, value);
                }
            }
        }
        
        return parsedArguments;
    }
}
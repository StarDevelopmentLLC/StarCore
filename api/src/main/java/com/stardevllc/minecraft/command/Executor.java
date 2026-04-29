package com.stardevllc.minecraft.command;

@FunctionalInterface
public interface Executor {
    boolean execute(CommandContext context);
}
package com.stardevllc.minecraft.command;

import java.util.List;

@FunctionalInterface
public interface Completer {
    List<String> complete(CommandContext context);
}
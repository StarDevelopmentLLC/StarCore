package com.stardevllc.starcore.utils.objecttester;

import org.bukkit.command.CommandSender;

@FunctionalInterface
public interface Selector<T> {
    T select(CommandSender sender, String[] args);
}

package com.stardevllc.minecraft.command;

import com.stardevllc.minecraft.command.argument.ParsedArguments;
import com.stardevllc.minecraft.command.flags.FlagResult;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public record CommandContext(JavaPlugin plugin,
                             CommandSender sender,
                             String label,
                             String[] args,
                             FlagResult flags,
                             ParsedArguments parsedArgs) {
}
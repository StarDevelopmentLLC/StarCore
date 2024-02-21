package com.stardevllc.starcore.cmds;

import com.stardevllc.starcore.StarCore;
import com.stardevllc.starmclib.color.ColorUtils;
import com.stardevllc.starmclib.color.CustomColor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StarCoreCmd implements CommandExecutor {

    private StarCore plugin;

    public StarCoreCmd(StarCore plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("starcore.admin")) {
            sender.sendMessage(ColorUtils.color("&cYou do not have permission to use that command."));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ColorUtils.color("&cUsage: /" + label + " <subcommand> <args>"));
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.reload(false);
            sender.sendMessage(ColorUtils.color("&aSuccessfully reloaded configs."));
        } else if (args[0].equalsIgnoreCase("color")) {
            if (!sender.hasPermission("starcore.admin.color")) {
                sender.sendMessage(ColorUtils.color("&cYou do not have permission to use that command."));
                return true;
            }

            if (!(args.length > 1)) {
                sender.sendMessage(ColorUtils.color("&cUsage: /" + label + " " + args[0] + " <listsymbols|listcolors|addcolor|removecolor>"));
                return true;
            }

            if (args[1].equalsIgnoreCase("listsymbols")) {
                sender.sendMessage(ColorUtils.color("&eList of valid color prefix characters: "));
                for (Character c : ColorUtils.getPrefixSymbols()) {
                    sender.sendMessage(ColorUtils.color("  &8- &b") + c);
                }
            } else if (args[1].equalsIgnoreCase("listcolors")) {
                sender.sendMessage(ColorUtils.color("&eList of non-default registered colors: "));
                ColorUtils.getCustomColors().forEach((code, color) -> sender.sendMessage(ColorUtils.color("  &8- &b") + code + "§8: §b" + color.getHex() + ColorUtils.color(" &8[&e" + color.getOwner().getName() + "&8]" + (!color.getPermission().isEmpty() ? " &8<&d" + color.getPermission() + "&8>" : ""))));
            } else if (args[1].equalsIgnoreCase("add")) {
                if (!(args.length > 3)) {
                    sender.sendMessage(ColorUtils.color("&cUsage: /" + label + " " + args[0] + " " + args[1] + " <code> <hex> [permission]"));
                    return true;
                }

                String code = args[2];

                if (ColorUtils.isSpigotColor(code)) {
                    sender.sendMessage(ColorUtils.color("&cYou cannot override default spigot colors. Please choose a different code."));
                    return true;
                }

                if (!ColorUtils.isValidCode(code)) {
                    sender.sendMessage(ColorUtils.color("&cThe code you provided is not valid. Codes must be 2 characters and start with a valid symbol. &eUse the /" + label + " " + args[0] + " listsymbols &ccommand."));
                    return true;
                }

                String hex = args[3];

                String permission = "";
                if (args.length > 4) {
                    permission = args[4];
                }

                if (!ColorUtils.isValidHex(hex)) {
                    sender.sendMessage(ColorUtils.color("&cThe color value you provided is not a valid HEX code."));
                    sender.sendMessage(ColorUtils.color("&c  HEX values must match ALL of the following"));
                    sender.sendMessage(ColorUtils.color("&c    - First Character must be a ") + "#");
                    sender.sendMessage(ColorUtils.color("&c    - The length must be 3 or 6 characters not including the ") + "#");
                    sender.sendMessage(ColorUtils.color("&c    - Characters must be 0-9 and a-f (case insensitive)"));
                    return true;
                }

                ColorUtils.addCustomColor(new CustomColor(plugin).hexValue(hex).symbolCode(code).permission(permission));
                sender.sendMessage(ChatColor.YELLOW + "You added " + ChatColor.AQUA + code + " " + ChatColor.YELLOW + "with the HEX Code " + ChatColor.AQUA + hex + ChatColor.YELLOW + " as a custom color.");
            } else if (args[1].equalsIgnoreCase("remove")) {
                if (!(args.length > 2)) {
                    sender.sendMessage(ColorUtils.color("&cUsage: /" + label + " " + args[0] + " " + args[1] + " <code>"));
                    return true;
                }

                String code = args[2];
                if (ColorUtils.getCustomColor(code) == null) {
                    sender.sendMessage(ColorUtils.color("&cThe code you specified is not a registered color."));
                    return true;
                }

                ColorUtils.removeColor(code);
                sender.sendMessage(ColorUtils.color("&eYou removed &b" + code + " &eas a custom color."));
            } else {
                sender.sendMessage(ColorUtils.color("&cInvalid subcommand."));
            }
        } else {
            ColorUtils.coloredMessage(sender, "&cInvalid subcommand.");
        }

        return true;
    }
}

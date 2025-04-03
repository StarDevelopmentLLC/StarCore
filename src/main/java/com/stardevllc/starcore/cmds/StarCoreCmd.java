package com.stardevllc.starcore.cmds;

import com.stardevllc.starcore.StarColors;
import com.stardevllc.starcore.StarCore;
import com.stardevllc.starcore.base.colors.ColorHandler;
import com.stardevllc.starcore.base.colors.CustomColor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class StarCoreCmd implements TabExecutor {

    private StarCore plugin;

    public StarCoreCmd(StarCore plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("starcore.admin")) {
            sender.sendMessage(StarColors.color(plugin.getMainConfig().getString("messages.command.nopermission")));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(StarColors.color("&cUsage: /" + label + " <subcommand> <args>"));
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("starcore.admin.reload")) {
                StarColors.coloredMessage(sender, plugin.getMainConfig().getString("messages.command.nopermission"));
                return true;
            }

            plugin.reload(false);
            sender.sendMessage(StarColors.color("&aSuccessfully reloaded configs."));
        } else if (args[0].equalsIgnoreCase("color")) {
            if (!sender.hasPermission("starcore.admin.color")) {
                sender.sendMessage(StarColors.color(plugin.getMainConfig().getString("messages.command.nopermission")));
                return true;
            }

            if (!(args.length > 1)) {
                sender.sendMessage(StarColors.color("&cUsage: /" + label + " " + args[0] + " list <symbols|colors>"));
                sender.sendMessage(StarColors.color("&cUsage: /" + label + " " + args[0] + " add <code> <hex> [permission]"));
                sender.sendMessage(StarColors.color("&cUsage: /" + label + " " + args[0] + " remove <code>"));
                return true;
            }

            if (args[1].equalsIgnoreCase("list")) {
                if (!sender.hasPermission("starcore.admin.color.list")) {
                    StarColors.coloredMessage(sender, plugin.getMainConfig().getString("messages.command.nopermission"));
                    return true;
                }

                if (!(args.length > 2)) {
                    StarColors.coloredMessage(sender, "&cUsage: /" + label + " " + args[0] + " " + args[1] + " symbols");
                    StarColors.coloredMessage(sender, "&cUsage: /" + label + " " + args[0] + " " + args[1] + " codes");
                    return true;
                }

                if (args[2].equalsIgnoreCase("symbols")) {
                    StringBuilder symbolBuilder = new StringBuilder();
                    for (Character c : StarColors.getPrefixSymbols()) {
                        symbolBuilder.append(StarColors.color("&b")).append(c).append(StarColors.color("&e, "));
                    }
                    String charList = symbolBuilder.toString().trim();
                    charList = charList.substring(0, charList.length() - 2);
                    sender.sendMessage(StarColors.color(plugin.getMainConfig().getString("messages.command.color.list.symbols.header")) + charList);
                } else if (args[2].equalsIgnoreCase("codes")) {
                    sender.sendMessage(StarColors.color(plugin.getMainConfig().getString("messages.command.color.list.colors")));
                    StarColors.getCustomColors().forEach((code, color) -> sender.sendMessage(StarColors.color("  &8- &b") + code + "§8: §b" + color.getHex() + StarColors.color(" &8[&e" + color.getOwner().getName() + "&8]" + (!color.getPermission().isEmpty() ? " &8<&d" + color.getPermission() + "&8>" : ""))));
                }
            } else if (args[1].equalsIgnoreCase("add")) {
                if (!sender.hasPermission("starcore.admin.color.add")) {
                    StarColors.coloredMessage(sender, plugin.getMainConfig().getString("messages.command.nopermission"));
                    return true;
                }

                if (!(args.length > 3)) {
                    sender.sendMessage(StarColors.color("&cUsage: /" + label + " " + args[0] + " " + args[1] + " <code> <hex> [permission]"));
                    return true;
                }

                String code = args[2];

                if (ColorHandler.isSpigotColor(code)) {
                    sender.sendMessage(StarColors.color(plugin.getMainConfig().getString("messages.command.color.add.cannot-override-spigot")));
                    return true;
                }

                if (!ColorHandler.isValidCode(code)) {
                    sender.sendMessage(StarColors.color(plugin.getMainConfig().getString("messages.command.color.add.invalid-code")));
                    return true;
                }

                String hex = args[3];

                String permission = "";
                if (args.length > 4) {
                    permission = args[4];
                }

                if (!ColorHandler.isValidHex(hex)) {
                    sender.sendMessage(StarColors.color("&cThe color value you provided is not a valid HEX code."));
                    sender.sendMessage(StarColors.color("&c  HEX values must match ALL of the following"));
                    sender.sendMessage(StarColors.color("&c    - First Character must be a ") + "#");
                    sender.sendMessage(StarColors.color("&c    - The length must be 3 or 6 characters not including the ") + "#");
                    sender.sendMessage(StarColors.color("&c    - Characters must be 0-9 and a-f (case insensitive)"));
                    return true;
                }

                StarColors.addCustomColor(new CustomColor(plugin).hexValue(hex).symbolCode(code).permission(permission));
                sender.sendMessage(ChatColor.YELLOW + "You added " + ChatColor.AQUA + code + " " + ChatColor.YELLOW + "with the HEX Code " + ChatColor.AQUA + hex + ChatColor.YELLOW + " as a custom color.");
            } else if (args[1].equalsIgnoreCase("remove")) {
                if (!sender.hasPermission("starcore.admin.color.remove")) {
                    StarColors.coloredMessage(sender, plugin.getMainConfig().getString("messages.command.nopermission"));
                    return true;
                }

                if (!(args.length > 2)) {
                    sender.sendMessage(StarColors.color("&cUsage: /" + label + " " + args[0] + " " + args[1] + " <code>"));
                    return true;
                }

                String code = args[2];
                if (StarColors.getCustomColor(code) == null) {
                    sender.sendMessage(StarColors.color(plugin.getMainConfig().getString("messages.command.color.remove.not-registered")));
                    return true;
                }

                StarColors.removeColor(code);
                sender.sendMessage(StarColors.color(plugin.getMainConfig().getString("messages.command.color.remove.success").replace("{OLDCODE}", code)));
            } else {
                sender.sendMessage(StarColors.color(plugin.getMainConfig().getString("messages.command.invalidsubcommand")));
            }
        } else {
            sender.sendMessage(StarColors.color(plugin.getMainConfig().getString("messages.command.invalidsubcommand")));
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("starcore.admin")) {
            return List.of();
        }

        if (args.length == 1) {
            return sortAndFilter(args[0], "reload", "color", "gui");
        }

        if (args[0].equalsIgnoreCase("gui")) {
            if (args.length == 2) {
                return sortAndFilter(args[1], "list");
            }
        }

        if (args[0].equalsIgnoreCase("color")) {
            if (args.length == 2) {
                return sortAndFilter(args[1], "list", "add", "remove");
            } else if (args[1].equalsIgnoreCase("list")) {
                if (args.length == 3) {
                    return sortAndFilter(args[2], "symbols", "codes");
                }
            } else if (args[1].equalsIgnoreCase("add")) {
                if (args.length == 3) {
                    return List.of("<code>");
                } else if (args.length == 4) {
                    return List.of("<hex>");
                } else if (args.length == 5) {
                    return List.of("[permission]");
                }
            } else if (args[1].equalsIgnoreCase("remove")) {
                if (args.length == 3) {
                    sortAndFilter(args[2], new LinkedList<>(StarColors.getCustomColors().keySet()));
                }
            }
        }

        return List.of();
    }
    
    private static List<String> sortAndFilter(String arg, List<String> completions) {
        completions.removeIf(option -> !option.toLowerCase().startsWith(arg));
        Collections.sort(completions);

        return completions;
    }

    private static List<String> sortAndFilter(String arg, String... values) {
        List<String> completions = new LinkedList<>();
        if (values == null) {
            return completions;
        }

        completions.addAll(List.of(values));
        return sortAndFilter(arg, completions);
    }
}
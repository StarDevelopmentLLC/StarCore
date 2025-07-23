package com.stardevllc.starcore.cmds;

import com.stardevllc.starcore.StarCore;
import com.stardevllc.starcore.api.StarColors;
import com.stardevllc.starcore.api.colors.ColorHandler;
import com.stardevllc.starcore.api.colors.CustomColor;
import com.stardevllc.starlib.dependency.Inject;
import com.stardevllc.starmclib.StarColorsAdventure;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class StarCoreCmd implements TabExecutor {

    @Inject
    private StarCore plugin;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        StarColorsAdventure colors = plugin.getColors();
        if (!sender.hasPermission("starcore.admin")) {
            colors.coloredLegacy(sender, plugin.getMessagesConfig().getString("command.nopermission"));
            return true;
        }

        if (args.length == 0) {
            colors.coloredLegacy(sender, "&cUsage: /" + label + " <subcommand> <args>");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("starcore.admin.reload")) {
                colors.coloredLegacy(sender, plugin.getMessagesConfig().getString("command.nopermission"));
                return true;
            }

            plugin.reload(false);
            colors.coloredLegacy(sender, "&aSuccessfully reloaded configs.");
        } else if (args[0].equalsIgnoreCase("color")) {
            if (!sender.hasPermission("starcore.admin.color")) {
                colors.coloredLegacy(sender, plugin.getMessagesConfig().getString("command.nopermission"));
                return true;
            }

            if (!(args.length > 1)) {
                colors.coloredLegacy(sender, "&cUsage: /" + label + " " + args[0] + " list <symbols|colors>");
                colors.coloredLegacy(sender, "&cUsage: /" + label + " " + args[0] + " add <code> <hex> [permission]");
                colors.coloredLegacy(sender, "&cUsage: /" + label + " " + args[0] + " remove <code>");
                return true;
            }

            if (args[1].equalsIgnoreCase("list")) {
                if (!sender.hasPermission("starcore.admin.color.list")) {
                    colors.coloredLegacy(sender, plugin.getMessagesConfig().getString("command.nopermission"));
                    return true;
                }

                if (!(args.length > 2)) {
                    colors.coloredLegacy(sender, "&cUsage: /" + label + " " + args[0] + " " + args[1] + " symbols");
                    colors.coloredLegacy(sender, "&cUsage: /" + label + " " + args[0] + " " + args[1] + " codes");
                    return true;
                }

                if (args[2].equalsIgnoreCase("symbols")) {
                    StringBuilder symbolBuilder = new StringBuilder();
                    for (Character c : StarColors.getPrefixSymbols()) {
                        symbolBuilder.append(StarColors.color("&b")).append(c).append(StarColors.color("&e, "));
                    }
                    String charList = symbolBuilder.toString().trim();
                    charList = charList.substring(0, charList.length() - 2);
                    sender.sendMessage(colors.colorLegacy(plugin.getMessagesConfig().getString("command.color.list.symbols")) + charList);
                } else if (args[2].equalsIgnoreCase("codes")) {
                    sender.sendMessage(StarColors.color(plugin.getMessagesConfig().getString("command.color.list.colors")));
                    StarColors.getCustomColors().forEach((code, color) -> sender.sendMessage(colors.colorLegacy("  &8- &b") + code + "§8: §b" + color.getHex() + colors.colorLegacy(" &8[&e" + color.getOwner().getName() + "&8]" + (!color.getPermission().isEmpty() ? " &8<&d" + color.getPermission() + "&8>" : ""))));
                }
            } else if (args[1].equalsIgnoreCase("add")) {
                if (!sender.hasPermission("starcore.admin.color.add")) {
                    colors.coloredLegacy(sender, plugin.getMessagesConfig().getString("command.nopermission"));
                    return true;
                }

                if (!(args.length > 3)) {
                    colors.coloredLegacy(sender, "&cUsage: /" + label + " " + args[0] + " " + args[1] + " <code> <hex> [permission]");
                    return true;
                }

                String code = args[2];

                if (ColorHandler.isSpigotColor(code)) {
                    colors.coloredLegacy(sender, plugin.getMessagesConfig().getString("command.color.add.cannot-override-spigot"));
                    return true;
                }

                if (!ColorHandler.isValidCode(code)) {
                    colors.coloredLegacy(sender, plugin.getMessagesConfig().getString("command.color.add.invalid-code"));
                    return true;
                }

                String hex = args[3];

                String permission = "";
                if (args.length > 4) {
                    permission = args[4];
                }

                if (!ColorHandler.isValidHex(hex)) {
                    colors.coloredLegacy(sender, "&cThe color value you provided is not a valid HEX code.");
                    colors.coloredLegacy(sender, "&c  HEX values must match ALL of the following");
                    sender.sendMessage(colors.colorLegacy("&c    - First Character must be a ") + "#");
                    sender.sendMessage(colors.colorLegacy("&c    - The length must be 3 or 6 characters not including the ") + "#");
                    colors.coloredLegacy(sender, "&c    - Characters must be 0-9 and a-f (case insensitive)");
                    return true;
                }

                StarColors.addCustomColor(new CustomColor(plugin).hexValue(hex).symbolCode(code).permission(permission));
                sender.sendMessage(ChatColor.YELLOW + "You added " + ChatColor.AQUA + code + " " + ChatColor.YELLOW + "with the HEX Code " + ChatColor.AQUA + hex + ChatColor.YELLOW + " as a custom color.");
            } else if (args[1].equalsIgnoreCase("remove")) {
                if (!sender.hasPermission("starcore.admin.color.remove")) {
                    colors.coloredLegacy(sender, plugin.getMessagesConfig().getString("command.nopermission"));
                    return true;
                }

                if (!(args.length > 2)) {
                    colors.coloredLegacy(sender, "&cUsage: /" + label + " " + args[0] + " " + args[1] + " <code>");
                    return true;
                }

                String code = args[2];
                if (StarColors.getCustomColor(code) == null) {
                    colors.coloredLegacy(sender, plugin.getMessagesConfig().getString("command.color.remove.not-registered"));
                    return true;
                }

                StarColors.removeColor(code);
                colors.coloredLegacy(sender, plugin.getMessagesConfig().getString("command.color.remove.success").replace("{OLDCODE}", code));
            } else {
                colors.coloredLegacy(sender, plugin.getMessagesConfig().getString("command.invalidsubcommand"));
            }
        } else {
            colors.coloredLegacy(sender, plugin.getMessagesConfig().getString("command.invalidsubcommand"));
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
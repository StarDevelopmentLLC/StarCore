package com.stardevllc.starcore.cmds;

import com.stardevllc.starcore.StarCore;
import com.stardevllc.starcore.color.ColorHandler;
import com.stardevllc.starcore.color.CustomColor;
import com.stardevllc.starcore.gui.handler.InventoryHandler;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.inventory.Inventory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class StarCoreCmd implements TabExecutor {

    private StarCore plugin;

    public StarCoreCmd(StarCore plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("starcore.admin")) {
            sender.sendMessage(ColorHandler.getInstance().color(plugin.getMainConfig().getString("messages.command.nopermission")));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ColorHandler.getInstance().color("&cUsage: /" + label + " <subcommand> <args>"));
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("starcore.admin.reload")) {
                ColorHandler.getInstance().coloredMessage(sender, plugin.getMainConfig().getString("messages.command.nopermission"));
                return true;
            }

            plugin.reload(false);
            sender.sendMessage(ColorHandler.getInstance().color("&aSuccessfully reloaded configs."));
        } else if (args[0].equalsIgnoreCase("color")) {
            if (!sender.hasPermission("starcore.admin.color")) {
                sender.sendMessage(ColorHandler.getInstance().color(plugin.getMainConfig().getString("messages.command.nopermission")));
                return true;
            }

            if (!(args.length > 1)) {
                sender.sendMessage(ColorHandler.getInstance().color("&cUsage: /" + label + " " + args[0] + " list <symbols|colors>"));
                sender.sendMessage(ColorHandler.getInstance().color("&cUsage: /" + label + " " + args[0] + " add <code> <hex> [permission]"));
                sender.sendMessage(ColorHandler.getInstance().color("&cUsage: /" + label + " " + args[0] + " remove <code>"));
                return true;
            }

            if (args[1].equalsIgnoreCase("list")) {
                if (!sender.hasPermission("starcore.admin.color.list")) {
                    ColorHandler.getInstance().coloredMessage(sender, plugin.getMainConfig().getString("messages.command.nopermission"));
                    return true;
                }

                if (!(args.length > 2)) {
                    ColorHandler.getInstance().coloredMessage(sender, "&cUsage: /" + label + " " + args[0] + " " + args[1] + " symbols");
                    ColorHandler.getInstance().coloredMessage(sender, "&cUsage: /" + label + " " + args[0] + " " + args[1] + " codes");
                    return true;
                }

                if (args[2].equalsIgnoreCase("symbols")) {
                    StringBuilder symbolBuilder = new StringBuilder();
                    for (Character c : ColorHandler.getInstance().getPrefixSymbols()) {
                        symbolBuilder.append(ColorHandler.getInstance().color("&b")).append(c).append(ColorHandler.getInstance().color("&e, "));
                    }
                    String charList = symbolBuilder.toString().trim();
                    charList = charList.substring(0, charList.length() - 2);
                    sender.sendMessage(ColorHandler.getInstance().color(plugin.getMainConfig().getString("messages.command.color.list.symbols.header")) + charList);
                } else if (args[2].equalsIgnoreCase("codes")) {
                    sender.sendMessage(ColorHandler.getInstance().color(plugin.getMainConfig().getString("messages.command.color.list.colors")));
                    ColorHandler.getInstance().getCustomColors().forEach((code, color) -> sender.sendMessage(ColorHandler.getInstance().color("  &8- &b") + code + "§8: §b" + color.getHex() + ColorHandler.getInstance().color(" &8[&e" + color.getOwner().getName() + "&8]" + (!color.getPermission().isEmpty() ? " &8<&d" + color.getPermission() + "&8>" : ""))));
                }
            } else if (args[1].equalsIgnoreCase("add")) {
                if (!sender.hasPermission("starcore.admin.color.add")) {
                    ColorHandler.getInstance().coloredMessage(sender, plugin.getMainConfig().getString("messages.command.nopermission"));
                    return true;
                }

                if (!(args.length > 3)) {
                    sender.sendMessage(ColorHandler.getInstance().color("&cUsage: /" + label + " " + args[0] + " " + args[1] + " <code> <hex> [permission]"));
                    return true;
                }

                String code = args[2];

                if (ColorHandler.isSpigotColor(code)) {
                    sender.sendMessage(ColorHandler.getInstance().color(plugin.getMainConfig().getString("messages.command.color.add.cannot-override-spigot")));
                    return true;
                }

                if (!ColorHandler.isValidCode(code)) {
                    sender.sendMessage(ColorHandler.getInstance().color(plugin.getMainConfig().getString("messages.command.color.add.invalid-code")));
                    return true;
                }

                String hex = args[3];

                String permission = "";
                if (args.length > 4) {
                    permission = args[4];
                }

                if (!ColorHandler.isValidHex(hex)) {
                    sender.sendMessage(ColorHandler.getInstance().color("&cThe color value you provided is not a valid HEX code."));
                    sender.sendMessage(ColorHandler.getInstance().color("&c  HEX values must match ALL of the following"));
                    sender.sendMessage(ColorHandler.getInstance().color("&c    - First Character must be a ") + "#");
                    sender.sendMessage(ColorHandler.getInstance().color("&c    - The length must be 3 or 6 characters not including the ") + "#");
                    sender.sendMessage(ColorHandler.getInstance().color("&c    - Characters must be 0-9 and a-f (case insensitive)"));
                    return true;
                }

                ColorHandler.getInstance().addCustomColor(new CustomColor(plugin).hexValue(hex).symbolCode(code).permission(permission));
                sender.sendMessage(ChatColor.YELLOW + "You added " + ChatColor.AQUA + code + " " + ChatColor.YELLOW + "with the HEX Code " + ChatColor.AQUA + hex + ChatColor.YELLOW + " as a custom color.");
            } else if (args[1].equalsIgnoreCase("remove")) {
                if (!sender.hasPermission("starcore.admin.color.remove")) {
                    ColorHandler.getInstance().coloredMessage(sender, plugin.getMainConfig().getString("messages.command.nopermission"));
                    return true;
                }

                if (!(args.length > 2)) {
                    sender.sendMessage(ColorHandler.getInstance().color("&cUsage: /" + label + " " + args[0] + " " + args[1] + " <code>"));
                    return true;
                }

                String code = args[2];
                if (ColorHandler.getInstance().getCustomColor(code) == null) {
                    sender.sendMessage(ColorHandler.getInstance().color(plugin.getMainConfig().getString("messages.command.color.remove.not-registered")));
                    return true;
                }

                ColorHandler.getInstance().removeColor(code);
                sender.sendMessage(ColorHandler.getInstance().color(plugin.getMainConfig().getString("messages.command.color.remove.success").replace("{OLDCODE}", code)));
            } else {
                sender.sendMessage(ColorHandler.getInstance().color(plugin.getMainConfig().getString("messages.command.invalidsubcommand")));
            }
        } else if (args[0].equalsIgnoreCase("gui")) {
            if (args[1].equalsIgnoreCase("list")) {
                if (!sender.hasPermission("starcore.admin.gui.list")) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to use that command.");
                    return true;
                }
                Map<Inventory, InventoryHandler> activeHandlers = plugin.getGuiManager().getActiveHandlers();
                sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + "Active GUIs Information");
                sender.sendMessage(ChatColor.YELLOW + " Total Active: " + ChatColor.AQUA + activeHandlers.size());
                activeHandlers.forEach((inv, handler) -> sender.sendMessage(ChatColor.YELLOW + "  Handler " + ChatColor.AQUA + handler.getClass().getSimpleName() + ChatColor.YELLOW + " with " + ChatColor.AQUA + inv.getViewers().size() + ChatColor.YELLOW + " total viewer(s)."));
            }
        } else {
            sender.sendMessage(ColorHandler.getInstance().color(plugin.getMainConfig().getString("messages.command.invalidsubcommand")));
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
                    sortAndFilter(args[2], new LinkedList<>(ColorHandler.getInstance().getCustomColors().keySet()));
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
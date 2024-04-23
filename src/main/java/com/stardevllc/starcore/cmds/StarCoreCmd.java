package com.stardevllc.starcore.cmds;

import com.stardevllc.starcore.StarCore;
import com.stardevllc.starcore.gui.handler.InventoryHandler;
import com.stardevllc.starcore.color.ColorUtils;
import com.stardevllc.starcore.color.CustomColor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;

import java.util.Map;

public class StarCoreCmd implements CommandExecutor {

    private StarCore plugin;

    public StarCoreCmd(StarCore plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("starcore.admin")) {
            sender.sendMessage(ColorUtils.color(plugin.getMainConfig().getString("messages.command.nopermission")));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ColorUtils.color("&cUsage: /" + label + " <subcommand> <args>"));
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("starcore.admin.reload")) {
                ColorUtils.coloredMessage(sender, plugin.getMainConfig().getString("messages.command.nopermission"));
                return true;
            }
            
            plugin.reload(false);
            sender.sendMessage(ColorUtils.color("&aSuccessfully reloaded configs."));
        } else if (args[0].equalsIgnoreCase("color")) {
            if (!sender.hasPermission("starcore.admin.color")) {
                sender.sendMessage(ColorUtils.color(plugin.getMainConfig().getString("messages.command.nopermission")));
                return true;
            }

            if (!(args.length > 1)) {
                sender.sendMessage(ColorUtils.color("&cUsage: /" + label + " " + args[0] + " list <symbols|colors>"));
                sender.sendMessage(ColorUtils.color("&cUsage: /" + label + " " + args[0] + " add <code> <hex> [permission]"));
                sender.sendMessage(ColorUtils.color("&cUsage: /" + label + " " + args[0] + " remove <code>"));
                return true;
            }

            if (args[1].equalsIgnoreCase("list")) {
                if (!sender.hasPermission("starcore.admin.color.list")) {
                    ColorUtils.coloredMessage(sender, plugin.getMainConfig().getString("messages.command.nopermission"));
                    return true;
                }
                
                if (!(args.length > 2)) {
                    ColorUtils.coloredMessage(sender, "&cUsage: /" + label + " " + args[0] + " " + args[1] + " symbols");
                    ColorUtils.coloredMessage(sender, "&cUsage: /" + label + " " + args[0] + " " + args[1] + " codes");
                    return true;
                }

                if (args[2].equalsIgnoreCase("symbols")) {
                    StringBuilder symbolBuilder = new StringBuilder();
                    for (Character c : ColorUtils.getPrefixSymbols()) {
                        symbolBuilder.append(ColorUtils.color("&b")).append(c).append(ColorUtils.color("&e, "));
                    }
                    String charList = symbolBuilder.toString().trim();
                    charList = charList.substring(0, charList.length() - 2);
                    sender.sendMessage(ColorUtils.color(plugin.getMainConfig().getString("messages.command.color.list.symbols.header")) + charList);
                } else if (args[2].equalsIgnoreCase("codes")) {
                    sender.sendMessage(ColorUtils.color(plugin.getMainConfig().getString("messages.command.color.list.colors")));
                    ColorUtils.getCustomColors().forEach((code, color) -> sender.sendMessage(ColorUtils.color("  &8- &b") + code + "§8: §b" + color.getHex() + ColorUtils.color(" &8[&e" + color.getOwner().getName() + "&8]" + (!color.getPermission().isEmpty() ? " &8<&d" + color.getPermission() + "&8>" : ""))));
                }
            } else if (args[1].equalsIgnoreCase("add")) {
                if (!sender.hasPermission("starcore.admin.color.add")) {
                    ColorUtils.coloredMessage(sender, plugin.getMainConfig().getString("messages.command.nopermission"));
                    return true;
                }
                
                if (!(args.length > 3)) {
                    sender.sendMessage(ColorUtils.color("&cUsage: /" + label + " " + args[0] + " " + args[1] + " <code> <hex> [permission]"));
                    return true;
                }

                String code = args[2];

                if (ColorUtils.isSpigotColor(code)) {
                    sender.sendMessage(ColorUtils.color(plugin.getMainConfig().getString("messages.command.color.add.cannot-override-spigot")));
                    return true;
                }

                if (!ColorUtils.isValidCode(code)) {
                    sender.sendMessage(ColorUtils.color(plugin.getMainConfig().getString("messages.command.color.add.invalid-code")));
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
                if (!sender.hasPermission("starcore.admin.color.remove")) {
                    ColorUtils.coloredMessage(sender, plugin.getMainConfig().getString("messages.command.nopermission"));
                    return true;
                }
                
                if (!(args.length > 2)) {
                    sender.sendMessage(ColorUtils.color("&cUsage: /" + label + " " + args[0] + " " + args[1] + " <code>"));
                    return true;
                }

                String code = args[2];
                if (ColorUtils.getCustomColor(code) == null) {
                    sender.sendMessage(ColorUtils.color(plugin.getMainConfig().getString("messages.command.color.remove.not-registered")));
                    return true;
                }

                ColorUtils.removeColor(code);
                sender.sendMessage(ColorUtils.color(plugin.getMainConfig().getString("messages.command.color.remove.success").replace("{OLDCODE}", code)));
            } else {
                sender.sendMessage(ColorUtils.color(plugin.getMainConfig().getString("messages.command.invalidsubcommand")));
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
            sender.sendMessage(ColorUtils.color(plugin.getMainConfig().getString("messages.command.invalidsubcommand")));
        }

        return true;
    }
}
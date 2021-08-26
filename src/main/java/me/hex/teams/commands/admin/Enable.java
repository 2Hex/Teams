package me.hex.teams.commands.admin;

import me.hex.teams.Teams;
import me.hex.teams.commands.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Enable extends BaseCommand {
    private final Teams plugin;

    public Enable(Teams plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("team")) return true;
        if (!sender.hasPermission("team.enable")) return true;
        if (args.length != 1) return true;
        if (!args[0].equalsIgnoreCase("enable")) return true;
        if (!plugin.getConfig().getBoolean("enable")) {
            plugin.getConfig().set("enable", true);
            plugin.saveConfig();
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eTeams have been &aENABLED &eby&6 " + sender.getName()));
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eIt's Already enabled!"));
        }
        return true;
    }
}

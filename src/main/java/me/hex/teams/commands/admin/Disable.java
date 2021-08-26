package me.hex.teams.commands.admin;

import me.hex.teams.Teams;
import me.hex.teams.commands.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Disable extends BaseCommand {
    private final Teams plugin;

    public Disable(Teams plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("team")) return true;
        if (!sender.hasPermission("team.disable")) return true;
        if (args.length != 1) return true;
        if (!args[0].equalsIgnoreCase("disable")) return true;
        if (plugin.getConfig().getBoolean("enable")) {
            plugin.getConfig().set("enable", false);
            plugin.saveConfig();
            leaders.clear();
            toggledChat.clear();
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eTeams have been &cDISABLED &eby &6" + sender.getName()));
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &cIt's Already disabled."));
        }
        return true;
    }
}

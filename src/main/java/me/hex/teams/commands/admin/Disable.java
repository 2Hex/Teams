package me.hex.teams.commands.admin;

import me.hex.teams.Teams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Disable implements CommandExecutor {
    private final Teams plugin;

    public Disable(Teams plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!command.getName().equalsIgnoreCase("team")) return true;
        if(!sender.hasPermission("team.disable")) return true;
        if (args.length != 1) return true;
        if (!args[0].equalsIgnoreCase("disable")) return true;
        plugin.getConfig().set("enable", false);
        Bukkit.broadcastMessage(ChatColor.YELLOW + "Hype" + ChatColor.GOLD + "Events >> " + ChatColor.GREEN + "Teams Disabled");
        return true;
    }
}

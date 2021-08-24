package me.hex.teams.commands.admin;

import me.hex.teams.Teams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Enable implements CommandExecutor {
    private final Teams plugin;

    public Enable(Teams plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!command.getName().equalsIgnoreCase("team")) return true;
        if(!sender.hasPermission("team.enable")) return true;
        if (args.length != 1) return true;
        if (!args[0].equalsIgnoreCase("enable")) return true;
        plugin.getConfig().set("enable", true);
        Bukkit.broadcastMessage(ChatColor.YELLOW + "Hype" + ChatColor.GOLD + "Events >> " + ChatColor.GREEN + "Teams Enabled");
        return true;
    }
}

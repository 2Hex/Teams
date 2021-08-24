package me.hex.teams.commands.admin;

import me.hex.teams.Teams;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Lock implements CommandExecutor {
    private final Teams plugin;

    public Lock(Teams plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!command.getName().equalsIgnoreCase("team")) return true;
        if(!sender.hasPermission("team.lock")) return true;
        if (args.length != 1) return true;
        if (!args[0].equalsIgnoreCase("lock")) return true;
        if (plugin.getConfig().getBoolean("enable")) {
            if (plugin.getConfig().getBoolean("lock")) {
                plugin.getConfig().set("lock", false);
                sender.sendMessage(ChatColor.GREEN + "UnLocked!");
            } else {
                plugin.getConfig().set("lock", true);
                sender.sendMessage(ChatColor.GREEN + "Locked!");
            }
            return true;
        }
        return true;
    }
}
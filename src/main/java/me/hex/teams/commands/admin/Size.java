package me.hex.teams.commands.admin;

import me.hex.teams.Teams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Size implements CommandExecutor {
    private final Teams plugin;

    public Size(Teams plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("team")) return true;
        if (!sender.hasPermission("team.size")) return true;
        if (args.length != 2) return true;
        if (!args[0].equalsIgnoreCase("size")) return true;
        if (plugin.getConfig().getBoolean("enable")) {
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(args[1]);
            if (m.find()) {
                plugin.getConfig().set("size", m.group());
                Bukkit.broadcastMessage(ChatColor.YELLOW + "Hype" + ChatColor.GOLD + "Events >> " + ChatColor.GREEN + "Teams Size Set to " + ChatColor.DARK_AQUA + m.group());
            }
            return true;
        }
        return true;
    }
}

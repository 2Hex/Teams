package me.hex.teams.commands.admin;

import me.hex.teams.Teams;
import me.hex.teams.commands.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class FriendlyFire extends BaseCommand {
    private final Teams plugin;

    public FriendlyFire(Teams plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("team")) return true;
        if (!sender.hasPermission("team.friendlyfire")) return true;
        if (args.length != 1) return true;
        if (!args[0].equalsIgnoreCase("friendlyfire")) return true;
        if(!plugin.getConfig().getBoolean("enable") || plugin.getConfig().getBoolean("lock")){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eTeams have been &cDISABLED / LOCKED"));
            return true;
        }
        if (!plugin.getConfig().getBoolean("teammates-can-hit-each-other")) {
            plugin.getConfig().set("teammates-can-hit-each-other", true);
            plugin.saveConfig();
            Bukkit.broadcastMessage(ChatColor.YELLOW + "Hype" + ChatColor.GOLD + "Events >> " + ChatColor.GREEN + "Teams Can Hit Each Other Now!");
        } else {
            plugin.getConfig().set("teammates-can-hit-each-other", false);
            plugin.saveConfig();
            Bukkit.broadcastMessage(ChatColor.YELLOW + "Hype" + ChatColor.GOLD + "Events >> " + ChatColor.GREEN + "Teams Can Not Hit Each Other Now!");
        }
        plugin.saveConfig();
        return true;
    }
}

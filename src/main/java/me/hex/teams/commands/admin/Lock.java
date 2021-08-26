package me.hex.teams.commands.admin;

import me.hex.teams.Teams;
import me.hex.teams.commands.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Lock extends BaseCommand {
    private final Teams plugin;

    public Lock(Teams plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("team")) return true;
        if (!sender.hasPermission("team.lock")) return true;
        if (args.length != 1) return true;
        if (!args[0].equalsIgnoreCase("lock")) return true;
        if(!plugin.getConfig().getBoolean("enable")){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eTeams have been &cDISABLED"));
            return true;
        }
        if (plugin.getConfig().getBoolean("lock")) {
            plugin.getConfig().set("lock", false);
            plugin.saveConfig();
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>>&e " + sender.getName() + " &ehas &aUNLOCKED &eteams! You can now create/invite players to your team."));
        } else {
            plugin.getConfig().set("lock", true);
            plugin.saveConfig();
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>>&e " + sender.getName() + " &ehas &cLOCKED &eteams! You can no longer create/invite players to your team."));
        }
        return true;
    }
}
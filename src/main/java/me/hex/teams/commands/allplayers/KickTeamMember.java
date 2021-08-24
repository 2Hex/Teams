package me.hex.teams.commands.allplayers;

import me.hex.teams.Teams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickTeamMember implements CommandExecutor {
    private final Teams plugin;

    public KickTeamMember(Teams plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("team")) return true;
        if (args.length != 2) return true;
        if (!args[0].equalsIgnoreCase("kick")) return true;
        if (plugin.getConfig().getBoolean("enable")) {
            if (!plugin.getConfig().getBoolean("kick")) {
                if (sender instanceof Player) {
                    for (String childSection : plugin.getConfig().getKeys(true)) {
                        if (Bukkit.getPlayer(args[1]) == null) return true;
                        Player target = Bukkit.getPlayer(args[1]);
                        if (plugin.getConfig().getStringList("Teams." + childSection).get(0).equalsIgnoreCase(sender.getName()))
                            plugin.getConfig().getStringList("Teams." + childSection).remove(target.getName());
                    }
                }
            }
        }
        return true;
    }
}



package me.hex.teams.commands.allplayers;

import me.hex.teams.Teams;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamList implements CommandExecutor {
    private final Teams plugin;

    public TeamList(Teams plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("team")) return true;
        if (args.length != 1) return true;
        if (!args[0].equalsIgnoreCase("list")) return true;
        if (plugin.getConfig().getBoolean("enable")) {
            if (!plugin.getConfig().getBoolean("lock")) {
                if(sender instanceof Player) {
                    for (String childSection : plugin.getConfig().getKeys(true)) {
                        if (plugin.getConfig().getStringList("Teams." + childSection).contains(sender.getName())) {
                            for (String element : plugin.getConfig().getStringList("Teams." + childSection)) {
                            sender.sendMessage(ChatColor.GREEN + element);
                            }
                            sender.sendMessage(ChatColor.RED + "Are your team members, first player is always the leader.");
                        }
                    }
                }

            }
        }
        return true;
    }
}


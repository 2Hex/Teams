package me.hex.teams.commands.allplayers;

import me.hex.teams.Teams;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Leave implements CommandExecutor {
    private final Teams plugin;

    public Leave(Teams plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("team")) return true;
        if (args.length != 1) return true;
        if (!args[0].equalsIgnoreCase("lock")) return true;
        if (plugin.getConfig().getBoolean("enable")) {
            if (!plugin.getConfig().getBoolean("lock")) {
                if(sender instanceof Player) {
                    for (String childSection : plugin.getConfig().getKeys(true)) {
                        plugin.getConfig().getStringList("Teams." + childSection).remove(sender.getName());
                    }
                }

            }
        }
        return true;
    }
}

package me.hex.teams.commands.allplayers;

import me.hex.teams.Teams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamChat implements CommandExecutor {
    private final Teams plugin;

    public TeamChat(Teams plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("team")) return true;
        if (args.length != 2) return true;
        if (!args[0].equalsIgnoreCase("chat")) return true;
        if (plugin.getConfig().getBoolean("enable")) {
            if (!plugin.getConfig().getBoolean("lock")) {
                if (sender instanceof Player) {
                    String message = args[1];
                    for (String childSection : plugin.getConfig().getKeys(true)) {
                        if (Bukkit.getPlayer(args[1]) == null) return true;
                        if (plugin.getConfig().getStringList("Teams." + childSection).contains(sender.getName()))
                            for(String playerName : plugin.getConfig().getStringList("Teams." + childSection)){
                                Player player = Bukkit.getPlayer(playerName);
                                player.sendMessage(ChatColor.YELLOW + "(" + message + ") " + ChatColor.GOLD + "From " + sender.getName());
                            }
                    }
                }
            }
        }
        return true;
    }
}

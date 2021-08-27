package me.hex.teams.commands.allplayers;

import me.hex.teams.Teams;
import me.hex.teams.commands.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class DisbandTeam extends BaseCommand {
    private final Teams plugin;

    public DisbandTeam(Teams plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("team")) return true;
        if (args.length != 1) return true;
        if (!args[0].equalsIgnoreCase("disband")) return true;
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(!plugin.getConfig().getBoolean("enable") || plugin.getConfig().getBoolean("lock")){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eTeams have been &cDISABLED / LOCKED"));
                return true;
            }

            if (isLeader(player.getUniqueId())) {
                for (UUID key : leaders.keySet()) {
                    List<UUID> list = getTeam(key);
                    if (!key.equals(player.getUniqueId())) continue;
                    for (UUID id : list) {
                        Player playerByID = Bukkit.getPlayer(id);
                        playerByID.sendMessage(ChatColor.translateAlternateColorCodes('&',  "&6" + player.getName() + " &eHas disbanded the team!"));
                    }
                }
                    leaders.remove(player.getUniqueId());
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>>") + ChatColor.GREEN + " You Disbanded The Team.");

            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>>") + ChatColor.RED + " You're not a team leader.");
            }
            return true;




        }
        return true;
    }
}




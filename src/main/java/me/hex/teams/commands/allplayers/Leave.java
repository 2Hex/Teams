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

public class Leave extends BaseCommand {
    private final Teams plugin;

    public Leave(Teams plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("team")) return true;
        if (args.length != 1) return true;
        if (!args[0].equalsIgnoreCase("leave")) return true;
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(!plugin.getConfig().getBoolean("enable") || plugin.getConfig().getBoolean("lock")){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eTeams have been &cDISABLED / LOCKED"));
                return true;
            }
            boolean isLeader = false;
            boolean inTeam = false;
            if(leaders.containsKey(player.getUniqueId()))
                isLeader = true;
            if(isLeader) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &cCan't leave, use /team disband instead."));
                return true;
            }
            for (UUID key : leaders.keySet()) {
                List<UUID> list = BaseCommand.leaders.get(key);
                if (!list.contains(player.getUniqueId())) continue;
                for (UUID id : list) {
                    Player playerByID = Bukkit.getPlayer(id);
                    playerByID.sendMessage(ChatColor.translateAlternateColorCodes('&',  "&6" + player.getName() + " &eHas Left the team!"));
                }
                    leaders.get(key).remove(player.getUniqueId());
                    inTeam = true;
            }
            if (inTeam) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eLeft!"));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eYou're not in any team"));
            }
            return true;
        }
        return true;
    }

}


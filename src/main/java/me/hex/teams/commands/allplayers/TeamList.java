package me.hex.teams.commands.allplayers;

import me.hex.teams.Teams;
import me.hex.teams.commands.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeamList extends BaseCommand {
    private final Teams plugin;

    public TeamList(Teams plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("team")) return true;
        if (args.length != 1) return true;
        if (!args[0].equalsIgnoreCase("list")) return true;
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(!plugin.getConfig().getBoolean("enable")){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eTeams have been &cDISABLED"));
                return true;
            }
            ArrayList<String> playersList = new ArrayList<>();
            UUID leaderID = UUID.randomUUID();
            for (UUID key : leaders.keySet()) {
                List<UUID> party = leaders.get(key);
                if (party.contains(player.getUniqueId())) {
                    for (UUID id : party) {
                        playersList.add(Bukkit.getPlayer(id).getName());
                        if (leaders.containsKey(id)) {
                            leaderID = id;
                        }
                    }
                }
            }
            if (playersList.isEmpty()) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &cThere are no teamed players with you."));
                return true;
            }
            if(Bukkit.getPlayer(leaderID).getName() == null){
                sender.sendMessage(ChatColor.RED + "You do not have a leader, if you do and you believe this is an error contact Domino#1234");
            return true;
            }
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eTeamed Players: " + playersList));

            if(!leaders.containsKey(Bukkit.getPlayer(leaderID).getUniqueId())){
                for(UUID k : leaders.get(Bukkit.getPlayer(leaderID).getUniqueId())){
                    if(leaders.containsKey(k))
                        leaderID = k;
                }
            }
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eLeader is: " + Bukkit.getPlayer(leaderID).getName()));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eTeamed Players Amount: " + playersList.size()));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eTeamed Players Max Amount: " + plugin.getConfig().getInt("size")));
        }
        return true;
    }
}



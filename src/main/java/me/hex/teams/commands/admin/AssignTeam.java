package me.hex.teams.commands.admin;

import me.hex.teams.Teams;
import me.hex.teams.commands.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class AssignTeam extends BaseCommand {
    private final Teams plugin;

    public AssignTeam(Teams plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("team")) return true;
        if (!sender.hasPermission("team.assign")) return true;
        if (args.length != 3) return true;
        if (!args[0].equalsIgnoreCase("assign")) return true;
        if (Bukkit.getPlayer(args[1]) == null || Bukkit.getPlayer(args[2]) == null) return true;
        Player assigned = Bukkit.getPlayer(args[1]);
        Player teamLeader = Bukkit.getPlayer(args[2]);
        Player player = (Player) sender;
        if (!plugin.getConfig().getBoolean("enable") || plugin.getConfig().getBoolean("lock")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eTeams have been &cDISABLED / LOCKED"));
            return true;
        }
        boolean alrinTeam = false;
        for (UUID key : leaders.keySet()) {
            List<UUID> list = BaseCommand.leaders.get(key);
            if (key == teamLeader.getUniqueId() && list.contains(assigned.getUniqueId()))
            alrinTeam = true;
            if (assigned.getUniqueId() == key && list.contains(teamLeader.getUniqueId()))
                alrinTeam = true;

        }
        if(alrinTeam){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &cPlayer already in " + teamLeader.getName() + "'s team!"));
        return true;
        }
        if (leaders.containsKey(teamLeader.getUniqueId())) {
            for (UUID key : leaders.keySet()) {
            leaders.get(key).remove(assigned.getUniqueId());
            if(key == assigned.getUniqueId())
                leaders.remove(key);
        }

            leaders.get(player.getUniqueId()).add(assigned.getUniqueId());
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eDone!"));
            assigned.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&lHype&e&lEvents&8>> &eYou have been assigned to &6" + teamLeader.getName() + "'s &eTeam!"));
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &c" + teamLeader.getName() + " is not a team leader"));
        }
        return true;
    }
}
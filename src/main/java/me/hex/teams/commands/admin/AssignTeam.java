package me.hex.teams.commands.admin;

import me.hex.teams.Teams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AssignTeam implements CommandExecutor {
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
        if (plugin.getConfig().getBoolean("enable") && !plugin.getConfig().getBoolean("lock")) {
            if (Bukkit.getPlayer(args[1]) == null || Bukkit.getPlayer(args[2]) == null) return true;
            Player assigned = Bukkit.getPlayer(args[1]);
            Player teamLeader = Bukkit.getPlayer(args[2]);
            for (String childSection : plugin.getConfig().getKeys(true)) {
                plugin.getConfig().getStringList("Teams." + childSection).remove(assigned.getName());
            }
            if (plugin.getConfig().isConfigurationSection("Teams." + teamLeader.getName())) {
                plugin.getConfig().getStringList("Teams." + teamLeader.getName()).add(assigned.getName());
                sender.sendMessage(ChatColor.GREEN + "DONE");
            } else {
                List<String> list = new ArrayList<>();
                list.add(assigned.getName());
                plugin.getConfig().set(("Teams." + teamLeader.getName()), list);
                sender.sendMessage(ChatColor.GREEN + "DONE");
            }

        }
        return true;
    }
}


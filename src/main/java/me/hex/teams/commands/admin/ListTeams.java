package me.hex.teams.commands.admin;

import me.hex.teams.Teams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListTeams implements CommandExecutor {
    private final Teams plugin;

    public ListTeams(Teams plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = new ArrayList<>(plugin.getConfig().getKeys(true));
        if (!command.getName().equalsIgnoreCase("team")) return true;
        if (!sender.hasPermission("team.list")) return true;
        if (args.length != 2) return true;
        if (!args[0].equalsIgnoreCase("listteams")) return true;
        if (args[1].equalsIgnoreCase("teamed")) {
            sender.sendMessage(ChatColor.YELLOW + "Hype" + ChatColor.GOLD + "Events >> " + ChatColor.GREEN + "Players in Teams:");
            sender.sendMessage(ChatColor.GOLD + Arrays.toString(list.toArray()));
            return true;
        }
        if (args[1].equalsIgnoreCase("unteamed")) {
            List<String> list1 = new ArrayList<>();
            for(Player p : Bukkit.getOnlinePlayers()){
                list1.add(p.getName());
            }
            list1.removeAll(list);
            sender.sendMessage(ChatColor.YELLOW + "Hype" + ChatColor.GOLD + "Events >> " + ChatColor.GREEN + "Players That Are NOT in Teams:");
            sender.sendMessage(ChatColor.GOLD + Arrays.toString(list1.toArray()));
            return true;
        }
        return true;
    }
}

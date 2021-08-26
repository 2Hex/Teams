package me.hex.teams.commands.admin;

import me.hex.teams.Teams;
import me.hex.teams.commands.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ListTeams extends BaseCommand {
    private final Teams plugin;

    public ListTeams(Teams plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("team")) return true;
        if (!args[0].equalsIgnoreCase("listteams")) return true;

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!plugin.getConfig().getBoolean("enable") || plugin.getConfig().getBoolean("lock")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eTeams have been &cDISABLED / LOCKED"));
                return true;
            }
            if (args.length == 2) {
                if (Bukkit.getPlayer(args[1]) == null) {
                    player.sendMessage(ChatColor.RED + "No Player Matching" + args[1]);
                    return true;
                }
                Player target = Bukkit.getPlayer(args[1]);
                for (UUID key : leaders.keySet()) {
                    if (leaders.get(key).contains(target.getUniqueId())) {
                        String msg = Arrays.toString(leaders.get(key).toArray());
                        player.sendMessage(ChatColor.GREEN + msg);
                    }
                }
                return true;
            }
            if (args.length == 1) {
                ArrayList<String> playersList = new ArrayList<>();
                for (UUID key : leaders.keySet()) {
                    List<UUID> partyList = leaders.get(key);
                    for (UUID id : partyList) {
                        playersList.add(Bukkit.getPlayer(id).getName());
                    }
                }

                if (playersList.isEmpty()) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eThere are currently no teamed players."));
                    return true;
                }
                ArrayList<String> unTeamed = new ArrayList<>();
                for(Player playeronline : Bukkit.getOnlinePlayers()){
                    if(!playersList.contains(playeronline.getName())){
                        unTeamed.add(playeronline.getName());
                    }
                }
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &6&lTeamed players:"));
                int i = 0;
                ArrayList<String> members = new ArrayList<>();
                for(UUID leader : leaders.keySet()) {
                    for(UUID guyUUID : leaders.get(leader)){
                        Player player1 = Bukkit.getPlayer(guyUUID);
                        String playerName = player1.getName();
                        members.add(playerName);
                    }
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &7[&e" + i + "&7] " + ChatColor.GREEN + Arrays.toString(members.toArray())));
                        i++;
                }
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &6&lUnTeamed players:"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &7[&e" + "0" + "&7] ") + ChatColor.RED + Arrays.toString(unTeamed.toArray()));
                members.clear();
            }
        }

        return true;
    }

}


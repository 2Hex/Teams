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

public class KickTeamMember extends BaseCommand {
    private final Teams plugin;

    public KickTeamMember(Teams plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("team")) return true;
        if (args.length != 2) return true;
        if (!args[0].equalsIgnoreCase("kick")) return true;
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if(!plugin.getConfig().getBoolean("enable") || plugin.getConfig().getBoolean("lock")){
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eTeams have been &cDISABLED / LOCKED"));
                        return true;
                    }
                    if (Bukkit.getPlayer(args[1]) == null) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eNo Player Matching " + args[1]));
                        return true;
                    }
                    Player target = Bukkit.getPlayer(args[1]);
                    if(target.getUniqueId().equals(player.getUniqueId())){
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &cYou cannot kick your self. (Duh)"));
                        return true;
                    }
                    if (leaders.containsKey(player.getUniqueId())) {
                        for (UUID key : leaders.keySet()) {
                            List<UUID> list = BaseCommand.leaders.get(key);
                            if (!key.equals(player.getUniqueId())) continue;
                            for (UUID id : list) {
                                Player playerByID = Bukkit.getPlayer(id);
                                playerByID.sendMessage(ChatColor.translateAlternateColorCodes('&',  "&6" + player.getName() + " &eHas Kicked " + target.getName()));
                            }
                        }
                        leaders.remove(player.getUniqueId());
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>>") + ChatColor.GREEN + " You Disbanded The Team.");

                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>>") + ChatColor.RED + " You're not a team leader.");
                    }
                    if (leaders.containsKey(player.getUniqueId())) {
                        List<UUID> members = leaders.get(player.getUniqueId());
                        if(!members.contains(target.getUniqueId())){
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &cYou cannot kick non-teammates. (Duh)"));
                        return true;
                        }
                        members.remove(target.getUniqueId());
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &cKicked " + target.getName()));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &cYou're not the team leader"));
                    }
                    return true;




        }
        return true;
    }
}



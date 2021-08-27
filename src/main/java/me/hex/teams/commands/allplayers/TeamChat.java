package me.hex.teams.commands.allplayers;

import me.hex.teams.Teams;
import me.hex.teams.commands.BaseCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TeamChat extends BaseCommand {
    private final Teams plugin;

    public TeamChat(Teams plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("team")) return true;
        if(!plugin.getConfig().getBoolean("enable") || plugin.getConfig().getBoolean("lock")){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eTeams have been &cDISABLED / LOCKED"));
            return true;
        }
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        boolean inTeam = false;
        for (UUID key : leaders.keySet()) {
            if (getTeam(key).contains(player.getUniqueId()))
                inTeam = true;
        }
        if(!inTeam){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &cYou're not in a team!"));
            return true;
        }
        if (args.length == 1) {
            if (!args[0].equalsIgnoreCase("chat")) return true;
            if(toggledChat.contains(player.getUniqueId())) {
                toggledChat.remove(player.getUniqueId());
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eToggled off team chat"));
            } else {
                toggledChat.add(player.getUniqueId());
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eToggled on team chat"));
            }
        }
        if (args.length >= 2) {
            if (!args[0].equalsIgnoreCase("chat")) return true;
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < args.length; i++) {
                if (i > 0) sb.append(" ");
                sb.append(args[i]);
            }
            String result = sb.toString().replaceFirst("@", "");
            player.chat("@" + result);
            return true;
        }
        return true;
    }
}

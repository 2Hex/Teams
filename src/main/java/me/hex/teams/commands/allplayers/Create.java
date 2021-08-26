package me.hex.teams.commands.allplayers;

import me.hex.teams.Teams;
import me.hex.teams.commands.BaseCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class Create extends BaseCommand {
    private final Teams plugin;

    public Create(Teams plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            if (args.length != 1) return true;
            if(!args[0].equalsIgnoreCase("create")) return true;
            Player player = (Player) sender;
            if(!plugin.getConfig().getBoolean("enable") || plugin.getConfig().getBoolean("lock")){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eTeams have been &cDISABLED / LOCKED"));
                return true;
            }
            if (!leaders.containsKey(player.getUniqueId())) {
                ArrayList<UUID> list = new ArrayList<>();
                list.add(player.getUniqueId());
                leaders.put(player.getUniqueId(), list);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eCreated team"));
                return true;
            }
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eCannot Make Team, Leave your current one first."));
        }
        return true;
    }
}

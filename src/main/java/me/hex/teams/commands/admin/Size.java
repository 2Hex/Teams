package me.hex.teams.commands.admin;

import me.hex.teams.Teams;
import me.hex.teams.commands.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Size extends BaseCommand {
    private final Teams plugin;

    public Size(Teams plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("team")) return true;
        if (!sender.hasPermission("team.size")) return true;
        if (args.length != 2) return true;
        if (!args[0].equalsIgnoreCase("size")) return true;
        if(!plugin.getConfig().getBoolean("enable") || plugin.getConfig().getBoolean("lock")){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eTeams have been &cDISABLED"));
            return true;
        }
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(args[1]);
            if (m.find()) {
                if (Integer.parseInt(m.group()) != plugin.getConfig().getInt("size")) {
                    if(Integer.parseInt(m.group()) == 1){
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eSize cannot be 1"));
                        return true;
                    }
                    plugin.getConfig().set("size", Integer.valueOf(m.group()));
                    plugin.saveConfig();
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eTeams Size Set to " + m.group()));
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eSize is already set to " + Integer.valueOf(m.group())));

                }
            }
            return true;
    }
}

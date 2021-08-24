package me.hex.teams.commands.allplayers;

import me.hex.teams.Teams;
import me.hex.teams.events.ChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Invite implements CommandExecutor {
    private final Teams plugin;
    private final ChatEvent chat;

    public Invite(Teams plugin, ChatEvent chat) {
        this.plugin = plugin;
        this.chat = chat;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (!sender.hasPermission("team.invite")) return true;
            if (args.length != 2) return true;
            if(Bukkit.getPlayer(args[1]) == null) return true;
            Player target = Bukkit.getPlayer(args[1]);
            if(!args[0].equalsIgnoreCase("invite")) return true;
            target.sendMessage(ChatColor.GREEN + "Do you want to accept the team invite? say accept/deny.");
            chat.requestChatInput(target.getUniqueId(), 600, input -> {
            if(input.equalsIgnoreCase("accept")){
                List<String> list = new ArrayList<>();
                list.add(sender.getName());
                list.add(target.getName());
                plugin.getConfig().set("Teams." + sender.getName(), list);
                target.sendMessage(ChatColor.GREEN + "Accepted.");
            }
            if(input.equalsIgnoreCase("deny")){
                target.sendMessage(ChatColor.GREEN + "Refused.");
            }
            });
        }
        return true;
    }
}

package me.hex.teams.commands.allplayers;

import me.hex.teams.Teams;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Create implements CommandExecutor {
    private final Teams plugin;

    public Create(Teams plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            if (!sender.hasPermission("team.create")) return true;
            if (args.length != 1) return true;
            if(!args[0].equalsIgnoreCase("create")) return true;
            Player player = (Player) sender;
            for (String childSection : plugin.getConfig().getKeys(true)) {
                plugin.getConfig().getStringList("Teams." + childSection).remove(player.getName());
            }
            List<String> list = new ArrayList<>();
            list.add(player.getName());
            plugin.getConfig().set("Teams." + player.getName(), list);
        }
        return true;
    }
}

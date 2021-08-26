package me.hex.teams;

import me.hex.teams.commands.ChatEvent;
import me.hex.teams.commands.OnHit;
import me.hex.teams.commands.TeamNumberExpansion;
import me.hex.teams.commands.admin.*;
import me.hex.teams.commands.allplayers.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;


public class Teams extends JavaPlugin{
    Map<String, CommandExecutor> commands = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        ChatEvent chat = new ChatEvent(this);
        getServer().getPluginManager().registerEvents(chat, this);
        getServer().getPluginManager().registerEvents(new OnHit(this), this);
        getCommand("team").setExecutor(this);
        commands.put("create", new Create(this));
        commands.put("invite", new Invite(this, chat));
        commands.put("kick", new KickTeamMember(this));
        commands.put("leave", new Leave(this));
        commands.put("chat", new TeamChat(this));
        commands.put("list", new TeamList(this));
        commands.put("size", new Size(this));
        commands.put("lock", new Lock(this));
        commands.put("listteams", new ListTeams(this));
        commands.put("enable", new Enable(this));
        commands.put("disable", new Disable(this));
        commands.put("assign", new AssignTeam(this));
        commands.put("friendlyfire", new FriendlyFire(this));
        commands.put("help", new Help());
        commands.put("disband", new DisbandTeam(this));
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new TeamNumberExpansion(this).register();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            if(sender != null)
            Bukkit.dispatchCommand(sender, "team help");
            return false;
        }
        CommandExecutor subCommand = commands.get(args[0].toLowerCase());
        if (subCommand == null) return false;
        return subCommand.onCommand(sender, command, label, args);
    }
}

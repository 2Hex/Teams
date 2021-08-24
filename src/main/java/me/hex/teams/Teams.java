package me.hex.teams;

import me.hex.teams.events.ChatEvent;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import me.hex.teams.commands.admin.*;
import me.hex.teams.commands.allplayers.*;


public final class Teams extends JavaPlugin {

    @Override
    public void onEnable() {
        ChatEvent chat = new ChatEvent(this);
        getCommand("team").setExecutor(new AssignTeam(this));
        getCommand("team").setExecutor(new Disable(this));
        getCommand("team").setExecutor(new Enable(this));
        getCommand("team").setExecutor(new ListTeams(this));
        getCommand("team").setExecutor(new Lock(this));
        getCommand("team").setExecutor(new Size(this));
        getCommand("team").setExecutor(new Create(this));
        getCommand("team").setExecutor(new Invite(this, chat));
        getCommand("team").setExecutor(new KickTeamMember(this));
        getCommand("team").setExecutor(new Leave(this));
        getCommand("team").setExecutor(new TeamChat(this));
        getCommand("team").setExecutor(new TeamList(this));
        getServer().getPluginManager().registerEvents(chat, this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

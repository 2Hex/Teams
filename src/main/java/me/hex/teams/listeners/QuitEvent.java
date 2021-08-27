package me.hex.teams.listeners;

import me.hex.teams.commands.BaseCommand;
import me.hex.teams.listeners.ChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.UUID;

public class QuitEvent implements Listener {
    private final ChatEvent chat;
    public QuitEvent(ChatEvent chat){
        this.chat = chat;
    }
    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        chat.waitingInputMap.remove(event.getPlayer().getUniqueId());

        for (UUID key : BaseCommand.getLeaders().keySet()) {
            List<UUID> list = BaseCommand.getTeam(key);
            if (!list.contains(event.getPlayer().getUniqueId())) continue;
            for (UUID id : list) {
                Player playerByID = Bukkit.getPlayer(id);
                if (!BaseCommand.isLeader(id)) {
                    if(!playerByID.isOnline()) return;
                    playerByID.sendMessage(ChatColor.RED + "Team Member " + ChatColor.YELLOW + event.getPlayer().getName() + ChatColor.DARK_RED + " Disconnected.");
                } else {
                    playerByID.sendMessage(ChatColor.RED + "Team Leader " + ChatColor.YELLOW + event.getPlayer().getName() + ChatColor.DARK_RED + " Disconnected.");
                    BaseCommand.getLeaders().remove(id);
                }
            }
        }
    }
}

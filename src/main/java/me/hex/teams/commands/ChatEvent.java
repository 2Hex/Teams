package me.hex.teams.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.function.Consumer;

public class ChatEvent implements Listener {

    private final JavaPlugin plugin;
    public final Map<UUID, Consumer<String>> waitingInputMap = new HashMap<>();

    public ChatEvent(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void requestChatInput(final UUID playerID, final int timeout, final Consumer<String> consumer) {
        this.waitingInputMap.put(playerID, consumer);
        Bukkit.getScheduler().runTaskLater(this.plugin, () -> this.timeOutMessage(playerID), timeout);
    }

    private void timeOutMessage(final UUID playerID) {
        final Consumer<String> consumer = this.waitingInputMap.remove(playerID);
        if (consumer != null) {
            consumer.accept(null);
        }
    }

    @EventHandler
    public void onChat(final AsyncPlayerChatEvent event) {
        final String message = event.getMessage();
        if (this.waitingInputMap.containsKey(event.getPlayer().getUniqueId()))
            event.setCancelled(true);
        Optional.ofNullable(this.waitingInputMap.remove(event.getPlayer().getUniqueId())).ifPresent(consumer -> consumer.accept(message));
        Player player = event.getPlayer();
        if (message.startsWith("@") || BaseCommand.toggledChat.contains(player.getUniqueId())) {
            if (!plugin.getConfig().getBoolean("enable")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eTeams are &cDISABLED!"));
                return;
            }

            String deliveryMessage = ChatColor.translateAlternateColorCodes('&', "&6[TM] " + player.getName() + "&7: &r" + message);
            String resultMessage = deliveryMessage;
            if(message.startsWith("@")) {
                resultMessage = deliveryMessage.replaceFirst("@", "");
            }
            boolean inTeam = false;
            for (UUID key : BaseCommand.leaders.keySet()) {
                if (BaseCommand.leaders.get(key).contains(player.getUniqueId())) {
                    inTeam = true;
                    for (UUID personID : BaseCommand.leaders.get(key)) {
                        Player actualPerson = Bukkit.getPlayer(personID);
                        actualPerson.sendMessage(resultMessage);
                    }
                }
            }
            if (!inTeam)
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &cYou're not in a team"));

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        this.waitingInputMap.remove(event.getPlayer().getUniqueId());

        for (UUID key : BaseCommand.leaders.keySet()) {
            List<UUID> list = BaseCommand.leaders.get(key);
            if (!list.contains(event.getPlayer().getUniqueId())) continue;
            for (UUID id : list) {
                Player playerByID = Bukkit.getPlayer(id);
                if (!BaseCommand.leaders.containsKey(id)) {
                    if(!playerByID.isOnline()) return;
                    playerByID.sendMessage(ChatColor.RED + "Team Member " + ChatColor.YELLOW + event.getPlayer().getName() + ChatColor.DARK_RED + " Disconnected.");
                } else {
                    playerByID.sendMessage(ChatColor.RED + "Team Leader " + ChatColor.YELLOW + event.getPlayer().getName() + ChatColor.DARK_RED + " Disconnected.");
                    BaseCommand.leaders.remove(id);
                }
            }
        }
    }
}

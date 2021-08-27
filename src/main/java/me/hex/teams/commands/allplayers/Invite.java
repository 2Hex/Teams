package me.hex.teams.commands.allplayers;

import me.hex.teams.Teams;
import me.hex.teams.commands.BaseCommand;
import me.hex.teams.commands.ChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class Invite extends BaseCommand {
    private final Teams plugin;
    private final ChatEvent chat;

    public Invite(Teams plugin, ChatEvent chat) {
        this.plugin = plugin;
        this.chat = chat;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length != 2) return true;
            if(Bukkit.getPlayer(args[1]) == null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eNo Player Matching " + args[1]));
                return true;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if(!args[0].equalsIgnoreCase("invite")) return true;
            if(!plugin.getConfig().getBoolean("enable") || plugin.getConfig().getBoolean("lock")){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eTeams have been &cDISABLED / LOCKED"));
                return true;
            }
            if(target.getUniqueId().equals(player.getUniqueId())){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &cYou cannot invite your self. (Duh)"));
                return true;
            }
            List<UUID> members = leaders.get(player.getUniqueId());
            if(plugin.getConfig().getInt("size") == members.size()){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &cYour team is full!"));
                return true;
            }
            boolean senderInTeam = false;
            for (UUID key : leaders.keySet()) {
                if (leaders.get(key).contains(player.getUniqueId())) {
                    senderInTeam = true;
                }
                if (senderInTeam && !leaders.containsKey(player.getUniqueId())) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &cYou're not the team leader."));
                    return true;
                }
            }
            if(!senderInTeam) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &cYou're not in a team."));
                return true;
            }
            boolean inTeam = false;
            for (UUID key : leaders.keySet()) {
                if (leaders.get(key).contains(target.getUniqueId()))
                    inTeam = true;
            }
            if(inTeam){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &cPlayer is in a team, tell him leave it first."));
                return true;
            }
            if(!chat.waitingInputMap.containsKey(target.getUniqueId())) {
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eYou have been invited to a Team By " + sender.getName() + " Answer with 'Accept' Or 'deny' "));
                if (leaders.containsKey(player.getUniqueId())) {
                    for (UUID key : leaders.keySet()) {
                        List<UUID> list = BaseCommand.leaders.get(key);
                        if (!key.equals(player.getUniqueId())) continue;
                        for (UUID id : list) {
                            Player playerByID = Bukkit.getPlayer(id);
                            playerByID.sendMessage(ChatColor.translateAlternateColorCodes('&',  "&6&lHype&e&lEvents&8>> &6" + target.getName() + " Has Been Invited To The Team! By " + player.getName()));
                        }
                    }

                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &cYou don't have a team, /team create."));
                    return true;
                }

                chat.requestChatInput(target.getUniqueId(), 1200, input -> {
                    if (input.equalsIgnoreCase("accept")) {

                        if (leaders.containsKey(player.getUniqueId())) {
                            members.add(target.getUniqueId());
                            if(members.size() > plugin.getConfig().getInt("size")){
                                members.remove(target.getUniqueId());
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &cYour team is full!"));
                                return;
                            }
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &cYou're not the team leader"));
                        }

                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eAccepted " + sender.getName()));
                        if (leaders.containsKey(player.getUniqueId())) {
                            for (UUID key : leaders.keySet()) {
                                List<UUID> list = BaseCommand.leaders.get(key);
                                if (!key.equals(player.getUniqueId())) continue;
                                for (UUID id : list) {
                                    Player playerByID = Bukkit.getPlayer(id);
                                    playerByID.sendMessage(ChatColor.translateAlternateColorCodes('&',  "&6&lHype&e&lEvents&8>> &6" + target.getName() + " Has joined the team!"));
                                }
                            }

                        }
                    }
                    if (input.equalsIgnoreCase("deny")) {
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &eDenied " + sender.getName()));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> " + target.getName() + " &eDenied"));
                    }
                });
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lHype&e&lEvents&8>> &ePlayer already was invited to a team, wait for them to respond."));
            }
        }
        return true;
    }
}

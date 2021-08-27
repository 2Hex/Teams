package me.hex.teams.commands.allplayers;

import me.hex.teams.commands.BaseCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Help extends BaseCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("team")) return true;
        if (!(args.length > 0)) return true;
        if (!(args[0].equalsIgnoreCase("help"))) return true;
            if(args.length == 1) {
                sendPlayerHelp(sender);
                return true;
            }
            if(args.length == 2) {
                if (args[1].equalsIgnoreCase("admin"))
                    sendAdminHelp(sender);
                return true;
            }

        return true;
    }
    private void sendMessageToPlayer(CommandSender sender, String message){
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
    private void sendAdminHelp(CommandSender sender){
        sendMessageToPlayer(sender, "- &6&lHype&e&lEvents&8>> &6&lTeam Commands &7- &ePage Two");
        sendMessageToPlayer(sender, "&e/team assign <PLAYER> <TEAM_LEADER>");
        sendMessageToPlayer(sender, "&e/team disable");
        sendMessageToPlayer(sender, "&e/team enable");
        sendMessageToPlayer(sender, "&e/team friendlyfire");
        sendMessageToPlayer(sender, "&e/team listteams");
        sendMessageToPlayer(sender, "&e/team lock");
        sendMessageToPlayer(sender, "&e/team size <NUMBER>");
    }
    private void sendPlayerHelp(CommandSender sender){
        sendMessageToPlayer(sender, "- &6&lHype&e&lEvents&8>> &6&lTeam Commands &7- &ePage One");
        sendMessageToPlayer(sender, "&e/team create");
        sendMessageToPlayer(sender, "&e/team kick <PLAYER>");
        sendMessageToPlayer(sender, "&e/team invite <PLAYER>");
        sendMessageToPlayer(sender, "&e/team leave");
        sendMessageToPlayer(sender, "&e/team chat <MESSAGE> OR /team chat (toggle)");
        sendMessageToPlayer(sender, "&e/team list");
    }
}

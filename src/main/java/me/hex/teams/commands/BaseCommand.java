package me.hex.teams.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public abstract class BaseCommand implements CommandExecutor {
    protected static JavaPlugin plugin;
    protected static LinkedHashMap<UUID, List<UUID>> leaders = new LinkedHashMap<>();
    protected static List<UUID> toggledChat = new ArrayList<>();

    public static List<UUID> getToggledChat() {
        return toggledChat;
    }

    public static LinkedHashMap<UUID, List<UUID>> getLeaders() {
        return leaders;
    }

    public static boolean isMember(UUID uuid) {
        for (UUID key : leaders.keySet()) {
            List<UUID> list = leaders.get(key);
            if (list.contains(uuid)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isLeader(UUID uuid) {
        return leaders.containsKey(uuid);
    }

    public static List<UUID> getTeam(UUID uuid) {
        return leaders.get(uuid);
    }

    public static String getTeamIndex(UUID leader) {

        Set<UUID> keySet = BaseCommand.getLeaders().keySet();
        ArrayList<UUID> list = new ArrayList<>(keySet);

        return String.valueOf(list.indexOf(leader));
    }
}

package me.hex.teams.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public abstract class BaseCommand implements CommandExecutor {
    protected static JavaPlugin plugin;
    protected static LinkedHashMap<UUID, List<UUID>> leaders = new LinkedHashMap<>();
    protected static List<UUID> toggledChat = new ArrayList<>();

}

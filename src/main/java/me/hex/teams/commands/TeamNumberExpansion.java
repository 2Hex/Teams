package me.hex.teams.commands;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.hex.teams.Teams;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TeamNumberExpansion extends PlaceholderExpansion {

    private final Teams plugin;
    private final getTeam utils;

    public TeamNumberExpansion(Teams plugin, getTeam utils) {
        this.utils = utils;
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getAuthor() {
        return "hex";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "teamindex";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }


    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        // Compute and return your placeholder here

        for (UUID key : BaseCommand.leaders.keySet()) {
            if (BaseCommand.leaders.get(key).contains(player.getUniqueId())) {
                return ChatColor.translateAlternateColorCodes('&', "&7[&e" + utils.getTeamIndex(key) + "&7]");
            }
        }
        if (plugin.getConfig().getBoolean("enable")) {
            return ChatColor.translateAlternateColorCodes('&', "&7[&cNONE&7]");
        }
        return " ";
    }
}

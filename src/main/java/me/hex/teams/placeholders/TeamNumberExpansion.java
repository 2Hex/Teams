package me.hex.teams.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.hex.teams.Teams;
import me.hex.teams.commands.BaseCommand;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TeamNumberExpansion extends PlaceholderExpansion {

    private final Teams plugin;

    public TeamNumberExpansion(Teams plugin) {
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

        for (UUID key : BaseCommand.getLeaders().keySet()) {
            if (BaseCommand.getTeam(key).contains(player.getUniqueId())) {
                return ChatColor.translateAlternateColorCodes('&', "&7[&e" + BaseCommand.getTeamIndex(key) + "&7]");
            }
        }
        if (plugin.getConfig().getBoolean("enable")) {
            return ChatColor.translateAlternateColorCodes('&', "&7[&cNONE&7]");
        }
        return " ";
    }
}

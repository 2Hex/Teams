package me.hex.teams.listeners;

import me.hex.teams.Teams;
import me.hex.teams.commands.BaseCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.UUID;

public class OnHit implements Listener {
/* idk why but ill comment this class.
    doing Dependency injection cuz yes
    */
private final Teams plugin;

    public OnHit(Teams plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        //checking if attacker and attacked are both players
        if(plugin.getConfig().getBoolean("teammates-can-hit-each-other")) return;
        if (!(event.getDamager() instanceof Player)) return;
        if (!(event.getEntity() instanceof Player)) return;
        Player attacker = (Player) event.getDamager();
        Player attacked = (Player) event.getEntity();
        boolean inTeam = false;
        /*
        looping through teams to  check if they're in a team together
        and setting the boolean value to true if found
         */
        for (UUID key : BaseCommand.getLeaders().keySet()) {
            if (BaseCommand.getTeam(key).contains(attacked.getUniqueId()) &&
                    BaseCommand.getTeam(key).contains(attacker.getUniqueId()))
                inTeam = true;
        }
        //Checking if the for loop found the info we wanted
        if(!inTeam) return;
        // Cancelling the event
        event.setCancelled(true);
    }
}

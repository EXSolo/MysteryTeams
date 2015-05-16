package com.exsoloscript.mysteryteams.module;

import com.exsoloscript.mysteryteams.team.MysteryTeam;
import com.exsoloscript.mysteryteams.team.MysteryTeamManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.exsoloscript.mysteryteams.MysteryTeamsPlugin.prefix;

public abstract class TeamBasedModule extends GameModule {

    private List<UUID> missingPlayers;
    private MysteryTeamManager teamManager;

    public TeamBasedModule(String moduleName) {
        super(moduleName);
        this.missingPlayers = new ArrayList<>();
        this.teamManager = new MysteryTeamManager();
    }

    public abstract ItemStack getItem(MysteryTeam t);

    public List<UUID> getMissingPlayers() {
        return missingPlayers;
    }

    public MysteryTeamManager getTeamManager() {
        return teamManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (this.missingPlayers.contains(event.getPlayer().getUniqueId())) {
            Player p = event.getPlayer();
            MysteryTeam t = this.teamManager.getByPlayer(event.getPlayer());

            ItemStack item = getItem(t);

            if (t != null) {
                if (p.getInventory().firstEmpty() > -1)
                    p.getInventory().addItem(item);
                else {
                    p.getWorld().dropItem(p.getLocation(), item);
                    p.sendMessage(prefix() + "Your item was dropped on the ground since your inventory is full!");
                }

                this.missingPlayers.remove(p.getUniqueId());
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();
        MysteryTeam t = this.teamManager.getByPlayer(p);

        if (t != null) {

            // Handling the drops
            ItemStack item = getItem(t);

            for (int i = 0; i < event.getDrops().size(); i++) {
                if (event.getDrops().get(i).getType() == item.getType())
                    event.getDrops().remove(i);
            }

            event.getDrops().add(item);

            // Remove player from the team
            t.getPlayers().remove(p.getUniqueId());

            // Chat messages
            Bukkit.broadcastMessage(prefix() + t.getColorData().getChatColor() + "Player from team " + t.getColorData().getName() + " died.");

            if (t.getPlayers().size() > 0) {
                Bukkit.broadcastMessage(prefix() + t.getColorData().getChatColor() + "Team " + t.getColorData().getName() + " has " + ChatColor.RESET + t.getPlayers().size() + t.getColorData().getChatColor()
                        + (t.getPlayers().size() > 1 ? " players" : " player") + " left");
            } else {
                this.teamManager.getTeams().remove(t);
                Bukkit.broadcastMessage(prefix() + t.getColorData().getChatColor()
                                + "Team "
                                + t.getColorData().getName()
                                + " eliminated. "
                                + (this.teamManager.getTeams().size() > 0 ? "There are " + ChatColor.RESET + this.teamManager.getTeams().size() + t.getColorData().getChatColor() + " teams left." : "The "
                                + t.getColorData().getName() + " team won!")
                );
            }
        }
    }
}

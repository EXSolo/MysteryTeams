package com.exsoloscript.mysteryteams.event;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.material.Wool;

import com.exsoloscript.mysteryteams.MysteryTeams;
import com.exsoloscript.mysteryteams.data.MysteryTeam;

public class DeathListener implements Listener {

	private MysteryTeams plugin;

	public DeathListener(MysteryTeams t) {
		this.plugin = t;
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player p = event.getEntity();
		MysteryTeam t = this.plugin.getTeamManager().getByPlayer(p);

		if (t != null) {
			if (t.getPlayers().get(p.getUniqueId())) {
				for (int i = 0; i < event.getDrops().size(); i++) {
					if (event.getDrops().get(i).getType() == Material.WOOL)
						event.getDrops().remove(i);
				}

				event.getDrops().add(new Wool(t.getColorData().getDyeColor()).toItemStack(1));

				Bukkit.broadcastMessage(plugin.prefix() + t.getColorData().getChatColor() + "Player from team " + t.getColorData().getName() + " died.");
				t.getPlayers().put(p.getUniqueId(), false);

				if (t.hasPlayersAlive()) {
					Bukkit.broadcastMessage(plugin.prefix() + t.getColorData().getChatColor() + "Team " + t.getColorData().getName() + " has " + ChatColor.RESET + t.playersAlive() + t.getColorData().getChatColor()
							+ (t.playersAlive() > 1 ? " players" : " player") + " left");
				} else {
					Bukkit.broadcastMessage(plugin.prefix()
							+ t.getColorData().getChatColor()
							+ "Team "
							+ t.getColorData().getName()
							+ " eliminated. "
							+ (this.plugin.getTeamManager().getTeamsWithAlivePlayers().size() > 1 ? "There are " + ChatColor.RESET + (this.plugin.getTeamManager().getTeamsWithAlivePlayers().size()) + t.getColorData().getChatColor() + " teams left."
									: "The " + (this.plugin.getTeamManager().getTeamsWithAlivePlayers().size() == 1 ? this.plugin.getTeamManager().getTeamsWithAlivePlayers().get(0).getColorData().getName() : "last") + " team won!"));
				}
			}
		}
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if (event.getEntityType() == EntityType.SHEEP)
			event.getDrops().clear();
	}
}
package com.exsoloscript.mysteryteams.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.exsoloscript.mysteryteams.MysteryTeams;
import com.exsoloscript.mysteryteams.data.MysteryTeam;

public class JoinListener implements Listener {

	private MysteryTeams plugin;

	public JoinListener(MysteryTeams t) {
		this.plugin = t;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (this.plugin.getMissingPlayers().contains(event.getPlayer().getUniqueId())) {
			Player p = event.getPlayer();
			MysteryTeam t = this.plugin.getTeamManager().getByPlayer(event.getPlayer());
			if (t != null) {
				this.plugin.giveWool(p.getUniqueId(), t.getColorData());
			}
		}
	}
}

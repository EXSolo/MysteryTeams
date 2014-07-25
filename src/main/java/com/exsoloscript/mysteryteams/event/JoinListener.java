package com.exsoloscript.mysteryteams.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;

import com.exsoloscript.mysteryteams.MysteryTeams;
import com.exsoloscript.mysteryteams.data.MysteryTeam;

public class JoinListener implements Listener {

	private MysteryTeams plugin;

	public JoinListener(MysteryTeams t) {
		this.plugin = t;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (this.plugin.getMissingPlayers().contains(event.getPlayer().getName())) {
			Player p = event.getPlayer();
			MysteryTeam t = this.plugin.getTeamManager().getByPlayer(event.getPlayer());
			ItemStack tmp = new Wool(t.getColorData().getDyeColor()).toItemStack();
			ItemStack wool = new ItemStack(tmp.getType(), tmp.getAmount(), tmp.getDurability());
			if (t != null) {
				if (p.getInventory().firstEmpty() > -1)
					p.getInventory().addItem(wool);
				else {
					p.getWorld().dropItem(p.getLocation(), wool);
					p.sendMessage(plugin.prefix() + "Your wool was dropped on the ground since your inventory is full!");
				}
				this.plugin.getMissingPlayers().remove(p.getName());
			}
		}
	}
}

package com.exsoloscript.mysteryteams.event;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.exsoloscript.mysteryteams.MysteryTeams;
import com.exsoloscript.mysteryteams.data.MysteryTeam;

public class WoolListener implements Listener {

	private MysteryTeams plugin;

	public WoolListener(MysteryTeams t) {
		this.plugin = t;
	}

	@EventHandler
	public void onPlayerShear(PlayerShearEntityEvent event) {
		if (event.getEntity().getType() == EntityType.SHEEP)
			event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerCraft(PrepareItemCraftEvent event) {
		if (event.getInventory().getResult().getType() == Material.WOOL) {
			for (HumanEntity he : event.getViewers()) {
				if (he instanceof Player) {
					Player p = (Player) he;
					MysteryTeam mt = this.plugin.getTeamManager().getByPlayer(p);
					if (mt != null) {
						event.getInventory().setResult(new ItemStack(Material.AIR, 1));
					}
				}
			}
		}
	}
}

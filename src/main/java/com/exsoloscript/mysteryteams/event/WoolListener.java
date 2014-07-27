package com.exsoloscript.mysteryteams.event;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;

public class WoolListener implements Listener {

	@EventHandler
	public void onPrepareCraft(PrepareItemCraftEvent event) {
		if (event.getInventory().getResult().getType() == Material.WOOL)
			event.getInventory().getResult().setType(Material.AIR);
	}
	
	@EventHandler
	public void onPlayerShear(PlayerShearEntityEvent event) {
		if (event.getEntity().getType() == EntityType.SHEEP)
			event.setCancelled(true);
	}
	
}

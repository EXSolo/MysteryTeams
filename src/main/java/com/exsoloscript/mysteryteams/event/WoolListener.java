package com.exsoloscript.mysteryteams.event;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class WoolListener implements Listener {

	@EventHandler
	public void onPrepareCraft(PrepareItemCraftEvent event) {
		if (event.getInventory().getResult().getType() == Material.WOOL)
			event.getInventory().getResult().setType(Material.AIR);
	}
	
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event) {
		if (event.getItemDrop().getItemStack().getType() == Material.WOOL && !event.getPlayer().isOp())
			event.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (event.getBlock().getType() == Material.WOOL && !event.getPlayer().isOp())
			event.setCancelled(true);
	}
	
}

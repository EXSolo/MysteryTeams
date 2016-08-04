package com.exsoloscript.mysteryteams.module

import com.exsoloscript.mysteryteams.team.MysteryTeam
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.event.player.PlayerShearEntityEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.material.Wool

class WoolModule : TeamBasedModule("Wools") {

    override fun getItem(t: MysteryTeam): ItemStack {
        return Wool(t.colorData.dyeColor).toItemStack(1)
    }

    @EventHandler
    fun onPrepareCraft(event: PrepareItemCraftEvent) {
        if (event.inventory.result != null) {
            if (event.inventory.result.type == Material.WOOL)
                event.inventory.result = ItemStack(Material.AIR)
        }
    }

    @EventHandler
    fun onPlayerShear(event: PlayerShearEntityEvent) {
        if (event.entity.type == EntityType.SHEEP)
            event.isCancelled = true
    }

    @EventHandler
    fun onEntityDeath(event: EntityDeathEvent) {
        if (event.entityType == EntityType.SHEEP) {
            val drops = event.drops
            for (i in drops.indices) {
                if (drops[i].type == Material.WOOL)
                    drops.removeAt(i)
            }
        }
    }
}

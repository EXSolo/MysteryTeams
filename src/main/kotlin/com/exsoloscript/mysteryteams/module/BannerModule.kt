package com.exsoloscript.mysteryteams.module

import com.exsoloscript.mysteryteams.team.MysteryTeam
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BannerMeta

class BannerModule : TeamBasedModule("Banners") {

    override fun getItem(t: MysteryTeam): ItemStack {
        val i = ItemStack(Material.BANNER)

        val bm = i.itemMeta as BannerMeta
        bm.baseColor = t.colorData.dyeColor
        i.itemMeta = bm

        return i
    }

    @EventHandler
    fun onPrepareCraft(event: PrepareItemCraftEvent) {
        if (event.inventory.result != null) {
            if (event.inventory.result.type == Material.BANNER)
                event.inventory.result = ItemStack(Material.AIR)
        }
    }
}

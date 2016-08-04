package com.exsoloscript.mysteryteams.module

import com.exsoloscript.mysteryteams.team.MysteryTeam
import org.bukkit.FireworkEffect
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.FireworkMeta

class FireworkModule : TeamBasedModule("Fireworks") {

    override fun getItem(t: MysteryTeam): ItemStack {
        val i = ItemStack(Material.FIREWORK, 32)
        val fm = i.itemMeta as FireworkMeta

        val fe = FireworkEffect.builder().withColor(t.colorData.color).with(FireworkEffect.Type.BALL_LARGE).build()
        fm.addEffect(fe)
        fm.power = 2

        i.itemMeta = fm
        return i
    }

    @EventHandler
    fun onPrepareCraft(event: PrepareItemCraftEvent) {
        if (event.inventory.result != null) {
            if (event.inventory.result.type == Material.FIREWORK)
                event.inventory.result = ItemStack(Material.AIR)
        }
    }
}

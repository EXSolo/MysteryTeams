package com.exsoloscript.mysteryteams.module;

import com.exsoloscript.mysteryteams.team.MysteryTeam;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;

import java.util.List;

public class WoolModule extends TeamBasedModule {

    public WoolModule() {
        super("Wools");
    }

    @Override
    public ItemStack getItem(MysteryTeam t) {
        return new Wool(t.getColorData().getDyeColor()).toItemStack(1);
    }

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        if (event.getInventory().getResult() != null) {
            if (event.getInventory().getResult().getType() == Material.WOOL)
                event.getInventory().setResult(new ItemStack(Material.AIR));
        }
    }

    @EventHandler
    public void onPlayerShear(PlayerShearEntityEvent event) {
        if (event.getEntity().getType() == EntityType.SHEEP)
            event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntityType() == EntityType.SHEEP) {
            List<ItemStack> drops = event.getDrops();
            for (int i = 0; i < drops.size(); i++) {
                if (drops.get(i).getType() == Material.WOOL)
                    drops.remove(i);
            }
        }
    }
}

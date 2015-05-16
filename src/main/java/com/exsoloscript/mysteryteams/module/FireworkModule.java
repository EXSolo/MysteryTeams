package com.exsoloscript.mysteryteams.module;

import com.exsoloscript.mysteryteams.team.MysteryTeam;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkModule extends TeamBasedModule {

    public FireworkModule() {
        super("Fireworks");
    }

    @Override
    public ItemStack getItem(MysteryTeam t) {
        ItemStack i = new ItemStack(Material.FIREWORK, 32);
        FireworkMeta fm = (FireworkMeta) i.getItemMeta();

        FireworkEffect fe = FireworkEffect.builder().withColor(t.getColorData().getColor()).with(FireworkEffect.Type.BALL_LARGE).build();
        fm.addEffect(fe);
        fm.setPower(2);

        i.setItemMeta(fm);
        return i;
    }

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        if (event.getInventory().getResult().getType() == Material.FIREWORK)
            event.getInventory().getResult().setType(Material.AIR);
    }
}

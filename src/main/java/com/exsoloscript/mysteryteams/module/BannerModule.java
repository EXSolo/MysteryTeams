package com.exsoloscript.mysteryteams.module;

import com.exsoloscript.mysteryteams.team.MysteryTeam;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

public class BannerModule extends TeamBasedModule {

    public BannerModule() {
        super("Banners");
    }

    @Override
    public ItemStack getItem(MysteryTeam t) {
        ItemStack i = new ItemStack(Material.BANNER);

        BannerMeta bm = (BannerMeta) i.getItemMeta();
        bm.setBaseColor(t.getColorData().getDyeColor());
        i.setItemMeta(bm);

        return i;
    }

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        if (event.getInventory().getResult().getType() == Material.BANNER)
            event.getInventory().getResult().setType(Material.AIR);
    }
}

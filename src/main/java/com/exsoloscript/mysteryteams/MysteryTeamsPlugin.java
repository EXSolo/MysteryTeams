package com.exsoloscript.mysteryteams;

import com.exsoloscript.mysteryteams.cmd.MysteryTeamsCommand;
import com.exsoloscript.mysteryteams.module.*;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class MysteryTeamsPlugin extends JavaPlugin {

    private ModuleManager moduleManager;

    public void onEnable() {
        this.moduleManager = new ModuleManager(this);
        register();
    }

    public void onDisable() {

    }

    public void register() {
        getCommand("mt").setExecutor(new MysteryTeamsCommand(this));

        // Load modules

        this.getModuleManager().registerModule(new WoolModule());
        this.getModuleManager().registerModule(new BannerModule());
        this.getModuleManager().registerModule(new FireworkModule());
    }

    public ModuleManager getModuleManager() {
        return this.moduleManager;
    }

    public static String prefix() {
        return ChatColor.GOLD + "[" + ChatColor.RED + "MysteryTeams" + ChatColor.GOLD + "] " + ChatColor.WHITE;
    }
}

package com.exsoloscript.mysteryteams

import com.exsoloscript.mysteryteams.cmd.MysteryTeamsCommand
import com.exsoloscript.mysteryteams.module.*
import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin

class MysteryTeamsPlugin : JavaPlugin() {

    val moduleManager: ModuleManager =  ModuleManager(this)

    override fun onEnable() {
        register()
    }

    fun register() {
        getCommand("mt").executor = MysteryTeamsCommand(this)

        // Load modules

        this.moduleManager.registerModule(WoolModule())
        this.moduleManager.registerModule(BannerModule())
        this.moduleManager.registerModule(FireworkModule())
    }

    companion object {

        fun prefix(): String {
            return "${ChatColor.GOLD}[${ChatColor.RED}MysteryTeams${ChatColor.GOLD}] ${ChatColor.WHITE}"
        }
    }
}

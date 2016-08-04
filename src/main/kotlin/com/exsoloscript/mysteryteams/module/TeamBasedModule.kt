package com.exsoloscript.mysteryteams.module

import com.exsoloscript.mysteryteams.MysteryTeamsPlugin.Companion.prefix
import com.exsoloscript.mysteryteams.team.MysteryTeam
import com.exsoloscript.mysteryteams.team.MysteryTeamManager
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack
import java.util.*

abstract class TeamBasedModule(moduleName: String) : GameModule(moduleName) {

    val missingPlayers: MutableList<UUID>
    val teamManager: MysteryTeamManager

    init {
        this.missingPlayers = ArrayList<UUID>()
        this.teamManager = MysteryTeamManager(this)
    }

    abstract fun getItem(t: MysteryTeam): ItemStack

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        if (this.missingPlayers.contains(player.uniqueId)) {
            this.teamManager.giveItem(player)
            this.missingPlayers.remove(player.uniqueId)
        }
    }

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val p = event.entity
        val t = this.teamManager.getByPlayer(p)

        if (t != null) {

            // Handling the drops
            val item = getItem(t)

            for (i in 0..event.drops.size - 1) {
                if (event.drops[i].type == item.type)
                    event.drops.removeAt(i)
            }

            event.drops.add(item)

            // Remove player from the team
            t.players.remove(p.uniqueId)

            // Chat messages
            Bukkit.broadcastMessage(prefix() + t.colorData.chatColor + "Player from team " + t.colorData.name + " died.")

            if (t.players.size > 0) {
                Bukkit.broadcastMessage(prefix() + t.colorData.chatColor + "Team " + t.colorData.name + " has " + ChatColor.RESET + t.players.size + t.colorData.chatColor
                        + (if (t.players.size > 1) " players" else " player") + " left")
            } else {
                this.teamManager.teams.remove(t)
                Bukkit.broadcastMessage(prefix() + t.colorData.chatColor
                        + "Team "
                        + t.colorData.name
                        + " eliminated. "
                        + if (this.teamManager.teams.size > 0)
                    "There are " + ChatColor.RESET + this.teamManager.teams.size + t.colorData.chatColor + " teams left."
                else
                    "The "
                            + t.colorData.name + " team won!")
            }
        }
    }
}

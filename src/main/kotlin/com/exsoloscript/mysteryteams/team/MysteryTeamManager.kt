package com.exsoloscript.mysteryteams.team

import com.exsoloscript.mysteryteams.MysteryTeamsPlugin.Companion.prefix
import com.exsoloscript.mysteryteams.module.TeamBasedModule
import com.exsoloscript.mysteryteams.team.MysteryTeam.ColorData
import com.exsoloscript.mysteryteams.util.CollectionUtils
import com.exsoloscript.mysteryteams.util.Colors
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import java.util.*

class MysteryTeamManager(private val module: TeamBasedModule) {

    var teams: MutableList<MysteryTeam> = ArrayList()
    var colors: Colors = Colors()

    init {
        reset()
    }

    fun newTeam(): MysteryTeam? {
        val cd = this.colors.next ?: return null
        val mt = MysteryTeam(this.teams.size + 1, cd)

        this.teams.add(mt)
        return mt
    }

    fun assignTeams(players: List<Player>, teamCount: Int) {
        reset()

        Collections.shuffle(players)

        val teams = CollectionUtils.split(players, teamCount)

        for (team in teams) {
            val t = newTeam()
            for (p in team)
                t!!.players.add(p.uniqueId)
        }

    }

    fun getByPlayer(p: OfflinePlayer): MysteryTeam? {
        for (t in this.teams)
            for (uuid in t.players)
                if (uuid == p.uniqueId)
                    return t
        return null
    }

    fun getByID(id: Int): MysteryTeam? {
        for (t in this.teams)
            if (t.id == id)
                return t
        return null
    }

    fun getTeamByColor(color: ColorData): MysteryTeam? {
        for (t in this.teams)
            if (t.colorData == color)
                return t
        return null
    }

    fun reset() {
        this.teams = ArrayList<MysteryTeam>()
        this.colors = Colors()
    }

    fun giveItems() {
        for (t in teams) {
            for (uuid in t.players) {
                val p = Bukkit.getPlayer(uuid)
                val item = module.getItem(t)
                if (p != null) {
                    if (p.inventory.firstEmpty() > -1) {
                        p.inventory.addItem(item)
                    } else {
                        p.world.dropItem(p.location, item)
                        p.sendMessage(prefix() + ChatColor.RED + "Your item was dropped on the ground since your inventory is full!")
                    }
                } else {
                    module.missingPlayers.add(uuid)
                }
            }
        }
    }

    fun giveItem(player: Player?) {
        if (player != null) {
            val team = getByPlayer(player)
            if (team != null) {
                val item = module.getItem(team)
                if (player.inventory.firstEmpty() > -1) {
                    player.inventory.addItem(item)
                } else {
                    player.world.dropItem(player.location, item)
                    player.sendMessage(prefix() + ChatColor.RED + "Your item was dropped on the ground since your inventory is full!")
                }
            }
        }
    }

    fun stringifyTeamSizes(): String {
            var out = ""
            if (teams.size > 0) {
                out += prefix() + "Printing teamsize of existing teams:\n"
                for (t in teams) {
                    out += " - " + t.colorData.chatColor + "Team " + t.colorData.name + ": " + ChatColor.RESET + t.players.size + "\n"
                }
            } else {
                out = prefix() + "No teams defined yet."
            }

            return out
        }

    override fun toString(): String {
        var out = ""
        if (teams.size > 0) {
            out += prefix() + "Printing existing teams:\n"
            for (t in teams) {
                out += "${t.colorData.chatColor} Team " + t.colorData.chatColor.name.toLowerCase() + ":\n"
                for (uuid in t.players) {
                    out += " - ${if (Bukkit.getOfflinePlayer(uuid) == null) ChatColor.RED.toString() else ChatColor.GREEN.toString() + Bukkit.getOfflinePlayer(uuid).name}\n"
                }
            }
        } else {
            out = prefix() + "No teams defined yet."
        }

        return out
    }
}

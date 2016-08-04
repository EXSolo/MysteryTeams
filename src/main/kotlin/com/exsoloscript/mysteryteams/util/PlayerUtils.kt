package com.exsoloscript.mysteryteams.util

import org.bukkit.Bukkit
import org.bukkit.entity.Player

import java.util.ArrayList

object PlayerUtils {

    val onlinePlayersWithoutOps: List<Player>
        get() {
            val players = ArrayList<Player>()
            for (p in Bukkit.getOnlinePlayers()) {
                if (!p.isOp)
                    players.add(p)
            }

            return players
        }
}

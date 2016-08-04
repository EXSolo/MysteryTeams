package com.exsoloscript.mysteryteams.team

import org.bukkit.ChatColor
import org.bukkit.Color
import org.bukkit.DyeColor
import java.util.*

class MysteryTeam(var id: Int, var colorData: MysteryTeam.ColorData) {

    val players: MutableList<UUID>

    init {
        this.players = ArrayList<UUID>()
    }

    enum class ColorData constructor(val chatColor: ChatColor, val dyeColor: DyeColor, val color: Color, val readableName: String) {

        RED(ChatColor.RED, DyeColor.RED, Color.RED, "red"),
        BLUE(ChatColor.BLUE, DyeColor.BLUE, Color.BLUE, "blue"),
        GREEN(ChatColor.GREEN, DyeColor.GREEN, Color.GREEN, "green"),
        YELLOW(ChatColor.YELLOW, DyeColor.YELLOW, Color.YELLOW, "yellow"),
        PURPLE(ChatColor.DARK_PURPLE, DyeColor.PURPLE, Color.PURPLE, "purple"),
        GRAY(ChatColor.GRAY, DyeColor.GRAY, Color.GRAY, "gray"),
        PINK(ChatColor.LIGHT_PURPLE, DyeColor.PINK, Color.RED.mixColors(Color.WHITE), "pink"),
        ORANGE(ChatColor.GOLD, DyeColor.ORANGE, Color.ORANGE, "orange"),
        BLACK(ChatColor.BLACK, DyeColor.BLACK, Color.BLACK, "black"),
        WHITE(ChatColor.WHITE, DyeColor.WHITE, Color.WHITE, "white"),
        CYAN(ChatColor.DARK_AQUA, DyeColor.CYAN, Color.AQUA, "cyan");


        companion object {

            fun getByName(name: String): ColorData? {
                for (data in values()) {
                    if (data.readableName.equals(name, ignoreCase = true))
                        return data
                }
                return null
            }
        }
    }
}

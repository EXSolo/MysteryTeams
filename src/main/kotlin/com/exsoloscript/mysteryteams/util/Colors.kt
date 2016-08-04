package com.exsoloscript.mysteryteams.util

import com.exsoloscript.mysteryteams.team.MysteryTeam

import java.util.ArrayList
import java.util.Arrays

class Colors {
    private var availableColors: MutableList<MysteryTeam.ColorData>? = null

    init {
        reset()
    }

    val next: MysteryTeam.ColorData?
        get() {
            if (availableColors!!.size > 0) {
                return availableColors!!.removeAt(0)
            }

            return null
        }

    fun reset() {
        this.availableColors = ArrayList(Arrays.asList(*MysteryTeam.ColorData.values()))
    }

}

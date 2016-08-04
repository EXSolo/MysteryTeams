package com.exsoloscript.mysteryteams.module

import org.bukkit.event.Listener

abstract class GameModule(val name: String?) : Listener {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GameModule) return false

        if (if (name != null) name != other.name else other.name != null) return false

        return true
    }

    override fun hashCode(): Int {
        return if (name != null) name.hashCode() else 0
    }
}

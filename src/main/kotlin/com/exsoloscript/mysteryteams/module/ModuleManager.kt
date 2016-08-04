package com.exsoloscript.mysteryteams.module

import com.google.common.base.Preconditions
import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class ModuleManager(private val plugin: JavaPlugin) {

    var currentModule: GameModule?

    val registeredModules: MutableList<GameModule>

    init {
        this.registeredModules = ArrayList<GameModule>()
        this.currentModule = WoolModule()

        Bukkit.getPluginManager().registerEvents(this.currentModule, this.plugin)
    }

    /**
     * Only one module can be enabled at a time.
     * If a different module is enabled it will be disabled

     * @param m The module that shall be enabled
     */
    fun enableModule(m: GameModule) {
        Preconditions.checkNotNull(m)

        disableCurrentModule()

        this.currentModule = m
        Bukkit.getPluginManager().registerEvents(currentModule, this.plugin)
    }

    fun disableCurrentModule() {
        HandlerList.unregisterAll(this.currentModule)
    }

    /**
     * Registers a GameModule. This method does not enable the module

     * @param m The module that shall be registered
     * *
     * @return false if the module was registered before, true otherwise
     */
    fun registerModule(m: GameModule): Boolean {
        if (!moduleRegistered(m)) {
            this.registeredModules.add(m)
            return true
        } else
            return false
    }

    /**
     * Unregisters a GameModule. This method does not enable the module

     * @param m The module that shall be unregistered
     * *
     * @return false if the module was not registered before, true otherwise
     */
    fun unregisterModule(m: GameModule): Boolean {
        if (!moduleRegistered(m)) {
            if (this.currentModule == m) {
                disableCurrentModule()
                this.currentModule = null
            }

            this.registeredModules.remove(m)
            return true
        } else
            return false
    }

    private fun moduleRegistered(m: GameModule): Boolean {
        return this.registeredModules.contains(m)
    }

    fun getModule(name: String): GameModule? {
        for (m in this.registeredModules) {
            if (m.name.equals(name, ignoreCase = true))
                return m
        }

        return null
    }
}

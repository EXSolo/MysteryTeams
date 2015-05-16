package com.exsoloscript.mysteryteams.module;

import com.exsoloscript.mysteryteams.MysteryTeamsPlugin;
import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private MysteryTeamsPlugin plugin;

    private GameModule current;

    private List<GameModule> registeredModules;

    public ModuleManager(MysteryTeamsPlugin plugin) {
        this.plugin = plugin;
        this.registeredModules = new ArrayList<>();

        this.current = new WoolModule();
        Bukkit.getPluginManager().registerEvents(this.current, this.plugin);
    }

    /**
     * Only one module can be enabled at a time.
     * If a different module is enabled it will be disabled
     *
     * @param m The module that shall be enabled
     */
    public void enableModule(GameModule m) {
        Preconditions.checkNotNull(m);

        disableCurrentModule();

        this.current = m;
        Bukkit.getPluginManager().registerEvents(current, this.plugin);
    }

    public void disableCurrentModule() {
        if (this.current != null) {
            HandlerList.unregisterAll(this.current);
        }
    }

    /**
     * Registers a GameModule. This method does not enable the module
     *
     * @param m The module that shall be registered
     * @return false if the module was registered before, true otherwise
     */
    public boolean registerModule(GameModule m) {
        if (!moduleRegistered(m)) {
            this.registeredModules.add(m);
            return true;
        } else return false;
    }

    /**
     * Unregisters a GameModule. This method does not enable the module
     *
     * @param m The module that shall be unregistered
     * @return false if the module was not registered before, true otherwise
     */
    public boolean unregisterModule(GameModule m) {
        if (!moduleRegistered(m)) {
            if (this.current.equals(m)) {
                disableCurrentModule();
                this.current = null;
            }

            this.registeredModules.remove(m);
            return true;
        } else return false;
    }

    private boolean moduleRegistered(GameModule m) {
        return this.registeredModules.contains(m);
    }

    public GameModule getModule(String name) {
        for (GameModule m : this.registeredModules) {
            if (m.getName().equalsIgnoreCase(name))
                return m;
        }

        return null;
    }

    public List<GameModule> getRegisteredModules() {
        return registeredModules;
    }

    public GameModule getCurrentModule() {
        return this.current;
    }
}

package com.exsoloscript.mysteryteams.module;

import org.bukkit.event.Listener;

public abstract class GameModule implements Listener {

    private String name;

    public GameModule(String moduleName) {
        this.name = moduleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameModule)) return false;

        GameModule that = (GameModule) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public String getName() {
        return name;
    }
}

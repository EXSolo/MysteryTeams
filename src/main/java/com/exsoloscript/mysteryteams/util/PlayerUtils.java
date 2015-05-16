package com.exsoloscript.mysteryteams.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerUtils {

    public static List<Player> getOnlinePlayersWithoutOps() {
        List<Player> players = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.isOp())
                players.add(p);
        }

        return players;
    }
}

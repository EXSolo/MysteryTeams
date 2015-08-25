package com.exsoloscript.mysteryteams.team;

import com.exsoloscript.mysteryteams.module.TeamBasedModule;
import com.exsoloscript.mysteryteams.team.MysteryTeam.ColorData;
import com.exsoloscript.mysteryteams.util.CollectionUtils;
import com.exsoloscript.mysteryteams.util.Colors;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.exsoloscript.mysteryteams.MysteryTeamsPlugin.prefix;

public class MysteryTeamManager {

    private TeamBasedModule module;

    private List<MysteryTeam> teams;
    private Colors colors;

    public MysteryTeamManager(TeamBasedModule module) {
        this.module = module;
        reset();
    }

    public MysteryTeam newTeam() {
        ColorData cd = this.colors.getNext();

        if (cd == null)
            return null;

        MysteryTeam mt = new MysteryTeam(this.teams.size() + 1, cd);

        this.teams.add(mt);
        return mt;
    }

    public void assignTeams(List<Player> players, int teamCount) {
        reset();

        Collections.shuffle(players);

        List<List<Player>> teams = CollectionUtils.split(players, teamCount);

        for (List<Player> team : teams) {
            MysteryTeam t = newTeam();
            for (Player p : team)
                t.getPlayers().add(p.getUniqueId());
        }

    }

    public MysteryTeam getByPlayer(Player p) {
        for (MysteryTeam t : this.teams)
            for (UUID uuid : t.getPlayers())
                if (uuid.equals(p.getUniqueId()))
                    return t;
        return null;
    }

    public MysteryTeam getByID(int id) {
        for (MysteryTeam t : this.teams)
            if (t.getId() == id)
                return t;
        return null;
    }

    public void reset() {
        this.teams = new ArrayList<>();
        this.colors = new Colors();
    }

    public void giveItems() {
        for (MysteryTeam t : getTeams()) {
            for (UUID uuid : t.getPlayers()) {
                Player p = Bukkit.getPlayer(uuid);
                ItemStack item = module.getItem(t);
                if (p != null)
                    if (p.getInventory().firstEmpty() > -1) {
                        p.getInventory().addItem(item);
                    } else {
                        p.getWorld().dropItem(p.getLocation(), item);
                        p.sendMessage(prefix() + ChatColor.RED + "Your item was dropped on the ground since your inventory is full!");
                    }
                else
                    module.getMissingPlayers().add(uuid);
            }
        }
    }

    public void giveItem(Player player) {
        if (player != null) {
            MysteryTeam team = getByPlayer(player);
            if (team != null) {
                ItemStack item = module.getItem(team);
                if (player.getInventory().firstEmpty() > -1) {
                    player.getInventory().addItem(item);
                } else {
                    player.getWorld().dropItem(player.getLocation(), item);
                    player.sendMessage(prefix() + ChatColor.RED + "Your item was dropped on the ground since your inventory is full!");
                }
            }
        }
    }

    public String getTeamSizes() {
        String out = "";
        if (getTeams().size() > 0) {
            out += prefix() + "Printing teamsize of existing teams:\n";
            for (MysteryTeam t : getTeams()) {
                out += " - " + t.getColorData().getChatColor() + "Team " + t.getColorData().getName() + ": " + ChatColor.RESET + t.getPlayers().size() + "\n";
            }
        } else {
            out = prefix() + "No teams defined yet.";
        }

        return out;
    }

    public List<MysteryTeam> getTeams() {
        return this.teams;
    }

    @Override
    public String toString() {
        String out = "";
        if (getTeams().size() > 0) {
            out += prefix() + "Printing existing teams:\n";
            for (MysteryTeam t : getTeams()) {
                out += t.getColorData().getChatColor() + "Team " + t.getColorData().getChatColor().name().toLowerCase() + ":\n";
                for (UUID uuid : t.getPlayers()) {
                    out += " - " + (Bukkit.getOfflinePlayer(uuid) == null ? ChatColor.RED : ChatColor.GREEN) + Bukkit.getOfflinePlayer(uuid).getName() + "\n";
                }
            }
        } else {
            out = prefix() + "No teams defined yet.";
        }

        return out;
    }
}

package com.exsoloscript.mysteryteams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.exsoloscript.mysteryteams.cmd.MysteryTeamsCommand;
import com.exsoloscript.mysteryteams.data.MysteryTeam.ColorData;
import com.exsoloscript.mysteryteams.data.MysteryTeamManager;
import com.exsoloscript.mysteryteams.event.DeathListener;
import com.exsoloscript.mysteryteams.event.JoinListener;
import com.exsoloscript.mysteryteams.event.WoolListener;

public class MysteryTeams extends JavaPlugin {

	private Map<UUID, Integer> teamData;
	private List<ColorData> availableColors;
	private List<UUID> missingPlayers;
	private MysteryTeamManager teamManager;

	public void onEnable() {
		this.teamData = new HashMap<UUID, Integer>();
		this.availableColors = new ArrayList<ColorData>(Arrays.asList(ColorData.values()));
		this.teamManager = new MysteryTeamManager(this);
		this.missingPlayers = new ArrayList<UUID>();

		register();
	}

	public void onDisable() {

	}

	public void register() {

		// Command

		getCommand("mt").setExecutor(new MysteryTeamsCommand(this));

		// Events

		PluginManager pm = Bukkit.getPluginManager();

		pm.registerEvents(new JoinListener(this), this);
		pm.registerEvents(new DeathListener(this), this);
		pm.registerEvents(new WoolListener(this), this);
	}

	public void resetTeamManager() {
		this.availableColors = new ArrayList<ColorData>(Arrays.asList(ColorData.values()));
		this.teamData.clear();
		this.missingPlayers.clear();
		this.teamManager = new MysteryTeamManager(this);
	}
	
	public void giveWool(UUID u, ColorData cd) {
		ItemStack wool = new Wool(cd.getDyeColor()).toItemStack(1);
		Player p = Bukkit.getPlayer(u);
		if (p != null)
			if (p.getInventory().firstEmpty() > -1) {
				p.getInventory().addItem(wool);
			} else {
				p.getWorld().dropItem(p.getLocation(), wool);
				p.sendMessage(prefix() + "Your wool was dropped on the ground since your inventory is full!");
			}
		else
			this.missingPlayers.add(u);
	}

	public String prefix() {
		return ChatColor.GOLD + "[" + ChatColor.RED + "MysteryTeams" + ChatColor.GOLD + "] " + ChatColor.WHITE;
	}

	public List<ColorData> getAvailableColors() {
		return availableColors;
	}

	public Map<UUID, Integer> getTeamData() {
		return teamData;
	}

	public MysteryTeamManager getTeamManager() {
		return this.teamManager;
	}

	public List<UUID> getMissingPlayers() {
		return this.missingPlayers;
	}
}

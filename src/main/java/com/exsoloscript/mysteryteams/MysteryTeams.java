package com.exsoloscript.mysteryteams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
	private List<String> missingPlayers;
	private MysteryTeamManager teamManager;

	public void onEnable() {
		this.teamData = new HashMap<UUID, Integer>();
		this.availableColors = new ArrayList<ColorData>(Arrays.asList(ColorData.values()));
		this.teamManager = new MysteryTeamManager(this);
		this.missingPlayers = new ArrayList<String>();

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
		pm.registerEvents(new WoolListener(), this);
	}

	public void resetTeamManager() {
		availableColors = new ArrayList<ColorData>(Arrays.asList(ColorData.values()));
		this.teamManager = new MysteryTeamManager(this);
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

	public List<String> getMissingPlayers() {
		return this.missingPlayers;
	}
}

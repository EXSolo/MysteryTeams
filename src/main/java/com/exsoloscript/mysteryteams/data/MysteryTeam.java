package com.exsoloscript.mysteryteams.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.OfflinePlayer;

public class MysteryTeam {

	private int id;
	private Map<UUID, Boolean> players;
	private ColorData colorData;

	public MysteryTeam(int id, ColorData cd) {
		this.id = id;
		this.colorData = cd;
		this.players = new HashMap<UUID, Boolean>();
	}
	
	public void addPlayer(OfflinePlayer p) {
		this.players.put(p.getUniqueId(), true);
	}
	
	public void removePlayer(OfflinePlayer p) {
		this.players.remove(p.getUniqueId());
	}
	
	public int playersAlive() {
		int alive = 0;

		for (Entry<UUID, Boolean> e : this.players.entrySet())
			if (e.getValue())
				alive++;
		
		return alive;
	}
	
	public boolean hasPlayersAlive() {
		return (playersAlive() > 0);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ColorData getColorData() {
		return colorData;
	}

	public void setColorData(ColorData colorData) {
		this.colorData = colorData;
	}
	
	public Map<UUID, Boolean> getPlayers() {
		return this.players;
	}

	public enum ColorData {

		RED(ChatColor.RED, DyeColor.RED, "red"), BLUE(ChatColor.BLUE, DyeColor.BLUE, "blue"), GREEN(ChatColor.GREEN, DyeColor.GREEN, "green"), YELLOW(ChatColor.YELLOW, DyeColor.YELLOW, "yellow"), PURPLE(ChatColor.DARK_PURPLE, DyeColor.PURPLE, "purple"), GRAY(ChatColor.GRAY, DyeColor.GRAY, "gray"), PINK(
				ChatColor.LIGHT_PURPLE, DyeColor.PINK, "pink"), ORANGE(ChatColor.GOLD, DyeColor.ORANGE, "orange"), BLACK(ChatColor.BLACK, DyeColor.BLACK, "black"), WHITE(ChatColor.WHITE, DyeColor.WHITE, "black"), CYAN(ChatColor.DARK_AQUA, DyeColor.CYAN, "cyan");

		private String name;
		private ChatColor cc;
		private DyeColor dc;

		private ColorData(ChatColor cc, DyeColor dc, String name) {
			this.cc = cc;
			this.dc = dc;
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		
		public DyeColor getDyeColor() {
			return dc;
		}
		
		public ChatColor getChatColor() {
			return cc;
		}
	}
}

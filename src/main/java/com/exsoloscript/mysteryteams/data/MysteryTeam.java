package com.exsoloscript.mysteryteams.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

public class MysteryTeam {

	private List<UUID> players;
	private int id;
	private ColorData colorData;

	public MysteryTeam(int id, ColorData cd) {
		this.id = id;
		this.colorData = cd;
		this.players = new ArrayList<UUID>();
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
	
	public List<UUID> getPlayers() {
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

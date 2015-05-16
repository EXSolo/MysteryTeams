package com.exsoloscript.mysteryteams.team;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;

public class MysteryTeam {

	private List<UUID> players;
	private int id;
	private ColorData colorData;

	public MysteryTeam(int id, ColorData cd) {
		this.id = id;
		this.colorData = cd;
		this.players = new ArrayList<>();
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

		RED(ChatColor.RED, DyeColor.RED, Color.RED, "red"), BLUE(ChatColor.BLUE, DyeColor.BLUE, Color.BLUE, "blue"), GREEN(ChatColor.GREEN, DyeColor.GREEN, Color.GREEN, "green"),
        YELLOW(ChatColor.YELLOW, DyeColor.YELLOW, Color.YELLOW, "yellow"), PURPLE(ChatColor.DARK_PURPLE, DyeColor.PURPLE, Color.PURPLE, "purple"),
        GRAY(ChatColor.GRAY, DyeColor.GRAY, Color.GRAY, "gray"), PINK(ChatColor.LIGHT_PURPLE, DyeColor.PINK, Color.RED.mixColors(Color.WHITE), "pink"), ORANGE(ChatColor.GOLD, DyeColor.ORANGE, Color.ORANGE, "orange"),
        BLACK(ChatColor.BLACK, DyeColor.BLACK, Color.BLACK, "black"), WHITE(ChatColor.WHITE, DyeColor.WHITE, Color.WHITE, "white"), CYAN(ChatColor.DARK_AQUA, DyeColor.CYAN, Color.AQUA, "cyan");

		private String name;
		private ChatColor cc;
		private DyeColor dc;
        private Color c;

		private ColorData(ChatColor cc, DyeColor dc, Color c, String name) {
			this.cc = cc;
			this.dc = dc;
            this.c = c;
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

        public Color getColor() {
            return c;
        }
	}
}

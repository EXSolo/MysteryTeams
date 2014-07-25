package com.exsoloscript.mysteryteams.data;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

public class MysteryTeam extends ArrayList<String> {

	private static final long serialVersionUID = 6713156139014235187L;
	private int id;
	private ColorData colorData;

	public MysteryTeam(int id, ColorData cd) {
		this.id = id;
		this.colorData = cd;
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

	public enum ColorData {

		RED(ChatColor.RED, DyeColor.RED), BLUE(ChatColor.BLUE, DyeColor.BLUE), GREEN(ChatColor.GREEN, DyeColor.GREEN), YELLOW(ChatColor.YELLOW, DyeColor.YELLOW), PURPLE(ChatColor.DARK_PURPLE, DyeColor.PURPLE), GRAY(ChatColor.GRAY, DyeColor.GRAY), PINK(
				ChatColor.LIGHT_PURPLE, DyeColor.PINK), ORANGE(ChatColor.GOLD, DyeColor.ORANGE), BLACK(ChatColor.BLACK, DyeColor.BLACK), WHITE(ChatColor.WHITE, DyeColor.WHITE), CYAN(ChatColor.DARK_AQUA, DyeColor.CYAN);

		private ChatColor cc;
		private DyeColor dc;

		private ColorData(ChatColor cc, DyeColor dc) {
			this.cc = cc;
			this.dc = dc;
		}
		
		public DyeColor getDyeColor() {
			return dc;
		}
		
		public ChatColor getChatColor() {
			return cc;
		}
	}
}

package com.exsoloscript.mysteryteams.cmd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;

import com.exsoloscript.mysteryteams.MysteryTeams;
import com.exsoloscript.mysteryteams.MysteryTeamsUtils;
import com.exsoloscript.mysteryteams.data.MysteryTeam;
import com.exsoloscript.mysteryteams.data.MysteryTeam.ColorData;

public class MysteryTeamsCommand implements CommandExecutor {

	private MysteryTeams plugin;

	public MysteryTeamsCommand(MysteryTeams t) {
		this.plugin = t;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (label.equalsIgnoreCase("mt")) {
			if (args.length > 0) {
				if (sender.isOp()) {
					if (args[0].equalsIgnoreCase("randomize")) {
						if (args.length > 1) {
							int teamCount = 1;

							try {
								teamCount = Integer.parseInt(args[1]);
							} catch (NumberFormatException e) {
								sender.sendMessage(this.plugin.prefix() + "Correct usage: /mt randomize <number-of-teams>");
								return true;
							}

							if (teamCount > ColorData.values().length) {
								sender.sendMessage(plugin.prefix() + "Too many teams. Plugin does not have that many colors registered.");
								return true;
							}

							List<Player> players = new ArrayList<Player>(Arrays.asList(Bukkit.getOnlinePlayers()));
							boolean useOps = true;

							if (args.length > 2) {
								useOps = Boolean.parseBoolean(args[2]);
							}

							if (!useOps) {
								List<Player> tmp = new ArrayList<Player>();
								for (Player p : players) {
									if (!p.isOp())
										tmp.add(p);
								}

								players = tmp;
							}

							Collections.shuffle(players);

							List<List<Player>> teams = MysteryTeamsUtils.split(players, teamCount);

							this.plugin.resetTeamManager();

							for (List<Player> team : teams) {
								MysteryTeam t = this.plugin.getTeamManager().newTeam();
								for (Player p : team)
									t.getPlayers().add(p.getUniqueId());
							}

							sender.sendMessage(plugin.prefix() + "Teams randomized.");
						}

					} else if (args[0].equalsIgnoreCase("clear")) {
						if (sender.isOp()) {
							this.plugin.resetTeamManager();
							sender.sendMessage(plugin.prefix() + "Teams cleared.");
						}
					} else if (args[0].equalsIgnoreCase("list")) {
						if (sender.isOp()) {
							if (this.plugin.getTeamManager().getTeams().size() > 0) {
								sender.sendMessage(plugin.prefix() + "Printing existing teams:");
								for (MysteryTeam t : this.plugin.getTeamManager().getTeams()) {
									sender.sendMessage(t.getColorData().getChatColor() + "Team " + t.getColorData().getChatColor().name().toLowerCase() + ":");
									for (UUID uuid : t.getPlayers()) {
										sender.sendMessage(" - " + (Bukkit.getOfflinePlayer(uuid) == null ? ChatColor.RED : ChatColor.GREEN) + Bukkit.getOfflinePlayer(uuid).getName());
									}
								}
							} else {
								sender.sendMessage(plugin.prefix() + "No teams defined yet.");
							}
						}
					} else if (args[0].equalsIgnoreCase("wool") || args[0].equalsIgnoreCase("wools")) {
						if (sender.isOp()) {
							for (MysteryTeam t : this.plugin.getTeamManager().getTeams()) {
								for (UUID uuid : t.getPlayers()) {
									Player p = Bukkit.getPlayer(uuid);
									ItemStack wool = new Wool(t.getColorData().getDyeColor()).toItemStack(1);
									if (p != null)
										if (p.getInventory().firstEmpty() > -1) {
											p.getInventory().addItem(wool);
										} else {
											p.getWorld().dropItem(p.getLocation(), wool);
											p.sendMessage(plugin.prefix() + "Your wool was dropped on the ground since your inventory is full!");
										}
									else
										this.plugin.getMissingPlayers().add(uuid);
								}
							}

							sender.sendMessage(plugin.prefix() + "Gave wool to online players. Will wait for offline players to join.");
						}
					}
				} else {
					sender.sendMessage(plugin.prefix() + "You have to be operator to execute this command!");
				}
				
				if (args[0].equalsIgnoreCase("version")) {
					sender.sendMessage(plugin.prefix() + "Plugin v" + plugin.getDescription().getVersion() + " created by EXSolo.");
				} else if (args[0].equalsIgnoreCase("teamsize")) {
					sender.sendMessage(plugin.prefix() + "Printing teamsize of existing teams:");
					for (MysteryTeam t : this.plugin.getTeamManager().getTeams()) {
						sender.sendMessage(" - " + t.getColorData().getChatColor() + "Team " + t.getColorData().getName() + ": " + ChatColor.RESET + t.getPlayers().size());
					}
				}
			} else {
				printHelp(sender);
			}
		}

		return false;
	}

	public void printHelp(CommandSender sender) {
		sender.sendMessage(plugin.prefix() + "MysteryTeams Command");
		sender.sendMessage(" - " + ChatColor.BLUE + "/mt randomize <number-of-teams> [use-ops]");
		sender.sendMessage(" - " + ChatColor.BLUE + "/mt clear");
		sender.sendMessage(" - " + ChatColor.BLUE + "/mt list");
		sender.sendMessage(" - " + ChatColor.BLUE + "/mt wool");
		sender.sendMessage(" - " + ChatColor.BLUE + "/mt version");
		sender.sendMessage(" - " + ChatColor.BLUE + "/mt help");
		sender.sendMessage(" - " + ChatColor.BLUE + "/mt teamsize");
	}

}

package com.exsoloscript.mysteryteams.cmd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
				if (args[0].equalsIgnoreCase("version")) {
					sender.sendMessage(plugin.prefix() + "Plugin v" + plugin.getDescription().getVersion() + " created by EXSolo.");
					return true;
				} else if (args[0].equalsIgnoreCase("teamsize")) {
					if (this.plugin.getTeamManager().getTeams().size() > 0) {
						sender.sendMessage(plugin.prefix() + "Printing teamsize of existing teams:");
						for (MysteryTeam t : this.plugin.getTeamManager().getTeams()) {
							sender.sendMessage(" - " + t.getColorData().getChatColor() + "Team " + t.getColorData().getName() + ": " + ChatColor.RESET + t.playersAlive());
						}
					} else {
						sender.sendMessage(plugin.prefix() + "No teams defined yet");
					}
					
					return true;
				}

				if (sender.hasPermission("mysteryteams.admin")) {
					if (args[0].equalsIgnoreCase("randomize")) {
						if (args.length > 1) {
							int teamCount = 1;

							try {
								teamCount = Integer.parseInt(args[1]);
							} catch (NumberFormatException e) {
								sender.sendMessage(this.plugin.prefix() + "Correct usage: /mt randomize <number-of-teams> [use-ops]");
								return true;
							}

							if (teamCount > ColorData.values().length) {
								sender.sendMessage(plugin.prefix() + "Too many teams. Plugin does not have that many colors registered.");
								return true;
							}

							if (teamCount <= 0) {
								sender.sendMessage(plugin.prefix() + "Number of teams must be positive.");
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
									if (!p.hasPermission("mysteryteams.admin"))
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
									t.addPlayer(p);
							}

							Bukkit.broadcastMessage(plugin.prefix() + "Mysteryteams randomized.");
						} else
							sender.sendMessage(plugin.prefix() + "Correct usage: /mt randomize <number-of-teams> [use-ops]");

					} else if (args[0].equalsIgnoreCase("clear")) {
						if (sender.hasPermission("mysteryteams.admin")) {
							this.plugin.resetTeamManager();
							sender.sendMessage(plugin.prefix() + "Teams cleared.");
						}
					} else if (args[0].equalsIgnoreCase("list")) {
						if (this.plugin.getTeamManager().getTeams().size() > 0) {
							sender.sendMessage(plugin.prefix() + "Printing existing teams:");
							sender.sendMessage(plugin.prefix() + ChatColor.DARK_PURPLE + "purple" + ChatColor.RESET + " = alive, offline");
							sender.sendMessage(plugin.prefix() + ChatColor.GREEN + "green" + ChatColor.RESET + " = alive");
							sender.sendMessage(plugin.prefix() + ChatColor.RED + "red" + ChatColor.RESET + " = dead");
							for (MysteryTeam t : this.plugin.getTeamManager().getTeams()) {
								sender.sendMessage(t.getColorData().getChatColor() + "Team " + t.getColorData().getName() + ChatColor.RESET + " (" + t.getId() + "): ");
								for (Entry<UUID, Boolean> e : t.getPlayers().entrySet()) {
									sender.sendMessage(" - " + (e.getValue() ? (!Bukkit.getOfflinePlayer(e.getKey()).isOnline() ? ChatColor.DARK_PURPLE : ChatColor.GREEN) : ChatColor.RED) + Bukkit.getOfflinePlayer(e.getKey()).getName());
								}
							}
						} else {
							sender.sendMessage(plugin.prefix() + "No teams defined yet.");
						}
					} else if (args[0].equalsIgnoreCase("wool") || args[0].equalsIgnoreCase("wools")) {
						for (MysteryTeam t : this.plugin.getTeamManager().getTeams()) {
							for (Entry<UUID, Boolean> e : t.getPlayers().entrySet()) {
								if (e.getValue()) {
									this.plugin.giveWool(e.getKey(), t.getColorData());
								}
							}
						}

						Bukkit.broadcastMessage(plugin.prefix() + "Wool was given to online players");
						sender.sendMessage(plugin.prefix() + "Gave wool to online players. Will wait for offline players to join.");
					} else if (args[0].equalsIgnoreCase("add")) {
						if (args.length > 2) {
							MysteryTeam t = null;
							MysteryTeam old = null;
							@SuppressWarnings("deprecation")
							OfflinePlayer p = Bukkit.getOfflinePlayer(args[2]);

							if (p != null) {
								old = plugin.getTeamManager().getByPlayer(p);

								try {
									t = plugin.getTeamManager().getByID(Integer.parseInt(args[1]));
								} catch (NumberFormatException e) {
								}

								if (old == null) {
									if (t != null) {
										t.addPlayer(p);
										sender.sendMessage(plugin.prefix() + "Player was added to team " + t.getId());
									} else {
										sender.sendMessage(plugin.prefix() + "Team with the id '" + args[1] + "' is unknown.");
									}
								} else {
									sender.sendMessage(plugin.prefix() + "Player is in a team. You have to remove him first.");
								}
							} else {
								sender.sendMessage(plugin.prefix() + "The player you specified is unknown.");
							}
						} else {
							sender.sendMessage(plugin.prefix() + "Correct usage: /mt add <teamId> <player>");
						}
					} else if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("rem")) {
						if (args.length > 1) {
							@SuppressWarnings("deprecation")
							OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
							MysteryTeam t = this.plugin.getTeamManager().getByPlayer(p);
							if (t != null) {
								t.removePlayer(p);
								sender.sendMessage(plugin.prefix() + "Player was removed from team " + t.getColorData().getName());
							} else {
								sender.sendMessage(plugin.prefix() + "Player '" + args[1] + "' is not in a team.");
							}
						} else {
							sender.sendMessage(plugin.prefix() + "Correct usage: /mt remove <player>");
						}
					} else if (args[0].equalsIgnoreCase("team") || args[0].equalsIgnoreCase("teams")) {
						if (args.length > 1) {
							if (args[1].equalsIgnoreCase("create")) {
								MysteryTeam t = this.plugin.getTeamManager().newTeam();
								if (t != null) {
									sender.sendMessage(plugin.prefix() + "Team with the id " + t.getId() + " was created");
								} else
									sender.sendMessage(plugin.prefix() + "Team could not be created, all colours are in use");
							} else if (args[1].equalsIgnoreCase("remove") || args[1].equalsIgnoreCase("rem")) {
								if (args.length > 2) {
									int id = 0;
									try {
										id = Integer.parseInt(args[2]);
									} catch (NumberFormatException e) {
										sender.sendMessage(plugin.prefix() + "Team ID is not a number");
										return true;
									}

									MysteryTeam t = this.plugin.getTeamManager().getByID(id);

									if (t != null) {
										this.plugin.getTeamManager().removeTeam(t);
										sender.sendMessage(plugin.prefix() + "Team removed");
									} else {
										sender.sendMessage(plugin.prefix() + "There is no team with the specified ID");
									}
								} else
									sender.sendMessage(plugin.prefix() + "Correct usage: /mt team remove <teamId>");
							} else {
								sender.sendMessage(plugin.prefix() + "Correct usage: /mt team <create;remove>");
							}
						} else
							sender.sendMessage(plugin.prefix() + "Correct usage: /mt team <create;remove>");
					} else {
						printHelp(sender);
					}
				} else {
					sender.sendMessage(plugin.prefix() + "You don't have permission to execute this command!");
				}
			} else {
				printHelp(sender);
			}
		}

		return false;
	}

	public void printHelp(CommandSender sender) {
		List<String> helppage = new ArrayList<String>();

		if (sender.hasPermission("mysteryteams.admin")) {
			helppage.add(" - " + ChatColor.BLUE + "/mt randomize <number-of-teams> [use-ops]");
			helppage.add(" - " + ChatColor.BLUE + "/mt clear");
			helppage.add(" - " + ChatColor.BLUE + "/mt list");
			helppage.add(" - " + ChatColor.BLUE + "/mt wool");
			helppage.add(" - " + ChatColor.BLUE + "/mt add <teamId> <player>");
			helppage.add(" - " + ChatColor.BLUE + "/mt remove <player>");
			helppage.add(" - " + ChatColor.BLUE + "/mt team <create;remove>");
		}

		helppage.add(" - " + ChatColor.BLUE + "/mt version");
		helppage.add(" - " + ChatColor.BLUE + "/mt teamsize");

		sender.sendMessage(plugin.prefix() + "MysteryTeams Command");

		for (String s : helppage)
			sender.sendMessage(s);
	}

}

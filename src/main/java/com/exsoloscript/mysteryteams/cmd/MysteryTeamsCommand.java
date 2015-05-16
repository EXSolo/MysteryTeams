package com.exsoloscript.mysteryteams.cmd;

import com.exsoloscript.mysteryteams.MysteryTeamsPlugin;
import com.exsoloscript.mysteryteams.module.GameModule;
import com.exsoloscript.mysteryteams.module.TeamBasedModule;
import com.exsoloscript.mysteryteams.team.MysteryTeam.ColorData;
import com.exsoloscript.mysteryteams.util.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.exsoloscript.mysteryteams.MysteryTeamsPlugin.prefix;

public class MysteryTeamsCommand implements CommandExecutor {

    private MysteryTeamsPlugin plugin;

    public MysteryTeamsCommand(MysteryTeamsPlugin t) {
        this.plugin = t;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (label.equalsIgnoreCase("mt")) {
            TeamBasedModule module;
            GameModule gm = this.plugin.getModuleManager().getCurrentModule();
            if (gm instanceof TeamBasedModule) {
                module = (TeamBasedModule) gm;
            } else {
                sender.sendMessage(prefix() + "An invalid module was loaded. Can't execute any commands!");
                return true;
            }

            if (args.length > 0) {
                if (sender.hasPermission("mysteryteams.admin")) {
                    if (args[0].equalsIgnoreCase("randomize")) {
                        if (args.length > 1) {
                            int teamCount = 1;

                            try {
                                teamCount = Integer.parseInt(args[1]);
                            } catch (NumberFormatException e) {
                                sender.sendMessage(prefix() + "Correct usage: /mt randomize <number-of-teams>");
                                return true;
                            }

                            if (teamCount > ColorData.values().length) {
                                sender.sendMessage(prefix() + "Too many teams. Plugin does not have that many colors registered.");
                                return true;
                            }

                            List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
                            boolean useOps = true;

                            if (args.length > 2) {
                                useOps = Boolean.parseBoolean(args[2]);
                            }

                            if (!useOps) {
                                players = PlayerUtils.getOnlinePlayersWithoutOps();
                            }

                            module.getTeamManager().assignTeams(players, teamCount);

                            sender.sendMessage(prefix() + "Teams randomized.");
                        } else {
                            sender.sendMessage(prefix() + "Correct usage: /mt randomize <number-of-teams>");
                        }
                    } else if (args[0].equalsIgnoreCase("clear")) {
                        module.getTeamManager().reset();
                        sender.sendMessage(prefix() + "Teams cleared.");
                    } else if (args[0].equalsIgnoreCase("list")) {
                        sender.sendMessage(module.getTeamManager().toString());
                    } else if (args[0].equalsIgnoreCase("give")) {
                        module.getTeamManager().giveItems(module);
                        sender.sendMessage(prefix() + "Gave items to online players. Will wait for offline players to join.");
                    } else if (args[0].equalsIgnoreCase("mode")) {
                        if (args.length > 1) {
                            if (args[1].equalsIgnoreCase("list")) {
                                sender.sendMessage(prefix() + "Listing loaded modes");
                                for (GameModule m : this.plugin.getModuleManager().getRegisteredModules()) {
                                    sender.sendMessage(" - " + m.getName());
                                }
                            } else {
                                GameModule newModule = this.plugin.getModuleManager().getModule(args[1]);

                                if (newModule != null) {
                                    this.plugin.getModuleManager().enableModule(newModule);
                                    sender.sendMessage(prefix() + "Mode '" + newModule.getName() + "' was enabled!");
                                } else {
                                    sender.sendMessage(prefix() + "Mode '" + args[1] + "' is unknown");
                                }

                                return true;
                            }
                        } else {
                            sender.sendMessage(prefix() + "Correct usage: /mt mode <mode-name;list>");
                        }
                    }
                } else {
                    sender.sendMessage(prefix() + "You have no permission to execute this command!");
                }

                if (args[0].equalsIgnoreCase("version")) {
                    sender.sendMessage(prefix() + "Plugin v" + plugin.getDescription().getVersion() + " created by EXSolo.");
                } else if (args[0].equalsIgnoreCase("teamsize")) {
                    sender.sendMessage(prefix() + "Printing teamsize of existing teams:");
                    sender.sendMessage(module.getTeamManager().getTeamSizes());
                }
            } else {
                printHelp(sender);
            }
        }

        return false;
    }

    public void printHelp(CommandSender sender) {
        sender.sendMessage(prefix() + "MysteryTeams Command");
        sender.sendMessage(" - " + ChatColor.BLUE + "/mt randomize <number-of-teams> [use-ops]");
        sender.sendMessage(" - " + ChatColor.BLUE + "/mt mode <list;mode-name>");
        sender.sendMessage(" - " + ChatColor.BLUE + "/mt clear");
        sender.sendMessage(" - " + ChatColor.BLUE + "/mt list");
        sender.sendMessage(" - " + ChatColor.BLUE + "/mt give");
        sender.sendMessage(" - " + ChatColor.BLUE + "/mt version");
        sender.sendMessage(" - " + ChatColor.BLUE + "/mt teamsize");
    }

}

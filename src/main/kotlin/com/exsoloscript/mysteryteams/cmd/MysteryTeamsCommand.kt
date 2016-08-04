package com.exsoloscript.mysteryteams.cmd

import com.exsoloscript.mysteryteams.MysteryTeamsPlugin
import com.exsoloscript.mysteryteams.MysteryTeamsPlugin.Companion.prefix
import com.exsoloscript.mysteryteams.module.TeamBasedModule
import com.exsoloscript.mysteryteams.team.MysteryTeam.ColorData
import com.exsoloscript.mysteryteams.util.PlayerUtils
import org.apache.commons.lang3.StringUtils
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class MysteryTeamsCommand(private val plugin: MysteryTeamsPlugin) : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {

        if (label.equals("mt", ignoreCase = true)) {
            val module: TeamBasedModule
            val current = this.plugin.moduleManager.currentModule
            if (current is TeamBasedModule) {
                module = current
            } else {
                sender.sendMessage(prefix() + "An invalid module was loaded. Can't execute any commands!")
                return true
            }

            if (args.size > 0) {
                if (args[0].equals("version", ignoreCase = true)) {
                    sender.sendMessage(prefix() + "Plugin v" + plugin.description.version + " created by EXSolo.")
                    return true
                } else if (args[0].equals("teamsize", ignoreCase = true)) {
                    sender.sendMessage(module.teamManager.stringifyTeamSizes())
                    return true
                }

                if (sender.hasPermission("mysteryteams.admin")) {
                    if (args[0].equals("randomize", ignoreCase = true)) {
                        if (args.size > 1) {
                            var teamCount = 0

                            try {
                                teamCount = Integer.parseInt(args[1])
                            } catch (e: NumberFormatException) {
                                sender.sendMessage(prefix() + "Correct usage: /mt randomize <number-of-teams>")
                                return true
                            }

                            if (teamCount > ColorData.values().size) {
                                sender.sendMessage(prefix() + "Too many teams. Plugin does not have that many colors registered.")
                                return true
                            }

                            var players: List<Player> = ArrayList(Bukkit.getOnlinePlayers())
                            var useOps = true

                            if (args.size > 2) {
                                useOps = java.lang.Boolean.parseBoolean(args[2])
                            }

                            if (!useOps) {
                                players = PlayerUtils.onlinePlayersWithoutOps
                            }

                            module.teamManager.assignTeams(players, teamCount)

                            sender.sendMessage(prefix() + "Teams randomized.")
                        } else {
                            sender.sendMessage(prefix() + "Correct usage: /mt randomize <number-of-teams>")
                        }
                    } else if (args[0].equals("clear", ignoreCase = true)) {
                        module.teamManager.reset()
                        sender.sendMessage(prefix() + "Teams cleared.")
                    } else if (args[0].equals("list", ignoreCase = true)) {
                        sender.sendMessage(module.teamManager.toString())
                    } else if (args[0].equals("give", ignoreCase = true)) {
                        if (args.size > 1) {
                            val player = Bukkit.getPlayer(args[1])
                            if (player != null) {
                                module.teamManager.giveItem(player)
                                sender.sendMessage(prefix() + "Gave item to player.")
                            } else {
                                sender.sendMessage(prefix() + "Player '" + args[1] + "' is not online")
                            }
                        } else {
                            module.teamManager.giveItems()
                            sender.sendMessage(prefix() + "Gave items to online players. Will wait for offline players to join.")
                        }
                    } else if (args[0].equals("mode", ignoreCase = true)) {
                        if (args.size > 1) {
                            if (args[1].equals("list", ignoreCase = true)) {
                                sender.sendMessage(prefix() + "Listing loaded modes:")
                                for (m in this.plugin.moduleManager.registeredModules) {
                                    sender.sendMessage(" - " + m.name)
                                }
                            } else {
                                val newModule = this.plugin.moduleManager.getModule(args[1])

                                if (newModule != null) {
                                    this.plugin.moduleManager.enableModule(newModule)
                                    sender.sendMessage(prefix() + "Mode '" + newModule.name + "' was enabled!")
                                } else {
                                    sender.sendMessage(prefix() + "Mode '" + args[1] + "' is unknown")
                                }
                                return true
                            }
                        } else {
                            sender.sendMessage(prefix() + "Correct usage: /mt mode <mode-name;list>")
                        }
                    } else if (args[0].equals("join", ignoreCase = true)) {
                        if (args.size > 2) {
                            if (StringUtils.isNumeric(args[1])) {
                                val team = module.teamManager.getByID(Integer.parseInt(args[1]))
                                val player = Bukkit.getPlayer(args[2])

                                if (player != null) {
                                    team!!.players.add(player.uniqueId)
                                    sender.sendMessage(player.name + " was added to team " + team.colorData.chatColor + team.colorData.name)
                                } else {
                                    sender.sendMessage(prefix() + "Player '" + args[2] + "' is not online")
                                }
                            } else {
                                sender.sendMessage(prefix() + args[1] + " is not a number")
                            }
                        } else {
                            sender.sendMessage(prefix() + "Correct usage /mt join <team-id> <player>")
                        }
                    } else if (args[0].equals("leave", ignoreCase = true)) {
                        if (args.size > 1) {
                            val player = Bukkit.getOfflinePlayer(args[1])
                            if (player != null) {
                                val team = module.teamManager.getByPlayer(player)
                                team!!.players.remove(player.uniqueId)
                                sender.sendMessage(player.name + " was removed from team " + team.colorData.chatColor + team.colorData.name)
                            } else {
                                sender.sendMessage(prefix() + "Player '" + args[1] + "' does not exist")
                            }
                        } else {
                            sender.sendMessage(prefix() + "Correct usage /mt leave <player>")
                        }
                    } else {
                        sender.sendMessage(prefix() + "Unknown argument.")
                    }
                } else {
                    sender.sendMessage(prefix() + "You have no permission to execute this command!")
                }
            } else {
                printHelp(sender)
            }
        }

        return false
    }

    fun printHelp(sender: CommandSender) {
        sender.sendMessage(prefix() + "MysteryTeams Command")
        sender.sendMessage(" - " + ChatColor.BLUE + "/mt randomize <number-of-teams> [use-ops]")
        sender.sendMessage(" - " + ChatColor.BLUE + "/mt mode <list;mode-name>")
        sender.sendMessage(" - " + ChatColor.BLUE + "/mt clear")
        sender.sendMessage(" - " + ChatColor.BLUE + "/mt list")
        sender.sendMessage(" - " + ChatColor.BLUE + "/mt give [player]")
        sender.sendMessage(" - " + ChatColor.BLUE + "/mt join <team-id> <player>")
        sender.sendMessage(" - " + ChatColor.BLUE + "/mt leave <player>")
        sender.sendMessage(" - " + ChatColor.BLUE + "/mt version")
        sender.sendMessage(" - " + ChatColor.BLUE + "/mt teamsize")
    }

}

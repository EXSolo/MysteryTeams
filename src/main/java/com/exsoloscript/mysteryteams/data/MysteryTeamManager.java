package com.exsoloscript.mysteryteams.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.OfflinePlayer;

import com.exsoloscript.mysteryteams.MysteryTeams;
import com.exsoloscript.mysteryteams.data.MysteryTeam.ColorData;

public class MysteryTeamManager {

	private MysteryTeams plugin;
	private List<MysteryTeam> teams;
	
	public MysteryTeamManager(MysteryTeams t) {
		this.plugin = t;
		this.teams = new ArrayList<MysteryTeam>();
	}
	
	public MysteryTeam newTeam() {
		ColorData cd = null;

		if (this.plugin.getAvailableColors().size() > 0) {
			cd = this.plugin.getAvailableColors().get(0);
			this.plugin.getAvailableColors().remove(0);
		} else
			return null;

		int id = 1;
		
		while (getByID(id) != null)
			id++;
		
		MysteryTeam mt = new MysteryTeam(id, cd);
		
		this.teams.add(mt);
		return mt;
	}
	
	public void removeTeam(MysteryTeam t) {
		this.plugin.getAvailableColors().add(t.getColorData());
		this.teams.remove(t);
	}

	public MysteryTeam getByPlayer(OfflinePlayer p) {
		for (MysteryTeam t : this.teams)
			for (UUID uuid : t.getPlayers().keySet())
				if (uuid.equals(p.getUniqueId()))
					return t;
		return null;
	}

	public MysteryTeam getByID(int id) {
		for (MysteryTeam t : this.teams)
			if (t.getId() == id)
				return t;
		return null;
	}
	
	public List<MysteryTeam> getTeams() {
		return this.teams;
	}
	
	public List<MysteryTeam> getTeamsWithAlivePlayers() {
		List<MysteryTeam> teams = new ArrayList<MysteryTeam>();
		
		for (MysteryTeam t : this.teams) {
			if (t.hasPlayersAlive())
				teams.add(t);
		}
		
		return teams;
	}

}

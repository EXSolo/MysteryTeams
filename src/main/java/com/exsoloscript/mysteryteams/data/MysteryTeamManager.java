package com.exsoloscript.mysteryteams.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

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

		MysteryTeam mt = new MysteryTeam(this.teams.size() + 1, cd);
		
		this.teams.add(mt);
		return mt;
	}

	public MysteryTeam getByPlayer(Player p) {
		for (MysteryTeam t : this.teams)
			for (UUID uuid : t.getPlayers())
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

}

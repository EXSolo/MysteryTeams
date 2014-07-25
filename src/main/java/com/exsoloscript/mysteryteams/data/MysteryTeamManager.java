package com.exsoloscript.mysteryteams.data;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.exsoloscript.mysteryteams.MysteryTeams;
import com.exsoloscript.mysteryteams.data.MysteryTeam.ColorData;

public class MysteryTeamManager extends ArrayList<MysteryTeam> {

	private static final long serialVersionUID = -3976088698306580495L;

	private MysteryTeams plugin;
	
	public MysteryTeamManager(MysteryTeams t) {
		this.plugin = t;
	}
	
	public MysteryTeam newTeam() {
		ColorData cd = null;

		if (this.plugin.getAvailableColors().size() > 0) {
			cd = this.plugin.getAvailableColors().get(0);
			this.plugin.getAvailableColors().remove(0);
		} else
			return null;

		MysteryTeam mt = new MysteryTeam(size() + 1, cd);
		
		this.add(mt);
		return mt;
	}

	public MysteryTeam getByPlayer(Player p) {
		for (MysteryTeam t : this)
			for (String name : t)
				if (name.equals(p.getName()))
					return t;
		return null;
	}

	public MysteryTeam getByID(int id) {
		for (MysteryTeam t : this)
			if (t.getId() == id)
				return t;
		return null;
	}

}

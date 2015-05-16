package com.exsoloscript.mysteryteams.util;

import com.exsoloscript.mysteryteams.team.MysteryTeam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Colors {
    private List<MysteryTeam.ColorData> availableColors;

    public Colors() {
        reset();
    }

    public MysteryTeam.ColorData getNext() {
        if (availableColors.size() > 0) {
            return availableColors.remove(0);
        }

        return null;
    }

    public void reset() {
        this.availableColors = new ArrayList<>(Arrays.asList(MysteryTeam.ColorData.values()));
    }

}

package com.exsoloscript.mysteryteams;

import java.util.ArrayList;
import java.util.List;

public class MysteryTeamsUtils {

	public static <T> List<List<T>> split(List<T> toSplit, int howOften) {
		List<List<T>> list = new ArrayList<List<T>>(howOften);
		for (int i = 0; i < howOften; i++) {
			list.add(new ArrayList<T>());
		}

		int i = 0;

		for (T t : toSplit) {
			list.get(i).add(t);
			i = (i + 1) % howOften;
		}

		return list;
	}
	
}

package pt.technic.apps.minesfinder.entity;

import java.util.ArrayList;
import java.util.List;

import pt.technic.apps.minesfinder.util.RecordManager;

public class PlayerCache {
	
	
	int newPlayerRanking = 99;
	private ArrayList<Player> easy = new ArrayList<>();
	private ArrayList<Player> med = new ArrayList<>();
	private ArrayList<Player> hard = new ArrayList<>();

	private PlayerCache() {
	}

	public static PlayerCache getInstance() {
		return LazyHolder.instance;
	}

	public ArrayList<Player> getListByLevel(Mode mode) {
		if (mode == Mode.EASY)
			return easy;
		if (mode == Mode.MED)
			return med;
		if (mode == Mode.HARD)
			return hard;
		return null;
	}

	public ArrayList<Player> setListByLevel(Mode mode, ArrayList<Player> list) {
		if (mode == Mode.EASY)
			this.easy = list;
		if (mode == Mode.MED)
			this.med = list;
		if (mode == Mode.HARD)
			this.hard = list;
		return null;
	}

	public int recordAt(Player player, List<Player> list) {
		int rank = -1;

		for (int i = 0; i < list.size(); i++) {
			if (player.getScore() < list.get(i).getScore()) {
				rank = i + 1;
			}
		}

		return rank;
	}
	
	static class LazyHolder {
		static final PlayerCache instance = new PlayerCache();
	}
}

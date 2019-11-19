package pt.technic.apps.minesfinder.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.StringJoiner;

import pt.technic.apps.minesfinder.Main;
import pt.technic.apps.minesfinder.entity.Mode;
import pt.technic.apps.minesfinder.entity.Player;
import pt.technic.apps.minesfinder.entity.PlayerCache;

public class RecordManager {

	public static String fileName = Main.FILE_NAME;

	private RecordManager() {
	}

	public static RecordManager getInstance() {
		return LazyHolder.instance;
	}

	private ArrayList<String[]> readFile() {
		File f = new File(fileName);
		ArrayList<String[]> records = new ArrayList<>();

		if (f.canRead()) {
			try (FileReader reader = new FileReader(f); BufferedReader buff = new BufferedReader(reader);) {

				String[] information = new String[2];
				String line = "";
				for (int i = 1; (line = buff.readLine()) != null; i++) {
					if ((i + 1) % 3 == 0) {
						// read name
						information[0] = line;
					}

					if (i % 3 == 0) {
						// read score
						information[1] = line;
						records.add(information);
						information = new String[2];
					}
				}

			} catch (Throwable t) {
				t.printStackTrace();
			}
		} else {
			writeFile();
			readFile();
		}
		return records;
	}

	public void readGameRecords() {

		ArrayList<String[]> records = this.readFile();

		for (int i = 0; i < records.size(); i++) {
			String[] names = records.get(i)[0].split(",");
			String[] scores = records.get(i)[1].split(",");
			ArrayList<Player> playerList = new ArrayList<Player>();

			try {

				for (int j = 0; j < names.length; j++) {
					String name = names[j];
					String score = scores[j];
					Player player = new Player();
					player.setRecord(name, Long.parseLong(score));
					playerList.add(player);
				}
			} catch (Exception e) {

			}
			if (i == 0)
				PlayerCache.getInstance().setListByLevel(Mode.EASY, playerList);
			else if (i == 1)
				PlayerCache.getInstance().setListByLevel(Mode.MED, playerList);
			else if (i == 2)
				PlayerCache.getInstance().setListByLevel(Mode.HARD, playerList);
		}

	}

	private String makeFileContents(ArrayList<Player> list) {
		String name = "";
		String score = "";

		StringJoiner nameJoiner = new StringJoiner(",");
		StringJoiner scoreJoiner = new StringJoiner(",");

		for (Player p : list) {
			nameJoiner.add(p.getName());
			scoreJoiner.add(String.valueOf(p.getScore()));
		}
		name = nameJoiner.toString() + "\n";
		score = scoreJoiner.toString() + "\n";

		return name + score;
	}

	public void writeFile() {
		File f = new File(fileName);
		try (FileWriter writer = new FileWriter(f)) {

			String easy = makeFileContents(PlayerCache.getInstance().getListByLevel(Mode.EASY));
			String med = makeFileContents(PlayerCache.getInstance().getListByLevel(Mode.MED));
			String hard = makeFileContents(PlayerCache.getInstance().getListByLevel(Mode.HARD));
			String contents = "EASY\n" + easy + "MED\n" + med + "HARD\n" + hard;
			writer.write(contents);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static class LazyHolder {
		// the best idom of singleton pattern
		static final RecordManager instance = new RecordManager();
	}
}

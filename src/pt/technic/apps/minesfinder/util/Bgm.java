package pt.technic.apps.minesfinder.util;

import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class Bgm extends Thread {
	private Player player;
	private boolean is;
	private File file;
	private FileInputStream file2;
	private BufferedInputStream file3;

	public Bgm(String name, boolean is) {
		try {
			this.is = is;
			file = new File(name);
			file2 = new FileInputStream(file.getCanonicalFile());
			file3 = new BufferedInputStream(file2);
			player = new Player(file3);
		} catch (Exception e) {
		}
	}

	public void close() {
		is = false;
		player.close();
		this.interrupt();

	}
	@Override
	public void run() {
		try {
			do {
				file2 = new FileInputStream(file);
				file3 = new BufferedInputStream(file2);
				player = new Player(file3);
				player.play();
			} while (is);
		} catch (Exception e) {

		}
	}
}

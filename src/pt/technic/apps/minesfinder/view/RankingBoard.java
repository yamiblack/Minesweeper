package pt.technic.apps.minesfinder.view;

import pt.technic.apps.minesfinder.entity.Mode;
import pt.technic.apps.minesfinder.entity.Player;
import pt.technic.apps.minesfinder.entity.PlayerCache;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RankingBoard extends JFrame {
	public RankingBoard() {
		int maxRankingShow = 20;

		setTitle("Ranking Board");

		GridLayout gameGrid = new GridLayout(21, 4);
		gameGrid.setVgap(5);

		Container gameContainer = getContentPane();
		gameContainer.setLayout(gameGrid);

		gameContainer.add(new JLabel("Ranking"));
		gameContainer.add(new JLabel("Easy"));
		gameContainer.add(new JLabel("Mdeium"));
		gameContainer.add(new JLabel("Hard"));

		ArrayList<Player> easy = PlayerCache.getInstance().getListByLevel(Mode.EASY);
		ArrayList<Player> med = PlayerCache.getInstance().getListByLevel(Mode.MED);
		ArrayList<Player> hard = PlayerCache.getInstance().getListByLevel(Mode.HARD);

		for (int i = 0; i < maxRankingShow; i++) {
			gameContainer.add(new JLabel(String.valueOf(i + 1)));
			if (easy.size() > i) {
				gameContainer.add(new JLabel(easy.get(i).getName() + " : " + easy.get(i).getScore()));
			} else {
				gameContainer.add(new JLabel(""));
			}
			if (med.size() > i) {
				gameContainer.add(new JLabel(med.get(i).getName() + " : " + med.get(i).getScore()));
			} else {
				gameContainer.add(new JLabel(""));
			}
			if (hard.size() > i) {
				gameContainer.add(new JLabel(hard.get(i).getName() + " : " + hard.get(i).getScore()));
			} else {
				gameContainer.add(new JLabel(""));
			}
		}

		setSize(1300, 500);
		setResizable(false);
		setVisible(true);
	}

}

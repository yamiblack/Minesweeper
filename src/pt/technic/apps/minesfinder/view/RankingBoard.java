package pt.technic.apps.minesfinder.view;

import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;

import pt.technic.apps.minesfinder.entity.Mode;
import pt.technic.apps.minesfinder.entity.Player;
import pt.technic.apps.minesfinder.entity.PlayerCache;

public class RankingBoard extends JFrame {
	public RankingBoard() {
		int maxRankingShow = 20;

		setTitle("Ranking Board");

		GridLayout grid = new GridLayout(21, 4);
		grid.setVgap(5);

		Container c = getContentPane();
		c.setLayout(grid);

		c.add(new JLabel("Ranking"));
		c.add(new JLabel("Easy"));
		c.add(new JLabel("Mdeium"));
		c.add(new JLabel("Hard"));

		ArrayList<Player> easy = PlayerCache.getInstance().getListByLevel(Mode.EASY);
		ArrayList<Player> med = PlayerCache.getInstance().getListByLevel(Mode.MED);
		ArrayList<Player> hard = PlayerCache.getInstance().getListByLevel(Mode.HARD);

		for (int i = 0; i < maxRankingShow; i++) {
			c.add(new JLabel(String.valueOf(i + 1)));
			if (easy.size() > i) {
				c.add(new JLabel(easy.get(i).getName() + " : " + easy.get(i).getScore()));
			} else {
				c.add(new JLabel(""));
			}
			if (med.size() > i) {
				c.add(new JLabel(med.get(i).getName() + " : " + med.get(i).getScore()));
			} else {
				c.add(new JLabel(""));
			}
			if (hard.size() > i) {
				c.add(new JLabel(hard.get(i).getName() + " : " + hard.get(i).getScore()));
			} else {
				c.add(new JLabel(""));
			}
		}

		setSize(1300, 500);
		setResizable(false);
		setVisible(true);
	}

}

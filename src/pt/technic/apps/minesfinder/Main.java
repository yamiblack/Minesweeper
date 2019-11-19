package pt.technic.apps.minesfinder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import pt.technic.apps.minesfinder.entity.Mode;
import pt.technic.apps.minesfinder.util.RecordManager;
import pt.technic.apps.minesfinder.view.BattleMode;
import pt.technic.apps.minesfinder.view.GameWindow;
import pt.technic.apps.minesfinder.view.Minefield;
import pt.technic.apps.minesfinder.view.RankingBoard;

/**
 *
 * @author Gabriel Massadas
 */
public class Main extends JFrame {

	public static final String FILE_NAME = System.getProperty("user.home") + File.separator + "minesfinder.txt";
	// 파일 경로를 명시

	public static void main(String args[]) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {

		UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		EventQueue.invokeLater(() -> new Main().setVisible(true));
	}

	private JLabel Records = new JLabel();
	private JLabel panelTitle = new JLabel();
	private JPanel panelBtns = new JPanel();
	private JPanel panelRecords = new JPanel();

	public Main() {
		initComponents();
		RecordManager.getInstance().readGameRecords();
	}

	private void setBtn(String name, ActionListener toRun, JPanel panel, boolean isImage) {
		JButton btn = new JButton();
		if (isImage) {
			String path = "/pt/technic/apps/minesfinder/resources/" + name.toLowerCase() + ".png";
			ImageIcon icon = new ImageIcon(getClass().getResource(path));
			btn.setIcon(icon);
		}
		btn.setText(name);
		btn.addActionListener(toRun);
		panel.add(btn);
	}

	private void initComponents() {
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("MinesFinder");
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		setPreferredSize(new java.awt.Dimension(600, 450));
		setResizable(false);

		panelTitle.setBackground(new Color(136, 135, 217));
		panelTitle.setFont(new Font("Ubuntu", 1, 24));
		panelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitle.setText("Minesfinder");
		panelTitle.setOpaque(true);

		getContentPane().add(panelTitle, BorderLayout.PAGE_START);
		JButton btnRankingBoard = new JButton("Ranking Board");
		getContentPane().add(btnRankingBoard, BorderLayout.WEST); // 왼쪽에 "Ranking Board" 버튼 추가
		btnRankingBoard.addActionListener(e -> new RankingBoard());
		panelBtns.setLayout(new GridLayout(2, 0));

		setBtn("Easy", e -> new GameWindow(new Minefield(9, 9, 10), Mode.EASY).setVisible(true), panelBtns, true);
		setBtn("Medium", e -> new GameWindow(new Minefield(16, 16, 40), Mode.MED).setVisible(true), panelBtns, true);
		setBtn("Hard", e -> new GameWindow(new Minefield(16, 30, 90), Mode.HARD).setVisible(true), panelBtns, true);
		setBtn("Battle", e -> new BattleMode(new Minefield(9, 9, 10), new Minefield(9, 9, 10)).setVisible(true),
				panelBtns, false);
		getContentPane().add(panelBtns, java.awt.BorderLayout.CENTER);
		pack();
	}
}

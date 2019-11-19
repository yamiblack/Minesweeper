package pt.technic.apps.minesfinder.view;

import javax.lang.model.util.AbstractAnnotationValueVisitor6;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import pt.technic.apps.minesfinder.util.Bgm;
import pt.technic.apps.minesfinder.util.ThreadPool;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

public class BattleMode extends javax.swing.JFrame {
	private ButtonMinefield[][] buttons;
	private ButtonMinefield[][] buttons2;
	private Minefield minefield;
	private Minefield minefield2;
	private JPanel MAINPanel = new JPanel();
	private JPanel mainpanel = new JPanel();
	private JPanel mainstausbar = new JPanel();
	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	private JPanel statuspanel = new JPanel();
	private JPanel statuspanel2 = new JPanel();

	private boolean gameStart = false; // 寃뚯엫�씠 �떆�옉 �릺�뿀�뒗吏� �뙋蹂�

	private Bgm battlebgm = new Bgm("mario.mp3", true);
	//private Bgm battlebgm = new Bgm(System.getProperty("user.dir") + "mario.mp3", true);
	private Bgm bgm = new Bgm("mariodie.mp3", false);

	public BattleMode() {
		initComponents();
	}

	public BattleMode(Minefield minefield, Minefield minefield2) {
		initComponents();
		gameStart = true;
		this.minefield = minefield;
		this.minefield2 = minefield2;
		initStatusBar();

		buttons = new ButtonMinefield[minefield.getWidth()][minefield.getHeight()];
		buttons2 = new ButtonMinefield[minefield2.getWidth()][minefield2.getHeight()];

		battlebgm.start();

		MAINPanel.setLayout(new FlowLayout());
		MAINPanel.setPreferredSize(new Dimension(1010, 560));

		statuspanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		statuspanel2.setLayout(new FlowLayout(FlowLayout.RIGHT));

		mainpanel.setLayout(new FlowLayout());
		mainstausbar.setLayout(new FlowLayout());

		mainstausbar.setPreferredSize(new Dimension(700, 30));
		mainpanel.setPreferredSize(new Dimension(1000, 500));

		panel1.setLayout(new GridLayout(minefield.getWidth(), minefield.getHeight()));
		panel2.setLayout(new GridLayout(minefield2.getWidth(), minefield2.getHeight()));

		KeyListener keyListener = new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				ButtonMinefield botao = (ButtonMinefield) e.getSource();
				int x = botao.getCol();
				int y = botao.getLine();

				if (e.getKeyCode() == KeyEvent.VK_W && x > 0) {
					buttons[x - 1][y].requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_A && y > 0) {
					buttons[x][y - 1].requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_S && x < minefield.getHeight() - 1) {
					buttons[x + 1][y].requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_D && y < minefield.getWidth() - 1) {
					buttons[x][y + 1].requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_F && minefield.getGridState(x, y) == minefield2.COVERED) {
					if (!minefield.isBattleDefeated()) {
						Battlebtn(x, y, 0);
						buttons2[x][y].setFocusable(true);
						buttons2[x][y].requestFocus();
						for (x = 0; x < minefield.getWidth(); x++) {
							for (y = 0; y < minefield.getHeight(); y++) {
								buttons[x][y].setFocusable(false);
							}
						}
						for (x = 0; x < minefield.getWidth(); x++) {
							for (y = 0; y < minefield.getHeight(); y++) {
								buttons2[x][y].setFocusable(true);
							}
						}
					}

				} else if (e.getKeyCode() == KeyEvent.VK_UP && x > 0) {
					buttons2[x - 1][y].requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT && y > 0) {
					buttons2[x][y - 1].requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN && x < minefield2.getHeight() - 1) {
					buttons2[x + 1][y].requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT && y < minefield2.getWidth() - 1) {
					buttons2[x][y + 1].requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER
						&& (minefield2.getGridState(x, y) == minefield2.COVERED)) {
					if (!minefield2.isBattleDefeated()) {
						Battlebtn(x, y, 1);
						buttons[x][y].setFocusable(true);
						buttons[x][y].requestFocus();
						for (x = 0; x < minefield.getWidth(); x++) {
							for (y = 0; y < minefield.getHeight(); y++) {
								buttons2[x][y].setFocusable(false);
							}
						}
						for (x = 0; x < minefield.getWidth(); x++) {
							for (y = 0; y < minefield.getHeight(); y++) {
								buttons[x][y].setFocusable(true);
							}
						}
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent ke) {
			}

			@Override
			public void keyReleased(KeyEvent ke) {
			}
		}

		;
		for (int x = 0; x < minefield.getWidth(); x++)

		{
			for (int y = 0; y < minefield.getHeight(); y++) {
				buttons[x][y] = new ButtonMinefield(x, y, 1);
				buttons2[x][y] = new ButtonMinefield(x, y, 0);
				buttons[x][y].setPreferredSize(new Dimension(55, 55));
				buttons2[x][y].setPreferredSize(new Dimension(55, 55));
				panel1.add(buttons[x][y]);
				panel2.add(buttons2[x][y]);
				buttons[x][y].addKeyListener(keyListener);
				buttons2[x][y].addKeyListener(keyListener);
			}
		}

		mainstausbar.add(statuspanel);
		mainstausbar.add(statuspanel2);

		mainpanel.add(panel1);
		mainpanel.add(panel2);

		MAINPanel.add(mainstausbar);
		MAINPanel.add(mainpanel);

		setContentPane(MAINPanel);

		pack();
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				gameStart = false;
				battlebgm.close();
			}
		});

	}

	private void initStatusBar() {
		JLabel timeLabel = new JLabel("Player1 Lifes : " + this.minefield.getnumlife() + " / Left Mines : "
				+ this.minefield.getleft() + "         " + "Player2 Lifes : " + this.minefield2.getnumlife()
				+ " / Left Mines : " + this.minefield2.getleft()); // �젅�씠釉� �깮�꽦

		ThreadPool.timeThreadPool.submit(() -> {
			while (gameStart) {
				timeLabel.setText("Player1 Lifes : " + this.minefield.getnumlife() + " / Left Mines : "
						+ this.minefield.getleft() + "         " + "Player2 Lifes : " + this.minefield2.getnumlife()
						+ " / Left Mines : " + this.minefield2.getleft()); // �젅�씠釉� �깮�꽦
			}
		});

		statuspanel.add(timeLabel);
	}

	private void Battlebtn(int x, int y, int i) {// 吏�猶� �떎 李얠쑝硫� �듅由�
		if (i == 0) {
			minefield.BattlerevealGrid(x, y);
			updateButtonsStates();
			if (minefield.isBattleFinished()) {
				gameStart = false;

				if (minefield.isBattleWin()) {
					battlebgm.close();
					JOptionPane.showMessageDialog(null, "COGRATULATIONS, WIN! Player1 Find All Mines", "WIN!",
							JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
				} else if (minefield.isBattleDefeated()) {
					battlebgm.close();
					bgm.start();
					bgm.close();
					for (x = 0; x < minefield.getWidth(); x++) {
						for (y = 0; y < minefield.getHeight(); y++) {
							buttons[x][y].setBackground(Color.RED);
							buttons[x][y].setText("END");
						}
					}
					JOptionPane.showMessageDialog(null, "Player2 Win! Player1 try harder", "Player2 win",
							JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
				}

			}
		}

		else {
			minefield2.BattlerevealGrid(x, y);
			updateButtonsStates2();

			if (minefield2.isBattleFinished()) {
				gameStart = false;
				if (minefield2.isBattleWin()) {
					battlebgm.close();
					JOptionPane.showMessageDialog(null, "COGRATULATIONS, WIN! Player2 Find All Mines", "WIN!",
							JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
				} else if (minefield2.isBattleDefeated()) {
					battlebgm.close();
					bgm.start();
					bgm.close();
					for (x = 0; x < minefield2.getWidth(); x++) {
						for (y = 0; y < minefield2.getHeight(); y++) {
							buttons2[x][y].setBackground(Color.RED);
							buttons2[x][y].setText("END");
						}
					}
					JOptionPane.showMessageDialog(null, "Player1 Win! Player2 try harder", "Player1 win",
							JOptionPane.INFORMATION_MESSAGE);

					setVisible(false);
				}
			}

		}
	}

	private void updateButtonsStates() {
		for (int x = 0; x < minefield.getWidth(); x++) {
			for (int y = 0; y < minefield.getHeight(); y++) {
				buttons[x][y].setEstado(minefield.getGridState(x, y));
			}
		}
	}

	private void updateButtonsStates2() {
		for (int x = 0; x < minefield2.getWidth(); x++) {
			for (int y = 0; y < minefield2.getHeight(); y++) {
				buttons2[x][y].setEstado(minefield2.getGridState(x, y));
			}
		}
	}

	private void initComponents() {
		gameStart = true;
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Game");
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(mainpanel);
		mainpanel.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 1094, Short.MAX_VALUE)

		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 553, Short.MAX_VALUE)

		);

		pack();
	}
}
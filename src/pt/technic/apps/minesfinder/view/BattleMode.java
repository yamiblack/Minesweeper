package pt.technic.apps.minesfinder.view;

import javax.swing.*;

import pt.technic.apps.minesfinder.util.Bgm;
import pt.technic.apps.minesfinder.util.ThreadPool;

import java.awt.*;
import java.awt.event.*;

public class BattleMode extends javax.swing.JFrame {
	private ButtonMinefield[][] buttons1p;
	private ButtonMinefield[][] buttons2p;

	private Minefield minefield1p;
	private Minefield minefield2p;

	private JPanel gameWindow = new JPanel();
	private JPanel userPanel = new JPanel();
	private JPanel statusBar = new JPanel();
	private JPanel panel1p = new JPanel();
	private JPanel panel2p = new JPanel();
	private JPanel status1p = new JPanel();
	private JPanel status2p = new JPanel();

	private boolean gameStart = false;

	private Bgm battleBgm = new Bgm("mario.mp3", true);
	private Bgm dieEffect = new Bgm("mariodie.mp3", false);

	public BattleMode() {
		initComponents();
	}

	public BattleMode(Minefield minefield1p, Minefield minefield2p) {
		initComponents();
		gameStart = true;
		this.minefield1p = minefield1p;
		this.minefield2p = minefield2p;
		initStatusBar();

		battleBgm.start();

		buttons1p = new ButtonMinefield[minefield1p.getWidth()][minefield1p.getHeight()];
		buttons2p = new ButtonMinefield[minefield2p.getWidth()][minefield2p.getHeight()];

		KeyListener keyListener = new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				ButtonMinefield botao = (ButtonMinefield) e.getSource();
				int x = botao.getCol();
				int y = botao.getLine();
				int keyCode = e.getKeyCode();

				switch (keyCode) {
					case KeyEvent.VK_W:
						if (x > 0)
							buttons1p[x - 1][y].requestFocus();
						break;

					case KeyEvent.VK_A:
						if (y > 0)
							buttons1p[x][y - 1].requestFocus();
						break;

					case KeyEvent.VK_S:
						if (x < minefield1p.getHeight() - 1)
							buttons1p[x + 1][y].requestFocus();
						break;

					case KeyEvent.VK_D:
						if (y < minefield1p.getWidth() - 1)
							buttons1p[x][y + 1].requestFocus();
						break;

					case KeyEvent.VK_F:
						if (e.getKeyCode() == KeyEvent.VK_F && (minefield1p.getGridState(x, y) == Minefield.COVERED) && !minefield1p.isBattleDefeated()) {
							battleBtn(x, y, 0);
							buttons2p[x][y].setFocusable(true);
							buttons2p[x][y].requestFocus();
							for (x = 0; x < minefield1p.getWidth(); x++) {
								for (y = 0; y < minefield1p.getHeight(); y++) {
									buttons1p[x][y].setFocusable(false);
								}
							}
							for (x = 0; x < minefield1p.getWidth(); x++) {
								for (y = 0; y < minefield1p.getHeight(); y++) {
									buttons2p[x][y].setFocusable(true);
								}
							}
						}
						break;
					case KeyEvent.VK_UP:
						if (x > 0)
							buttons2p[x - 1][y].requestFocus();
						break;
					case KeyEvent.VK_LEFT:
						if (y > 0)
							buttons2p[x][y - 1].requestFocus();
						break;
					case KeyEvent.VK_RIGHT:
						if (y < minefield2p.getWidth() - 1)
							buttons2p[x][y + 1].requestFocus();
						break;
					case KeyEvent.VK_DOWN:
						if (x < minefield2p.getHeight() - 1)
							buttons2p[x + 1][y].requestFocus();
						break;
					case KeyEvent.VK_ENTER:
						if ((minefield2p.getGridState(x, y) == Minefield.COVERED) && !minefield2p.isBattleDefeated()) {
							battleBtn(x, y, 1);
							buttons1p[x][y].setFocusable(true);
							buttons1p[x][y].requestFocus();
							for (x = 0; x < minefield1p.getWidth(); x++) {
								for (y = 0; y < minefield1p.getHeight(); y++) {
									buttons2p[x][y].setFocusable(false);
								}
							}
							for (x = 0; x < minefield1p.getWidth(); x++) {
								for (y = 0; y < minefield1p.getHeight(); y++) {
									buttons1p[x][y].setFocusable(true);
								}
							}
						}
						break;
					default:
						break;
				}
			}

			@Override
			public void keyTyped(KeyEvent ke) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void keyReleased(KeyEvent ke) {
				throw new UnsupportedOperationException();

			}
		};

		for (int x = 0; x < minefield1p.getWidth(); x++)
		{
			for (int y = 0; y < minefield1p.getHeight(); y++) {
				buttons1p[x][y] = new ButtonMinefield(x, y, 1);
				buttons2p[x][y] = new ButtonMinefield(x, y, 0);
				buttons1p[x][y].addKeyListener(keyListener);
				buttons2p[x][y].addKeyListener(keyListener);
			}
		}
		battleWindow(buttons1p,buttons2p);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				gameStart = false;
				battleBgm.close();
			}
		});

	}

	public void battleWindow(ButtonMinefield[][] buttons1p, ButtonMinefield[][] buttons2p){

		gameWindow.setLayout(new FlowLayout());
		gameWindow.setPreferredSize(new Dimension(1010, 560));

		status1p.setLayout(new FlowLayout(FlowLayout.LEFT));
		status2p.setLayout(new FlowLayout(FlowLayout.RIGHT));

		userPanel.setLayout(new FlowLayout());
		statusBar.setLayout(new FlowLayout());

		statusBar.setPreferredSize(new Dimension(700, 30));
		userPanel.setPreferredSize(new Dimension(1000, 500));

		panel1p.setLayout(new GridLayout(minefield1p.getWidth(), minefield1p.getHeight()));
		panel2p.setLayout(new GridLayout(minefield2p.getWidth(), minefield2p.getHeight()));

		for (int x = 0; x < minefield1p.getWidth(); x++)
		{
			for (int y = 0; y < minefield1p.getHeight(); y++) {
				buttons1p[x][y].setPreferredSize(new Dimension(55, 55));
				buttons2p[x][y].setPreferredSize(new Dimension(55, 55));
				panel1p.add(buttons1p[x][y]);
				panel2p.add(buttons2p[x][y]);
			}
		}

		statusBar.add(status1p);
		statusBar.add(status2p);

		userPanel.add(panel1p);
		userPanel.add(panel2p);

		gameWindow.add(statusBar);
		gameWindow.add(userPanel);

		setContentPane(gameWindow);

		pack();
	}

	private void initStatusBar() {
		JLabel timeLabel = new JLabel("Player1 Lifes : " + this.minefield1p.getnumlife() + " / Left Mines : "
				+ this.minefield1p.getleft() + "         " + "Player2 Lifes : " + this.minefield2p.getnumlife()
				+ " / Left Mines : " + this.minefield1p.getleft()); // �젅�씠釉� �깮�꽦

		ThreadPool.timeThreadPool.submit(() -> {
			while (gameStart) {
				timeLabel.setText("Player1 Lifes : " + this.minefield1p.getnumlife() + " / Left Mines : "
						+ this.minefield1p.getleft() + "         " + "Player2 Lifes : " + this.minefield2p.getnumlife()
						+ " / Left Mines : " + this.minefield2p.getleft()); // �젅�씠釉� �깮�꽦
			}
		});

		status1p.add(timeLabel);
	}

	private void battleBtn(int x, int y, int i) {
		if (i == 0) {
			minefield1p.battleRevealGrid(x, y);
			updateButtonsStates();
			if (minefield1p.isBattleFinished()) {
				gameStart = false;

				if (minefield1p.isBattleWin()) {
					battleBgm.close();
					JOptionPane.showMessageDialog(null, "COGRATULATIONS, WIN! Player1 Find All Mines", "WIN!",
							JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
				} else if (minefield1p.isBattleDefeated()) {
					gameDefeatEffect(x,y);
					JOptionPane.showMessageDialog(null, "Player2 Win! Player1 try harder", "Player2 win",
							JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
				}

			}
		}

		else {
			minefield2p.battleRevealGrid(x, y);
			updateButtonsStates2();

			if (minefield2p.isBattleFinished()) {
				gameStart = false;
				if (minefield2p.isBattleWin()) {
					battleBgm.close();
					JOptionPane.showMessageDialog(null, "COGRATULATIONS, WIN! Player2 Find All Mines", "WIN!",
							JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
				} else if (minefield2p.isBattleDefeated()) {
					gameDefeatEffect(x,y);
					JOptionPane.showMessageDialog(null, "Player1 Win! Player2 try harder", "Player1 win",
							JOptionPane.INFORMATION_MESSAGE);

					setVisible(false);
				}
			}

		}
	}
	private void gameDefeatEffect(int x, int y) {
		battleBgm.close();
		dieEffect.start();
		dieEffect.close();
		if (minefield1p.isBattleDefeated()) {
			for (x = 0; x < minefield1p.getWidth(); x++) {
				for (y = 0; y < minefield1p.getHeight(); y++) {
					buttons1p[x][y].setBackground(Color.RED);
					buttons1p[x][y].setText("END");
				}
			}
		} else if (minefield2p.isBattleDefeated()) {
			for (x = 0; x < minefield2p.getWidth(); x++) {
				for (y = 0; y < minefield2p.getHeight(); y++) {
					buttons2p[x][y].setBackground(Color.RED);
					buttons2p[x][y].setText("END");
				}
			}
		}
	}
	private void updateButtonsStates() {
		for (int x = 0; x < minefield1p.getWidth(); x++) {
			for (int y = 0; y < minefield1p.getHeight(); y++) {
				buttons1p[x][y].setEstado(minefield1p.getGridState(x, y));
			}
		}
	}

	private void updateButtonsStates2() {
		for (int x = 0; x < minefield2p.getWidth(); x++) {
			for (int y = 0; y < minefield2p.getHeight(); y++) {
				buttons2p[x][y].setEstado(minefield2p.getGridState(x, y));
			}
		}
	}

	private void initComponents() {
		gameStart = true;
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Game");
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(userPanel);
		userPanel.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 1094, Short.MAX_VALUE)

		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 553, Short.MAX_VALUE)

		);

		pack();
	}
}
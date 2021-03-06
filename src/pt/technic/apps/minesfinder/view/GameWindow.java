package pt.technic.apps.minesfinder.view;

import pt.technic.apps.minesfinder.entity.Mode;
import pt.technic.apps.minesfinder.entity.Player;
import pt.technic.apps.minesfinder.entity.PlayerCache;
import pt.technic.apps.minesfinder.util.Bgm;
import pt.technic.apps.minesfinder.util.RecordManager;
import pt.technic.apps.minesfinder.util.ThreadPool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GameWindow extends javax.swing.JFrame {
    private ButtonMinefield[][] buttons;
    private Minefield minefield;
    private Player record = new Player();
    private Mode mode;
    private final int[] sec = {0};
    private boolean gameStart = false;


    private Bgm bgm = new Bgm("boom.mp3", false);
    private Bgm mainbgm = new Bgm("jelda.mp3", true);

    public GameWindow() {
        initComponents();
    }

    public GameWindow(Minefield minefield, Mode mode) {

        initComponents();
        this.minefield = minefield;
        this.mode = mode;
        initStatusBar();

        mainbgm.start();
        buttons = new ButtonMinefield[minefield.getWidth()][minefield.getHeight()];
        getContentPane().setLayout(new GridLayout(minefield.getWidth(), minefield.getHeight()));

        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonMinefield botao = (ButtonMinefield) e.getSource();
                int x = botao.getCol();
                int y = botao.getLine();
                minefield.revealGrid(x, y);
                updateButtonsStates();

                if (minefield.isGameFinished()) {
                    gameStart = false;
                    mainbgm.close();

                    try {
                        if (minefield.isPlayerDefeated()) {
                            bgm.start();
                            bgm.close();
                            JOptionPane.showMessageDialog(null, "Oh, a mine broke", "Lost!",
                                    JOptionPane.INFORMATION_MESSAGE);

                        } else {
                            JOptionPane
                                    .showMessageDialog(
                                            null, "Congratulations. You managed to discover all the mines in "
                                                    + (sec[0]) + " seconds",
                                            "victory", JOptionPane.INFORMATION_MESSAGE);

                            saveScore();

                        }
                    } catch (NullPointerException npe) {
                        Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, e);
                    }

                    setVisible(false);
                }
            }
        };

        MouseListener mouseListener = new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    ButtonMinefield botao = (ButtonMinefield) e.getSource();
                    int x = botao.getCol();
                    int y = botao.getLine();
                    if (minefield.getGridState(x, y) == minefield.COVERED) {
                        minefield.setMineMarked(x, y);
                    } else if (minefield.getGridState(x, y) == minefield.MARKED) {
                        minefield.setMineQuestion(x, y);
                    } else if (minefield.getGridState(x, y) == minefield.QUESTION) {
                        minefield.setMineCovered(x, y);
                    }
                    updateButtonsStates();
                }
            }

            @Override
            public void mouseClicked(MouseEvent me) {
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }
        };

        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    int result = JOptionPane.showConfirmDialog
                            (null, "Try again?", "Really?", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        minefield.setGameFinished(true);
                        gameStart = false;
                        mainbgm.close();
                        setVisible(false);
                        GameWindow retrywindow = new GameWindow(
                                new Minefield(minefield.getWidth(), minefield.getHeight(), minefield.getNumMines()),
                                mode);
                        retrywindow.setVisible(true);
                    }
                }
            }

            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };

        // Create buttons for the player
        for (int x = 0; x < minefield.getWidth(); x++) {
            for (int y = 0; y < minefield.getHeight(); y++) {
                buttons[x][y] = new ButtonMinefield(x, y, 2);
                buttons[x][y].addActionListener(action);
                buttons[x][y].addMouseListener(mouseListener);
                buttons[x][y].addKeyListener(keyListener);
                getContentPane().add(buttons[x][y]);
            }
        }

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                gameStart = false;
                mainbgm.close();
            }
        });

    }

    private void initStatusBar() {
        JMenuBar statusBar = new JMenuBar(); // �긽�깭諛� �깮�꽦
        JPanel panel = new JPanel(); // �뙣�꼸 �깮�꽦
        JLabel timeLabel = new JLabel("Time : " + String.valueOf(sec[0]) + " /  Mark Chances : "
                + this.minefield.getNumMarkChances() + " (Press 'R' to Restart)"); // �젅�씠釉�

        ThreadPool.timeThreadPool.submit(() -> {
            while (gameStart) {
                sec[0] += 1; // 1利앷�
                try {
                    TimeUnit.SECONDS.sleep(1); // 1珥� �돩怨�
                    timeLabel.setText("Time : " + String.valueOf(sec[0]) + " / Mark Chances : "
                            + this.minefield.getNumMarkChances() + " (Press 'R' to Restart)"); // �젅�씠釉� �깮�꽦
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        panel.add(timeLabel);
        statusBar.add(panel);
        setJMenuBar(statusBar);
    }

    private void updateButtonsStates() {
        for (int x = 0; x < minefield.getWidth(); x++) {
            for (int y = 0; y < minefield.getHeight(); y++) {
                buttons[x][y].setEstado(minefield.getGridState(x, y));
            }
        }
    }

    private void saveScore() {
        long scoreOfThisGame = sec[0];
        record.setScore(scoreOfThisGame);

        try {
            int index = PlayerCache.getInstance().getListByLevel(mode).size() == 0 ? 0
                    : PlayerCache.getInstance().recordAt(record, PlayerCache.getInstance().getListByLevel(mode));

            if (index >= 0) {
                String name = JOptionPane.showInputDialog("Enter your name");
                if (name != "") {
                    record.setRecord(name.replace(",", ""), sec[0]);
                    PlayerCache.getInstance().getListByLevel(mode).add(record);
                    Collections.sort(PlayerCache.getInstance().getListByLevel(mode), new Comparator<Player>() {

                        @Override
                        public int compare(Player p1, Player p2) {
                            if (p1.getScore() < p2.getScore())
                                return -1;
                            else if (p1.getScore() == p2.getScore())
                                return 0;
                            else
                                return 1;
                        }
                    });

                    int size = PlayerCache.getInstance().getListByLevel(mode).size();
                    for (int i = 20; i < size; i++) {
                        PlayerCache.getInstance().getListByLevel(mode).remove(i);
                    }

                }

                RecordManager.getInstance().writeFile();
            }

        } catch (NullPointerException npe) {
            Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, npe);
        }


    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        gameStart = true; // 寃뚯엫�쓣 �옱�떆�옉�븷 �븣 寃뚯엫 �떆�옉�맖�쓣 �븣由�
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        setTitle("Game");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 1094, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 553, Short.MAX_VALUE));

        pack();
    }

    public Player getPlayer() {
        return record;
    }
}

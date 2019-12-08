package pt.technic.apps.minesfinder.view;

import pt.technic.apps.minesfinder.entity.Mode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetFreeMode extends JFrame {

    JTextField row = new JTextField(2);
    JTextField col = new JTextField(2);
    JTextField mines = new JTextField(2);

    public int parseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public SetFreeMode() {
        JButton okBtn = new JButton("OK");
        setTitle("Set your own game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(3, 2));

        JPanel okBtnPanel = new JPanel();

        Container gameContainer = getContentPane();

        gamePanel.add(new JLabel("Row"));
        gamePanel.add(row);
        gamePanel.add(new JLabel("Column"));
        gamePanel.add(col);
        gamePanel.add(new JLabel("Mines"));
        gamePanel.add(mines);
        gameContainer.add(gamePanel, BorderLayout.CENTER);

        okBtnPanel.add(okBtn);
        okBtn.addActionListener(new FreeMode());

        gameContainer.add(okBtnPanel, BorderLayout.SOUTH);

        setSize(300, 150);
        setResizable(false);
        setVisible(true);

    }

    public class FreeMode implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            int numOfRow = parseInt(row.getText());
            int numOfCol = parseInt(col.getText());
            int numOfMines = parseInt(mines.getText());

            new GameWindow(new Minefield(numOfRow, numOfCol, numOfMines), Mode.FREE).setVisible(true);
        }
    }

}

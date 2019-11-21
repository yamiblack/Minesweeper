package pt.technic.apps.minesfinder.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pt.technic.apps.minesfinder.entity.Mode;
import pt.technic.apps.minesfinder.util.Bgm;

public class SetFreeMode extends JFrame {

	int numOfRow = 0;
	int numOfCol = 0;
	int numOfMines = 0;

	JTextField row = new JTextField(2);
	JTextField col = new JTextField(2);
	JTextField mines = new JTextField(2);

	public int parseInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
		}
		return 0;
	}

	public SetFreeMode() {
		JButton btn = new JButton("OK");
		setTitle("Set your own game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(3, 2));

		JPanel p2 = new JPanel();

		Container c = getContentPane();

		p1.add(new JLabel("Row"));
		p1.add(row);
		p1.add(new JLabel("Column"));
		p1.add(col);
		p1.add(new JLabel("Mines"));
		p1.add(mines);
		c.add(p1, BorderLayout.CENTER);

		p2.add(btn);
		btn.addActionListener(new FreeMode());

		c.add(p2, BorderLayout.SOUTH);

		setSize(300, 150);
		setResizable(false);
		setVisible(true);
		
	}

	public class FreeMode implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int numOfRow = parseInt(row.getText());
			int numOfCol = parseInt(col.getText());
			int numOfMines = parseInt(mines.getText());

			new GameWindow(new Minefield(numOfRow, numOfCol, numOfMines), Mode.FREE).setVisible(true);
		}
	}

}

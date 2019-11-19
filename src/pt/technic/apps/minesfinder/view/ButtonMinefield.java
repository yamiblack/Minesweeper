package pt.technic.apps.minesfinder.view;

import java.awt.Color;
import javax.swing.JButton;

/**
 *
 * @author Gabriel Massadas
 */
public class ButtonMinefield extends JButton {
    private int state, col, line;

    public ButtonMinefield(int col, int line,int i) {
        this.col = col;
        this.line = line;

        if(i==1){
            state = Minefield.COVERED;
            setBackground(Color.LIGHT_GRAY);
        }
        else if(i==0){
            state = Minefield.COVERED;
            setBackground(Color.white);
        }
        else {
            state = Minefield.COVERED;
        }
    }
    
    public void setEstado(int state) {
        this.state=state;
        switch (state) {
            case Minefield.EMPTY:
                setText("");
                setBackground(Color.gray);
                break;
            case Minefield.COVERED:
                setText("");
                setBackground(null);
                break;
            case Minefield.QUESTION:
                setText("?");
                setBackground(Color.yellow);
                break;
            case Minefield.MARKED:
                setText("!");
                setBackground(Color.red);
                break;
            case Minefield.BUSTED:
                setText("*");
                setBackground(Color.orange);
                break;
            case Minefield.PORTION:
                setText("#");
                setBackground(Color.PINK);
                break;
            default:
                setText(String.valueOf(state));
                setBackground(Color.gray);
                break;
        }
    }

    public int getState() {
        return state;
    }

    public int getCol() {
        return col;
    }

    public int getLine() {
        return line;
    }
    
    
}

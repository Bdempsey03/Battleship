package Visuals;

import Logic.Run;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.Semaphore;

/**
 * This class is part of Battleship for Ben Dempsey's CPSC 281 - Winter 2023 Final project.
 *
 * Inputtable text box in bottom right quadrant of battleship GUI
 */
public class TextBox extends JComponent{

    public static Semaphore semaphore = Run.semaphore;//releases permit everytime enter is pressed

    private int X;
    private int Y;
    private final JTextField field = new JTextField("");
    public static String userInput = "";
    private ControlPanel cp;

    /**
     * Constructor for the inputtable textbox
     * @param text the text to display in the box
     * @param f the JFrame
     * @param X size in X-axis
     * @param Y size in Y-axis
     * @param cp control panel that the frame needs to communicate with
     */
    public TextBox(String text, JFrame f,int X, int Y, ControlPanel cp) {
        this.X=X;
        this.Y=Y;

        field.setText(text);//field = textfield
        field.setBackground(Color.GRAY);
        this.add(field); // add textfield
        field.setSize(this.X, this.Y);
        field.setFont(new Font("Times New Roman", Font.BOLD, 100));
        field.addKeyListener( new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){//if enter pressed
                        userInput = field.getText();//input is whatever was in the textbox before enter pressed
                            field.setText("");
                            cp.setRowTwoText(userInput);
                            semaphore.release();//allows game to read input
                }}});
        f.add(this);//add self to frame
    }

    protected void paintComponent(Graphics g){
        field.repaint();
    }
    public void setDimension(int width, int height) {
        X = width;
        Y = height;
        field.setSize(this.X, this.Y);
    }
}



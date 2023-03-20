package Visuals;

import Logic.Run;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.Semaphore;

public class TextBox extends JComponent{

    public static Semaphore semaphore = Run.semaphore;

    private int X;
    private int Y;
//    private TextBoxHeader header = new TextBoxHeader(X, Y);
    private JTextField field = new JTextField("Hello");
    public static String userInput = "";
    private ControlPanel cp;


    public TextBox(String text, JFrame f,int X, int Y, ControlPanel cp) {
        this.X=X;
        this.Y=Y;
//        header.setHeaderText(text);
//        header.setVisible(true);
        field.setText(text);
        field.setBackground(Color.GRAY);
        this.add(field);
//        this.add(header);
        field.setSize(X, Y);
        field.setFont(new Font("Times New Roman", Font.BOLD, 100));
//        header.setSize(X, Y);
        field.addKeyListener( new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                        userInput = field.getText();
                        if((userInput.length()>2) &&
                        (!((userInput.charAt(0)<97 || userInput.charAt(0)>107) &&
                                (userInput.charAt(2)<49 || userInput.charAt(2)>59)))) {//checking that the input is valid
                            field.setText("");
                            cp.setRowTwoText(userInput);
                            semaphore.release();
                        }else{
                            cp.setRowOneText("Invalid move. Please input (a-i)-(1-10)");
                }
                }}});
        f.add(this);
    }

    protected void paintComponent(Graphics g){
//        header.repaint();
        field.repaint();
    }
    public void setDimension(int width, int height) {
        X = width;
        Y = height;
    }
}



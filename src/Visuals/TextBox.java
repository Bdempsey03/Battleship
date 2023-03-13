package Visuals;

import javax.swing.*;
import java.awt.*;

public class TextBox extends JComponent{

    private int X;
    private int Y;
    private TextBoxHeader header = new TextBoxHeader(X, Y);
    private JTextField field = new JTextField("Hello");


    public TextBox(String text, JFrame f,int X, int Y) {
        this.X=X;
        this.Y=Y;
        header.setHeaderText(text);
        field.setText(text);
        field.setBackground(Color.GRAY);
        this.add(field);
        this.add(header);
        field.setSize(X, Y);
        header.setSize(X, Y);
        f.add(this);
    }

    protected void paintComponent(Graphics g){
        header.repaint();
        field.repaint();
    }
    public void setDimension(int width, int height) {
        X = width;
        Y = height;
    }
}



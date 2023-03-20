package Visuals;

import javax.swing.*;
import java.awt.*;

public class TextBoxHeader extends JComponent {

    private int X;
    private int Y;
    private String text;

    public TextBoxHeader(int X, int Y){
        this.X=X;
        this.Y=Y;
    }
    protected void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, X/10, Y/10);
    }
    public void setHeaderText(String s){
        text = s;
        repaint();
    }
}

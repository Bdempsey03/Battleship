package Visuals;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JComponent {
    private int X;
    private int Y;
    private String panelText = "";

    public ControlPanel(int X, int Y){
        this.X = X;
        this.Y = Y;
    }
    public void setDimension(int width, int height) {
        X = width;
        Y = height;
    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D)g;

        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, X, Y);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Times New Roman", Font.BOLD, X*Y/1000));
        g2d.drawString(panelText, X/4, Y*2/3);

    }

    public void setPanelText(String s){
        this.panelText=s;
        repaint();
    }
}

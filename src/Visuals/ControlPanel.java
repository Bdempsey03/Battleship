package Visuals;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JComponent {
    private int X;
    private int Y;
    private String rowOneText = "";
    private String rowTwoText = "";

    private BasicStroke stroke = new BasicStroke((float)(X/100.));
    private BasicStroke stroke2 = new BasicStroke((float)(X/1000.));


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

        g2d.setStroke(stroke);
        g2d.drawRect(X/100,Y/100,X-(2*X/100),Y-(2*Y/100));

        g2d.setStroke(stroke2);
        g2d.setFont(new Font("Arial Black", Font.PLAIN, (X*Y/X+Y)/40));
        g2d.scale(X/800., 3*Y/400.);//cahnge to 200. for GUIv1
        g2d.drawString(rowOneText, 10, 40);//change to 10 for GUIv1
        g2d.drawString(rowTwoText, 10, 40 + ((X*Y)*2/(X+Y))/40);
        g2d.scale(800./X, 400*Y/3.);

    }


    public void setRowOneText(String s){
        this.rowOneText=s;
        repaint();
    }
    public void setRowTwoText(String s){
        this.rowTwoText=s;
        repaint();
    }
}

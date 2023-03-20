package Visuals;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ControlPanel extends JComponent {


    private int X;
    private int Y;
    private String rowOneText = "";
    private String rowTwoText = "";

    private BasicStroke stroke = new BasicStroke((float)(X/100.));
    private BasicStroke stroke2 = new BasicStroke((float)(X/1000.));

    Font font;

    GraphicsEnvironment ge;


    private BufferedImage battleship;
    private BufferedImage aircraftcarrier;
    private BufferedImage submarine;
    private BufferedImage destroyer;
    private BufferedImage patrol;



    public ControlPanel(int X, int Y){
        this.X = X;
        this.Y = Y;
        setImages();
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

//        g2d.scale(2/3., 2/3.);
        g2d.setFont(font);
        g2d.setColor(Color.RED);
//        g2d.scale(X/800., 1);
        g2d.drawString("Opponent Remaining Ships:", 5*Y/7, 5*Y/14);
//        g2d.scale(1,1);
        g2d.setColor(Color.BLACK);
        g2d.fillRect(3*X/8 - (X/50), 3*Y/7 - (Y/30), 5*X/8, 4*Y/7 );
        g2d.setColor(Color.white);
        for(int i =0; i <= 5; i++)
        g2d.drawLine(3*X/8 - (X/50), (9+2*i)*Y/21, X-(X/50), (9+2*i)*Y/21);

        g2d.setColor(Color.BLACK);
        g2d.drawImage(aircraftcarrier, 3*X/8, 3*Y/7, null);
        g2d.drawImage(battleship, 4*X/8, 3*Y/7, null);
        g2d.drawImage(destroyer, 5*X/8, 3*Y/7, null);
        g2d.scale(1,6/7.);
        g2d.drawImage(submarine, 6*X/8, Y/2, null);
        g2d.scale(1,7/6.);
        g2d.scale(1,3/2.);
        g2d.drawImage(patrol, 7*X/8, 6*Y/21, null);
        g2d.scale(1, 2/3.);
//        g2d.scale(3/2.,3/2.);

        g2d.setStroke(stroke);
        g2d.drawRect(X/100,Y/100,X-(2*X/100),Y-(2*Y/100));
        g2d.setStroke(stroke2);
        g2d.scale(X/400., 3*Y/400.);//cahnge to 200. for GUIv1
        g2d.drawString(rowOneText, 10, 40);//change to 10 for GUIv1
        g2d.drawString(rowTwoText, 10, 40 + ((X*Y)/(X+Y))/10);
        g2d.scale(400. * X, (Y/3.) *400);


    }


    public void setRowOneText(String s){
        this.rowOneText=s;
        repaint();
    }
    public void setRowTwoText(String s){
        this.rowTwoText=s;
        repaint();
    }

    public String getRowTwoText() {
        return rowTwoText;
    }
    public void setImages(){
        try {

            aircraftcarrier = ImageIO.read(new File("src\\Visuals\\Images\\Carrier.png"));
            battleship = ImageIO.read(new File("src\\Visuals\\Images\\Battleship.png"));
            destroyer = ImageIO.read(new File("src\\Visuals\\Images\\Cruiser.png"));
            submarine = ImageIO.read(new File("src\\Visuals\\Images\\Submarine.png"));
            patrol = ImageIO.read(new File("src\\Visuals\\Images\\Patrol.png"));

            font = Font.createFont(Font.TRUETYPE_FONT, new File("src\\Visuals\\Font\\digital-7.ttf"));
            font = font.deriveFont(Font.BOLD,28);
            GraphicsEnvironment ge;
            ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);

        }catch(IOException e) {
            System.out.println("Missing one or more images in ControlPanel setImages");
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
    }
}


package Visuals;

import Logic.Game;
import Logic.GameBoard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Board extends JComponent {
    public int X = 600;
    public int Y = 300;
    private int penRadius = (X+Y)/400;
    private boolean init = false;
    private Logic.GameBoard gameBoard;


    BufferedImage aircraftX;

    public Board(int X, int Y, GameBoard board){
        this.gameBoard = board;
        this.X=X;
        this.Y=Y;
    }
    public void setDimension(int width, int height) {
        X = width;
        Y = height;
    }


    protected void paintComponent(Graphics g){
        if(!init) {
            setupImages();
            init = true;
        }

        Graphics2D g2d = (Graphics2D)g;
        /*
         *Gradient drawing
         */
        int green;
        if(!gameBoard.isHuman())
            green = 0;
        else
            green = 100;
        boolean gradientUp = true;
        int gradientRange = 100; //must be <255
        for(int i = 0; i < 1000; i++) {
            if(gradientUp) {
                if(i%gradientRange == 0) {
                    gradientUp = false;
                }
                g2d.setColor(new Color(0, green, 255-gradientRange + (i % gradientRange)));//
            }else{
                if(i%gradientRange == 0) {
                    gradientUp = true;
                }
                g2d.setColor(new Color(0, green, (255) - (i % gradientRange)));
            }

            g2d.fillRect(i*X/1000, 0, 10, Y);
            g2d.setColor(Color.BLACK);
        }
        /*
        Draw a 10 x 10 grid
         */
        g2d.setStroke(new BasicStroke((float)(penRadius), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for(int i = 1; i < 10; i++){
            g2d.drawLine(i*(X/10), 0, i*(X/10), Y);
            g2d.drawLine(0, i*(Y/10), X, i*(Y/10));
        }

        /* Drawing ships */
        if(!gameBoard.isHuman()) {
            for (int i = 0; i < 100; i++) {
                int x = (gameBoard.getBoard(i / 10, i % 10).status);
                /* IF THE TILE BEFORE X IS NULL THEN WE TREAT IT AS EMPTY, ELSE WE USE IT'S VALUE IN CAMPARISONS */
                int y = (gameBoard.getBoard((i / 10) - 1, i % 10)) == null ? 1 : (gameBoard.getBoard((i - 1) / 10, i % 10).status);
                int z = (gameBoard.getBoard((i) / 10, (i % 10) - 1)) == null ? 1 : (gameBoard.getBoard((i - 1) / 10, i % 10).status);

                if ((x == 2 || x == 3 || x == 4 || x == 5 || x == 6 || x == 7 || x == 8 || x == 9))
//                    &&(y!=x)&&(z!=x))
                {
//                drawShip(g2d, i/10 * (X/10) , i%10 * (Y/10));
                    if (x == 2 || x == 3)
                        g2d.setColor(Color.ORANGE);
                    if (x == 4 || x == 5)
                        g2d.setColor(Color.RED);
                    if (x == 6 || x == 7)
                        g2d.setColor(Color.GREEN);
                    if (x == 8 || x == 9)
                        g2d.setColor(Color.MAGENTA);
                    g2d.fillRect(i / 10 * (X / 10), i % 10 * (Y / 10), X / 10, Y / 10);
                }
            }
        }

//        g2d.scale(((double)X / 1000.0), ((double)Y / 1000.0));
//        g2d.drawImage(aircraftX,0,0,null);
//        g2d.scale(1000.0/X, 1000.0/Y);
        for(int i = 0; i < 100; i++){
            if(!gameBoard.getBoard(i/10,i%10).hit) { //not shot at
                g2d.setColor(Color.BLACK);
                g2d.fillOval(((i / 10) * (X / 10)) + (X / 25), (i % 10) * (Y / 10) + Y / 25, X / 50, Y / 50); //making the dots
            }
            else {
                if(gameBoard.getBoard(i/10, i%10).hit && gameBoard.getBoard(i/10, i%10).status == 1) {//shot at and a miss
                    g2d.setColor(Color.white);
                    g2d.fillOval(((i / 10) * (X / 10)) + (X / 35), (i % 10) * (Y / 10) + Y / 35, X / 20, Y / 20);
                    g2d.setColor(Color.gray);
                    g2d.fillOval(((i / 10) * (X / 10)) + (X / 25), (i % 10) * (Y / 10) + Y / 25, X / 40, Y / 40);
                }
                else{
                    if(gameBoard.getBoard(i/10, i%10).hit && gameBoard.getBoard(i/10, i%10).status != 1){
                        g2d.setColor(Color.red);
                        g2d.fillOval(((i / 10) * (X / 10)) + (X / 35), (i % 10) * (Y / 10) + Y / 35, X / 20, Y / 20);
                        g2d.setColor(Color.orange);
                        g2d.fillOval(((i / 10) * (X / 10)) + (X / 25), (i % 10) * (Y / 10) + Y / 25, X / 40, Y / 40);
                    }
                }
            }
        }

        g2d.setColor(Color.GRAY);
        g2d.drawRect(0, 0, X, Y);
    }

    public void drawShip(Graphics2D g2d, int x, int y){
        g2d.scale(X/100., Y/100.);
        g2d.drawImage(aircraftX, x, y, null);
        g2d.scale(100*X, 100 * Y);
    }

    public void setupImages(){
//        try {
//            aircraftX = ImageIO.read(new File("src\\Visuals\\Aircraftcarrier.png"));
//        } catch (IOException e) {
//            System.out.println("Missing images in Board!");;
//        }
    }

}

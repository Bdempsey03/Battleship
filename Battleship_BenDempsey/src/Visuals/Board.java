package Visuals;

import Logic.GameBoard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class is part of Battleship for Ben Dempsey's CPSC 281 - Winter 2023 Final project.
 *
 * This is a visual Battleship board with the ability to display ships, pegs, and tiles guessed.
 */
public class Board extends JComponent {
    public int X = 600;//size
    public int Y = 300;
    private int penRadius = (X+Y)/400;
    private boolean init = false;
    private Logic.GameBoard gameBoard;


    BufferedImage aircraftX;
    private BufferedImage aircraftcarrier;
    private BufferedImage battleship;
    private BufferedImage destroyer;
    private BufferedImage submarine;
    private BufferedImage patrol;

    public Board(int X, int Y, GameBoard board){
        this.gameBoard = board;
        this.X=X;
        this.Y=Y;
    }
    public void setDimension(int width, int height) {
        X = width;
        Y = height;
    }

    /**
     * Draws battleship board
     * @param g graphics object
     */
    protected void paintComponent(Graphics g){
        if(!init) {
            setImages();//load images into member variables
            init = true;
        }

        Graphics2D g2d = (Graphics2D)g;

        //Putting letters around outside
        g2d.setColor(new Color(200,100,10));
        g2d.fillRect(0,0,X,Y);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0,0,X,Y);
        for(int i = 0; i <= 10; i++){//drawing letters and numbers
            g2d.drawString((char)(i+96)+"", i*(X/11)+X/20, Y/20);
            g2d.drawString(i+"", X/20, Y/10+i*(Y/11)+Y/20);
        }


        g2d.scale(0.9,0.9);//drawing board

        /*
         *Gradient drawing
         */
        int green;
        if(!gameBoard.isHuman())//If the board is CPU's we change the colour slightly (to distinguish)
            green = 0;
        else
            green = 100;
        boolean gradientUp = true;//incrementing colour or decrementing?
        int gradientRange = 100; //must be <255
        for(int i = 0; i < 1000; i++) {
            if(gradientUp) {
                if(i%gradientRange == 0) {
                    gradientUp = false;//when we reach the max colour we start decrementing colour
                }
                g2d.setColor(new Color(0, green, 255-gradientRange + (i % gradientRange)));
            }else{
                if(i%gradientRange == 0) {
                    gradientUp = true;//when we hit the min colour we start incrementing colour
                }
                g2d.setColor(new Color(0, green, (255) - (i % gradientRange)));
            }

            g2d.fillRect(i*X/1000 + X/10, 0+(Y/10), 10, Y);
            g2d.setColor(Color.BLACK);
        }
        /*
        Draw a 10 x 10 grid
         */
        g2d.setStroke(new BasicStroke((float)(penRadius), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for(int i = 1; i < 10; i++){
            g2d.drawLine(i*(X/10)+(X/10), 0+(Y/10), i*(X/10)+(X/10), Y+(Y/10));
            g2d.drawLine(0+(X/10), i*(Y/10)+(Y/10), X+(X/10), i*(Y/10)+(Y/10));
        }

        /* Drawing ships */
        if(!gameBoard.isHuman()) {
            for (int i = 0; i < 100; i++) {
                int x = (gameBoard.getBoard(i / 10, i % 10).status);
                /* IF THE TILE BEFORE X IS NULL THEN WE TREAT IT AS EMPTY, ELSE WE USE IT'S VALUE IN CAMPARISONS */
                int y = (gameBoard.getBoard((i / 10) - 1, i % 10)) == null ? 1 : (gameBoard.getBoard((i - 1) / 10, i % 10).status);
                int z = (gameBoard.getBoard((i) / 10, (i % 10) - 1)) == null ? 1 : (gameBoard.getBoard((i - 1) / 10, i % 10).status);

                if ((x == 2 || x == 3 || x == 4 || x == 5 || x == 6 || x == 7 || x == 8 || x == 9 || x == 10 || x == 11))
//                    &&(y!=x)&&(z!=x))
                {
                    if (x == 2 || x == 3)//ship is aircraft
                        g2d.setColor(new Color(128, 128,128));
                    if (x == 4 || x == 5)//ship is battleship
                        g2d.setColor(new Color(118, 168,128));
                    if (x == 6 || x == 7)//cruiser
                        g2d.setColor(new Color(128, 128,178));
                    if (x == 8 || x == 9)//patrol
                        g2d.setColor(new Color(128, 88,128));
                    if (x == 10 || x == 11)//submarine
                        g2d.setColor(new Color(128, 128,78));
                    g2d.fillRect(i / 10 * (X / 10)+(X/10), i % 10 * (Y / 10)+(Y/10), X / 10, Y / 10);
                }
            }
        }

        /*
        Drawing pegs and hits/misses
         */
        for(int i = 0; i < 100; i++){
            if(!gameBoard.getBoard(i/10,i%10).hit) { //not shot at
                g2d.setColor(Color.BLACK);
                g2d.fillOval(((i / 10) * (X / 10)) + (X / 25)+(X/10), (i % 10) * (Y / 10) + Y / 25+(Y/10), X / 50, Y / 50); //making the dots
            }
            else {
                if(gameBoard.getBoard(i/10, i%10).hit && gameBoard.getBoard(i/10, i%10).status == 1) {//shot at and a miss
                    g2d.setColor(Color.white);
                    g2d.fillOval(((i / 10) * (X / 10)) + (X / 35)+(X/10), (i % 10) * (Y / 10) + Y / 35+(Y/10), X / 20, Y / 20);
                    g2d.setColor(Color.gray);
                    g2d.fillOval(((i / 10) * (X / 10)) + (X / 25)+(X/10), (i % 10) * (Y / 10) + Y / 25+(Y/10), X / 40, Y / 40);
                }
                else{
                    if(gameBoard.getBoard(i/10, i%10).hit && gameBoard.getBoard(i/10, i%10).status != 1){//hit and a ship
                        g2d.setColor(Color.red);
                        g2d.fillOval(((i / 10) * (X / 10)) + (X / 35)+(X/10), (i % 10) * (Y / 10) + Y / 35+(Y/10), X / 20, Y / 20);
                        g2d.setColor(Color.orange);
                        g2d.fillOval(((i / 10) * (X / 10)) + (X / 25)+(X/10), (i % 10) * (Y / 10) + Y / 25+(Y/10), X / 40, Y / 40);
                    }
                }
            }
        }

        g2d.setColor(Color.GRAY);
        g2d.drawRect(0+(X/10), 0+(Y/10), X, Y);
    }

    /**
     * Setting up the images
     */
    public void setImages() {
        try {

            aircraftcarrier = ImageIO.read(new File("src\\Visuals\\Images\\Carrier.png"));
            battleship = ImageIO.read(new File("src\\Visuals\\Images\\Battleship.png"));
            destroyer = ImageIO.read(new File("src\\Visuals\\Images\\Cruiser.png"));
            submarine = ImageIO.read(new File("src\\Visuals\\Images\\Submarine.png"));
            patrol = ImageIO.read(new File("src\\Visuals\\Images\\Patrol.png"));

        } catch (IOException e) {
            System.out.println("Missing one or more images in Board setImages");
            e.printStackTrace();
        }
    }

}

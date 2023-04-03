package Visuals;

import Logic.GameBoard;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * This class is part of Battleship for Ben Dempsey's CPSC 281 - Winter 2023 Final project.
 *
 * Graphical user interface for Battleship
 */
public class GUI {
    private final JFrame frame = new JFrame("Battleship");
    private int X = 900;//size
    private int Y = 600;


    private GameBoard humanGameBoard;
    private GameBoard cpuGameBoard;
    private final boolean isVisible;


    protected Board humanBoard;
    protected Board cpuBoard;
    protected ControlPanel ctrlPanel;
    protected TextBox text;

    /**
     * Constructor for battleship GUI
     * @param humanGameBoard the user's board
     * @param cpuGameBoard the CPU's game board
     * @param visible set false if simulating game without gui
     */
    public GUI(GameBoard humanGameBoard, GameBoard cpuGameBoard, boolean visible){
        this.isVisible = visible;
        this.humanGameBoard = humanGameBoard;
        humanBoard = new Board(X/2, Y, humanGameBoard);
        cpuBoard = new Board(X/2, Y, cpuGameBoard);
        ctrlPanel = new ControlPanel(X, Y, cpuGameBoard);
        text = new TextBox("", frame, 2*X/3, Y/4, ctrlPanel);
        humanBoard.repaint();
        cpuBoard.repaint();
        ctrlPanel.repaint();
        text.repaint();
    }

    /**
     * Gets everything set up for the battleship GUI
     */
    public void setup(){
        frame.setSize(X, Y);
        frame.setLayout(null);//chose null because it is more versatile
        frame.add(humanBoard);
        frame.add(cpuBoard);
        frame.add(ctrlPanel);
        frame.add(text);



        frame.setVisible(isVisible);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*
        When we resize the window we have to resize all the individual components and call repaint on them
         */
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                X=frame.getContentPane().getWidth();//every time we resize we get the new size
                Y=frame.getContentPane().getHeight();
                cpuBoard.setDimension(X/3, Y/2);//resizing all components
                humanBoard.setDimension(X/3, Y/2);
                ctrlPanel.setDimension(2*X/3,3*Y/4);
                text.setDimension(2*X/3, Y/4);


                cpuBoard.setBounds(0, 0, X/3, Y/2);
                humanBoard.setBounds(0, Y/2, X/3, Y/2);
                ctrlPanel.setBounds(X/3, 0, X*2/3, 3*Y/4);
                text.setBounds(X/3, 3*Y/4, 2*X/3, Y/4);

                repaint();//calls repaint for all components
            }
        });
        repaint();//only needed for initial setup
    }


    /**
     * Repaints all children
     */
    public void repaint() {
        cpuBoard.repaint();
        humanBoard.repaint();
        text.repaint();
        ctrlPanel.repaint();
    }
    public ControlPanel getCtrl(){
        return ctrlPanel;
    }

    public void setVisible(boolean visible){
        frame.setVisible(visible);
    }
}

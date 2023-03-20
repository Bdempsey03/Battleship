package Visuals;

import Logic.GameBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GUI {
    private JFrame frame = new JFrame();
    private int X = 600;
    private int Y = 400;


    private GameBoard humanGameBoard;
    private GameBoard cpuGameBoard;


    protected Board humanBoard;
    protected Board cpuBoard;
    protected ControlPanel ctrlPanel;
    protected TextBox text;

    public GUI(GameBoard humanGameBoard, GameBoard cpuGameBoard){
        this.humanGameBoard = humanGameBoard;
        humanBoard = new Board(X, Y, humanGameBoard);
        cpuBoard = new Board(X, Y, cpuGameBoard);
        ctrlPanel = new ControlPanel(X, Y);
        text = new TextBox("Input here", frame, X, Y, ctrlPanel);
        humanBoard.repaint();
        cpuBoard.repaint();
        ctrlPanel.repaint();
        text.repaint();
    }

    public void setup(){
        frame.setSize(X, Y);
        frame.setLayout(null);
        frame.add(humanBoard);
        frame.add(cpuBoard);
        frame.add(ctrlPanel);
        frame.add(text);



        frame.setVisible(true);
        //^^^CONTROLED IN RUN
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                X=frame.getContentPane().getWidth();
                Y=frame.getContentPane().getHeight();
                cpuBoard.setDimension(X/2, 3*Y/4);
                humanBoard.setDimension(X/2, 3*Y/4);
                ctrlPanel.setDimension(X,Y/4);//change in control panel if using this GUI
                text.setSize(X/4, Y/4);


                cpuBoard.setBounds(0, 0, X/2, 3*Y/4);
                humanBoard.setBounds(X/2, 0, X/2, 3*Y/4);
                ctrlPanel.setBounds(X/4, 3*Y/4, X, Y/4);
                text.setBounds(0, 3*Y/4, X/4, Y/4);

                repaint();
            }
        });
        repaint();
    }


    public void repaint() {
        ctrlPanel.repaint();
        cpuBoard.repaint();
        humanBoard.repaint();
        text.repaint();
    }
    public ControlPanel getCtrl(){
        return ctrlPanel;
    }

    public void setVisible(boolean visible){
        frame.setVisible(visible);
    }
    //    public JTextArea getTextBox(){return textBox;}
}

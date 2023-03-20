package Visuals;

import Logic.GameBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GUIv2 {
    private JFrame frame = new JFrame();
    private int X = 900;
    private int Y = 600;


    private GameBoard humanGameBoard;
    private GameBoard cpuGameBoard;
    private boolean isVisible;


    protected Board humanBoard;
    protected Board cpuBoard;
    protected ControlPanel ctrlPanel;
    protected TextBox text;

    public GUIv2(GameBoard humanGameBoard, GameBoard cpuGameBoard, boolean visible){
        this.isVisible = visible;
        this.humanGameBoard = humanGameBoard;
        humanBoard = new Board(X/2, Y, humanGameBoard);
        cpuBoard = new Board(X/2, Y, cpuGameBoard);
        ctrlPanel = new ControlPanel(X, Y);
        text = new TextBox("", frame, 2*X/3, Y/4, ctrlPanel);
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



        frame.setVisible(isVisible);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                X=frame.getContentPane().getWidth();
                Y=frame.getContentPane().getHeight();
                cpuBoard.setDimension(X/3, Y/2);
                humanBoard.setDimension(X/3, Y/2);
                ctrlPanel.setDimension(2*X/3,3*Y/4);
                text.setDimension(2*X/3, Y/4);


                cpuBoard.setBounds(0, 0, X/3, Y/2);
                humanBoard.setBounds(0, Y/2, X/3, Y/2);
                ctrlPanel.setBounds(X/3, 0, X*2/3, 3*Y/4);
                text.setBounds(X/3, 3*Y/4, 2*X/3, Y/4);

                repaint();
            }
        });
        repaint();
    }


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
//    public JTextArea getTextBox(){return textBox;}
}

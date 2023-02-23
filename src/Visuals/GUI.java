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

    protected JButton textBox;

    public GUI(GameBoard humanGameBoard, GameBoard cpuGameBoard){
        this.humanGameBoard = humanGameBoard;
        humanBoard = new Board(X, Y, humanGameBoard);
        cpuBoard = new Board(X, Y, cpuGameBoard);
        ctrlPanel = new ControlPanel(X, Y);
        textBox = new JButton("ello");
        textBox.setBackground(Color.BLUE);
        textBox.repaint();
        humanBoard.repaint();
        cpuBoard.repaint();
        ctrlPanel.repaint();
    }

    public void setup(){
        frame.setSize(X, Y);
        frame.setLayout(null);
        frame.add(textBox);
        frame.add(humanBoard);
        frame.add(cpuBoard);
        frame.add(ctrlPanel);



        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                X=frame.getContentPane().getWidth();
                Y=frame.getContentPane().getHeight();
                humanBoard.setDimension(X/2, 3*Y/4);
                cpuBoard.setDimension(X/2, 3*Y/4);
                ctrlPanel.setDimension(3*X/4,Y/4);

                textBox.setBounds(X, Y*3/4, X/4, Y/4);
                humanBoard.setBounds(0, 0, X/2, 3*Y/4);
                cpuBoard.setBounds(X/2, 0, X/2, 3*Y/4);
                ctrlPanel.setBounds(X/4, 3*Y/4, X, Y/4);

                textBox.setText("THis is  a test");
                repaint();
            }
        });
    }


    public void repaint() {
        textBox.repaint();
        ctrlPanel.repaint();
        cpuBoard.repaint();
        humanBoard.repaint();
    }
    public ControlPanel getCtrl(){
        return ctrlPanel;
    }
//    public JTextArea getTextBox(){return textBox;}
}

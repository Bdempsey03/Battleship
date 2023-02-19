package Visuals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GUI {
    private JFrame frame = new JFrame();
    private int X = 600;
    private int Y = 400;

    protected Board userBoard = new Board(X, Y);
    protected Board cpuBoard = new Board(X, Y);
    protected ControlPanel ctrlPanel = new ControlPanel(X, Y);

    public void setup(){
        frame.setSize(X, Y);
        frame.setLayout(null);
        frame.add(userBoard);
        frame.add(cpuBoard);
        frame.add(ctrlPanel);


        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                X=frame.getContentPane().getWidth();
                Y=frame.getContentPane().getHeight();
                userBoard.setDimension(X/2, 3*Y/4);
                cpuBoard.setDimension(X/2, 3*Y/4);
                ctrlPanel.setDimension(X,Y/4);
                userBoard.setBounds(0, 0, X/2, 3*Y/4);
                cpuBoard.setBounds(X/2, 0, X/2, 3*Y/4);
                ctrlPanel.setBounds(0, 3*Y/4, X, Y/4);

                ctrlPanel.repaint();
                cpuBoard.repaint();
                userBoard.repaint();
            }
        });
    }


}

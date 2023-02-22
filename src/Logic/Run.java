package Logic;

import Visuals.GUI;

import java.util.Scanner;

public class Run {

    public Game game;
    public GUI gui;
    public boolean humanTurn;
    public Scanner sc = new Scanner(System.in);
    public Move newestMove;

    public static void main(String[] args) {
        Run run = new Run();
        run.go();
    }

    public void go() {
        humanTurn = true;
        game = new Game();//makes board, cpu, player
        gui = new GUI(game.getHumanBoard(), game.getCpuBoard()); //make gui
        gui.setup();



        while (true) {//change to win condition later
            if (humanTurn) {
                newestMove = new HumanMove((char)sc.nextInt(), sc.nextInt());
                game.human.fire(newestMove);
            }else{

                try {
                    Thread.currentThread().sleep(1000);//temporary
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                newestMove = new SmartMove();
                game.cpu.fire(newestMove);
            }
            String hitOrMissed = "hit";
            if(humanTurn)
                gui.getCtrl().setPanelText("Human " + hitOrMissed + " at " + newestMove);
            gui.repaint();
            humanTurn=!humanTurn;
        }
    }
}

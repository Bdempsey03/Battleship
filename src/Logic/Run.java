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
        int[] shipMaker = new int[4];


        humanTurn = true;
        game = new Game();//makes board, cpu, player
        gui = new GUI(game.getHumanBoard(), game.getCpuBoard()); //make gui
        gui.setup();

        gui.getCtrl().setPanelText("Please select locations for you ships!\n start of ship x:");
        gui.repaint();
        shipMaker[0] = sc.nextInt();
        gui.getCtrl().setPanelText("Please select locations for you ships!\n start of ship y:");
        gui.repaint();
        shipMaker[1] = sc.nextInt();
        gui.getCtrl().setPanelText("Please select locations for you ships!\n orientation:");
        gui.repaint();
        shipMaker[2] = sc.nextInt();
        gui.getCtrl().setPanelText("Please select locations for you ships!\n length:");
        shipMaker[3] = sc.nextInt();
        game.humanGameBoard.addShip(shipMaker[0], shipMaker[1], shipMaker[2], shipMaker[3]);
        gui.repaint();


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
            String turn = humanTurn?"Human ":"CPU ";
            gui.getCtrl().setPanelText(turn + hitOrMissed + " at " + newestMove);
            gui.repaint();
            humanTurn=!humanTurn;
        }
    }
}

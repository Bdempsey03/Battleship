package Logic;
import Visuals.GUI;
import Visuals.GUIv2;
import org.w3c.dom.ls.LSOutput;

import java.util.TreeSet;
import java.util.Scanner;

public class Run {

    public Game game;
    public GUIv2 gui;
    public boolean humanTurn;
    public static Scanner sc = new Scanner(System.in);
    public Move newestMove;
    public int numberOfShips = 5;
    public String ship;
    public int length;
    public TreeSet<Integer> cpuBoardHits = new TreeSet<>(); //GOING TO USE THESE TO KEEP TRACK OF PLACES SHOT AT
    public TreeSet<Integer> humanBoardHits = new TreeSet<>();

    public static int cpuWinCount = 0;
    public static int gameCount = 0;
    public int waitTime = 0;//Time cpu takes to make a move

    private static int ITERATIONS = 10000;

    public static void main(String[] args) {

        Run run = new Run();

        int response;

        do {
            System.out.println("Do you want to play Battleship (0) or simulate games (1)? ");
            response = sc.nextInt();
        }while(response!=0 && response!=1);

        if(response==1) {
            run.simulate(5000, false, 0, 0);
            run.simulate(5000, true, 0, 0);
            run.simulate(5000, false, 1, 1);
            run.simulate(5000, true, 1, 1);
            run.simulate(5000, false, 1, 0);
            run.simulate(5000, true, 1, 0);
        }else{
            System.out.println("What difficulty? (0/1)");
            response = sc.nextInt();
            run.startGame(true, 0, response, true);
        }

    }
    public void simulate(int iterations, boolean fair, int cpu1Diff, int cpu2Diff){
        cpuWinCount = 0;
        gameCount = 0;
        if(fair) {//are we switching who goes first or will it always be cpu?
            for (int i = 0; i < iterations / 2; i++) {
                this.startGame(true, cpu1Diff, cpu2Diff, false);
            }
            for (int i = 0; i < iterations / 2; i++) {
                this.startGame(false, cpu1Diff, cpu2Diff, false);
            }
        }else{
            for (int i = 0; i < iterations; i++) {
                this.startGame(true, cpu1Diff, cpu2Diff, false);
            }
        }
        while (gameCount != iterations) System.out.print("");
        System.out.println("The CPU wins " + ((float)cpuWinCount / (float)gameCount) * 100. + "% of the time");



    }

    public void startGame(boolean humanTurn, int cpu1Diff, int cpu2Diff, boolean humanPlayer) {
        int[] shipMaker = new int[4];
        if(humanPlayer)
            waitTime = 100; //waits 100ms before moving


        this.humanTurn = humanTurn;
        game = new Game();//makes board, cpu, player
        gui = new GUIv2(game.getHumanBoard(), game.getCpuBoard(), humanPlayer); //make gui
        gui.setup();

        setUpShips(humanPlayer, game.getHumanBoard());//cpu setup on human board if not humanPlayer, else human sets up

//        setUpShips(true);//human setup ships
        setUpShips(false, game.getCpuBoard());//cpu setup ships


//        System.out.println(game.getCpuBoard());
        while (!winConditionMet(game.getCpuBoard(), game.getHumanBoard())[0]) {//change to win condition later
//            System.out.println(game.getCpuBoard());
            if (humanTurn) {
                do {

                    //START OF AI PART
                    if(!humanPlayer) {
                        try {
                            newestMove = new SmartMove(cpu1Diff, game.cpuGameBoard);//for CPU
                            Thread.currentThread().sleep(waitTime);
                        } catch (InterruptedException e) {
                            System.out.println("erorr at moves in RUN");
                        }//end of AI part
                    }else{
                    newestMove = new HumanMove((char) sc.nextInt(), sc.nextInt());//for human player
                    }
                } while (humanBoardHits.contains(newestMove.getColumn() * 10 + newestMove.getRow()));

                game.human.fire(newestMove, game.getCpuBoard());
                humanBoardHits.add(newestMove.getColumn() * 10 + newestMove.getRow());
//                System.out.println(newestMove.getColumn()*10 + newestMove.getRow());
//                System.out.println(humanBoardHits);
            } else {

                try {
                    Thread.currentThread().sleep(waitTime*6);//temporary
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                do {
                    newestMove = new SmartMove(cpu2Diff, game.humanGameBoard);
                } while (cpuBoardHits.contains(newestMove.getColumn() * 10 + newestMove.getRow()));

                game.cpu.fire(newestMove, game.getHumanBoard());
                cpuBoardHits.add(newestMove.getColumn() * 10 + newestMove.getRow());
            }
            String hitOrMissed = "hit";
            String turn = humanTurn ? "Human " : "CPU ";
            gui.getCtrl().setRowOneText(turn + hitOrMissed + " at " + newestMove);
            gui.repaint();
            humanTurn = !humanTurn;
        }
        String winner = winnerString(game.getCpuBoard(), game.getHumanBoard());

        gui.getCtrl().setRowOneText(winner + "won!!");
        humanBoardHits.clear();
        cpuBoardHits.clear();
    }

    public void setUpShips(boolean isHuman, GameBoard board) {
        int[] shipMaker = new int[4];
        if (isHuman) {
            for (int i = 0; i < 5; i++) {//Human placing their own ships
                switch (i) {
                    case 0:
                        ship = " aircraft carrier";
                        length = 5;
                        break;
                    case 1:
                        ship = " battleship";
                        length = 4;
                        break;
                    case 2:
                        ship = " submarine";
                        length = 3;
                        break;
                    case 3:
                        ship = " cruiser";
                        length = 3;
                        break;
                    case 4:
                        ship = " destroyer";
                        length = 2;
                }
                do {
                    gui.getCtrl().setRowOneText("Please select locations for your" + ship + "!\n start of ship x:");
                    gui.repaint();
                    shipMaker[0] = sc.nextInt();
                    gui.getCtrl().setRowOneText("Please select locations for your" + ship + "!\n start of ship y:");
                    gui.repaint();
                    shipMaker[1] = sc.nextInt();
                    gui.getCtrl().setRowOneText("Please select locations for your" + ship + "!\n orientation:");
                    gui.repaint();
                    shipMaker[2] = sc.nextInt();
                    shipMaker[3] = length;
                } while (!Game.legalPlacement(board, shipMaker[0], shipMaker[1], shipMaker[3], shipMaker[2]));
                board.addShip(shipMaker[0], shipMaker[1], shipMaker[2], shipMaker[3]);
                gui.repaint();
            }
        } else {
            for (int i = 0; i < 5; i++) {
                switch (i) {
                    case 0:
                        ship = " aircraft carrier";
                        length = 5;
                        break;
                    case 1:
                        ship = " battleship";
                        length = 4;
                        break;
                    case 2:
                        ship = " submarine";
                        length = 3;
                        break;
                    case 3:
                        ship = " cruiser";
                        length = 3;
                        break;
                    case 4:
                        ship = " destroyer";
                        length = 2;
                }
                try {
                    gui.getCtrl().setRowOneText("CPU choosing.");
                    Thread.sleep(waitTime);
                    gui.repaint();
                    gui.getCtrl().setRowOneText("CPU choosing..");
                    Thread.sleep(waitTime);
                    gui.repaint();
                    gui.getCtrl().setRowOneText("CPU choosing...");
                    Thread.sleep(waitTime);
                    gui.repaint();
                    shipMaker = game.cpu.chooseShipLocations(length, board);
                    board.addShip(shipMaker[0], shipMaker[1], shipMaker[2], shipMaker[3]);
//                    System.out.println(shipMaker[0] + " " + shipMaker[1] + " " + shipMaker[2] + " " + shipMaker[3]);
                    gui.repaint();
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted");
                }
            }
        }

    }

    /**
     * @param b1 first board
     * @param b2 second board
     * @return [0] = true when game has been 1, [1] = true if b1 won
     */
    public boolean[] winConditionMet(GameBoard b1, GameBoard b2) {

        boolean b1won = true;
        boolean b2won = true;
        for (int i = 0; i < 100; i++) {
            if (b1.getBoard(i / 10, i % 10).getStatus() != 1 && !b1.getBoard(i / 10, i % 10).isHit()) {//if a tile has a ship and hasnt been hit
                b1won = false;
            }
            if (b2.getBoard(i / 10, i % 10).getStatus() != 1 && !b2.getBoard(i / 10, i % 10).isHit()) {//if a tile has a ship and hasnt been hit
                b2won = false;

            }
        }
        if (b2won) {
            gameCount++;
            cpuWinCount++;
        } else if (b1won) {
            gameCount++;
        }
        return new boolean[]{b1won || b2won, b1won};
    }
    public String winnerString(GameBoard b1, GameBoard b2) {
        String s = "";
        boolean b1won = true;
        boolean b2won = true;
        for (int i = 0; i < 100; i++) {
            if (b1.getBoard(i / 10, i % 10).getStatus() != 1 && !b1.getBoard(i / 10, i % 10).isHit()) {//if a tile has a ship and hasnt been hit
                b1won = false;
            }
            if (b2.getBoard(i / 10, i % 10).getStatus() != 1 && !b2.getBoard(i / 10, i % 10).isHit()) {//if a tile has a ship and hasnt been hit
                b2won = false;

            }
        }
        if (b2won) {
            if(b2.isHuman())
                s = "Human ";
            else
                s = "CPU ";
        } else if (b1won) {
            if(b1.isHuman())
                s = "Human ";
            else
                s = "CPU ";
        }
        return s;
    }
    private void GUIVisible(boolean isVisible, GUI gui){
        gui.setVisible(isVisible);
    }
}

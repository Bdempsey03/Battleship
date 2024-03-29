package Logic;
import Visuals.GUI;

import java.util.TreeSet;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

/**
 * This class is part of Battleship for Ben Dempsey's CPSC 281 - Winter 2023 Final project.
 *
 * This is the class that runs a game of battleship. It has a Game, GUi, Players, winCount, and has the main method.
 * The entire game happens in this class.
 */
public class Run {

    public Game game;//the game
    public GUI gui;//the gui
    public boolean humanTurn;//true when human turn
    public static Scanner sc = new Scanner(System.in);//for console input (mostly for testing)
    public Move newestMove;//the last move created
    public String ship;
    public int length;//lenght of ship
    /*
    I chose a Red Black tree because it has faster search times than an arrayList
     */
    public TreeSet<Integer> cpuBoardHits = new TreeSet<>(); //These contain the previously guessed locations
    public TreeSet<Integer> humanBoardHits = new TreeSet<>();
    public static Semaphore semaphore = new Semaphore(0);//Every time a user presses enter a permit is released

    public static int cpuWinCount = 0;
    public static int gameCount = 0;
    public int waitTime = 0;//Time cpu takes to make a move

    private static int ITERATIONS = 10000;

    /**
     * Main method for the game of battleship
     */
    public static void main(String[] args) {

        Run run = new Run();

        int response = 2;//When the below comment is active the game asks user if they want to simulate games or not

        /*
        do {
            System.out.println("Do you want to play Battleship (0) or simulate games (1)? ");
            response = sc.nextInt();
        }while(response!=0 && response!=1);
        */

        if(response==1) {
            run.simulate(ITERATIONS, false, 0, 0);
            run.simulate(ITERATIONS, true, 0, 0);
            run.simulate(ITERATIONS, false, 1, 1);
            run.simulate(ITERATIONS, true, 1, 1);
            run.simulate(ITERATIONS, false, 1, 0);
            run.simulate(ITERATIONS, true, 1, 0);
        }else{
//            System.out.println("What difficulty? (0/1)");
//            response = sc.nextInt();
            run.startGame(true, 1, response-1, true);
        }

    }

    /**
     * For simulating games without a gui. For analyzing algorithms.
     * @param iterations iterations
     * @param fair cpu goes first each time or split 50/50
     * @param cpu1Diff difficulty 1
     * @param cpu2Diff difficulty 2
     */
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

    /**
     * This method starts a game when called
     * @param humanTurn true if it is human's turn first
     * @param cpu1Diff difficulty of cpu
     * @param cpu2Diff difficulty of cpu2
     * @param humanPlayer is it CPU vs CPU or Human vs CPU
     */
    public void startGame(boolean humanTurn, int cpu1Diff, int cpu2Diff, boolean humanPlayer){
        if(humanPlayer)
            waitTime = 100; //waits 100ms before moving


        this.humanTurn = humanTurn;
        game = new Game();//makes board, cpu, player
        gui = new GUI(game.getHumanBoard(), game.getCpuBoard(), humanPlayer); //make gui
        gui.setup();


        //TODO change false to true/isHuman to let user set up ships
        try {
            setUpShips(false, game.getHumanBoard());//cpu setup on human board if not humanPlayer, else human sets up

//        setUpShips(true);//human setup ships
            setUpShips(false, game.getCpuBoard());//cpu setup ships
        }catch(InterruptedException e){
            System.out.println("Error in Run setup ships");
        }

        while (!winConditionMet(game.getCpuBoard(), game.getHumanBoard())[0]) {//change to win condition later
            if (humanTurn) {
                do {

                    //START OF AI PART
                    if(!humanPlayer) {
                        try {
                            newestMove = new SmartMove(cpu1Diff, game.humanGameBoard);//for CPU
                            Thread.currentThread().sleep(waitTime);
                        } catch (InterruptedException e) {
                            System.out.println("erorr at moves in RUN");
                        }//end of AI part
                    }else{
                        try {
                            semaphore.acquire();//Thread will wait here until a move is inputted into the GUI
                            newestMove = new HumanMove(textToMove(gui.getCtrl().getRowTwoText())[0],textToMove(gui.getCtrl().getRowTwoText())[1]);//for human player

                        }catch(InterruptedException e){
                            System.out.println("Semaphore issues in Run");
                        }
                    }
                } while (humanBoardHits.contains(newestMove.getColumn() * 10 + newestMove.getRow()));
                game.human.fire(newestMove, game.getCpuBoard());
                humanBoardHits.add(newestMove.getColumn() * 10 + newestMove.getRow());
            } else {
                try {
                    Thread.currentThread().sleep(waitTime*6);//CPU waits
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                do {
                    newestMove = new SmartMove(cpu2Diff, game.cpuGameBoard, cpuBoardHits);//CPU move
                } while (cpuBoardHits.contains(newestMove.getColumn() * 10 + newestMove.getRow()));//Keep asking for new move until it is one that
                //hasn't been guessed before

                game.cpu.fire(newestMove, game.getHumanBoard());//do the move
                cpuBoardHits.add(newestMove.getColumn() * 10 + newestMove.getRow());//add the move to the Red Black tree
            }
            String hitOrMissed;

            if(humanTurn)//if the human made the last move
                if(game.getCpuBoard().getBoard(newestMove.getRow(), newestMove.getColumn()).status!=1)//if the last move was a hit
                    hitOrMissed = "hit";
                else
                    hitOrMissed = "missed";
            else
                if(game.getHumanBoard().getBoard(newestMove.getRow(), newestMove.getColumn()).status!=1)//if the last move was a hit
                    hitOrMissed = "hit";
                else
                    hitOrMissed = "missed";

            String turn = humanTurn ? "Human " : "CPU ";//if it is the human player's turn then we want to display the word "Human"
            gui.getCtrl().setRowOneText(turn + hitOrMissed + " at " + newestMove);
            gui.repaint();
            humanTurn = !humanTurn;
        }
        String winner = winnerString(game.getCpuBoard(), game.getHumanBoard());

        gui.getCtrl().setRowOneText(winner + "won!");
        humanBoardHits.clear();
        cpuBoardHits.clear();
    }

    /**
     * This method sets up ships on a battleship board
     * @param isHuman if true then ships are set up manually
     * @param board the board we are setting up on
     * @throws InterruptedException has Thread.sleep() in it
     */
    public void setUpShips(boolean isHuman, GameBoard board) throws InterruptedException {
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
                    gui.getCtrl().setRowOneText("Enter "+ship+" location");
                    gui.repaint();
                    semaphore.acquire();//can only acquire if user
                    shipMaker[0] = gui.getCtrl().getRowTwoText().charAt(0)-97;//converts 1 -> A, etc
                    gui.getCtrl().setRowOneText("Please select locations for your" + ship + "!\n start of ship y:");
                    gui.repaint();
                    shipMaker[1] = gui.getCtrl().getRowTwoText().charAt(2)-48;
                    gui.getCtrl().setRowOneText("Please select locations for your" + ship + "!\n orientation:");
                    gui.repaint();
                    shipMaker[2] = gui.getCtrl().getRowTwoText().charAt(4)-48;
                    shipMaker[3] = length;
                } while (!Game.legalPlacement(board, shipMaker[0], shipMaker[1], shipMaker[3], shipMaker[2]));
                //keep asking for moves until it is a valid move and then add the ship to the board
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
                    gui.repaint();
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted at end of RUN");
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
    private int[] textToMove(String text){
        String s = text;
        int row = s.charAt(0)-97;
        int col = s.charAt(2)-48;
        System.out.println(row + "_" + col);

        return new int[]{row, col};
    }
}

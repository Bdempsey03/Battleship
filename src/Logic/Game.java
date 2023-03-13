package Logic;

public class Game {
    GameBoard humanGameBoard;
    GameBoard cpuGameBoard;
    Player cpu;
    Player human;

    public Game() {
        humanGameBoard = new GameBoard(10, 10, true);
        cpuGameBoard = new GameBoard(10, 10, false);
        cpu = new CPU(this);
        human = new Human(this);
    }


    public GameBoard getHumanBoard() {
        return this.cpuGameBoard;
    }//This is the board that our ships will appear on


    public GameBoard getCpuBoard() {
        return humanGameBoard;
    }

    public static boolean legalPlacement(GameBoard board, int x, int y, int length, int orientation) {//orientation 1 = vertical, 0 = hori
        if (orientation == 0) {
            while (length != 0) {
                if (board.getBoard(x, y) == null || board.getBoard(x, y).getStatus() != 1) {
//                    System.out.println("INVALID");
                    return false;
                }
                length--;
                x++;
            }
        } else {
            while (length != 0) {
                if (board.getBoard(x, y) == null || board.getBoard(x, y).getStatus() != 1) {
//                    System.out.println("INVALID");
                    return false;
                }
                length--;
                y++;
            }
        }
//        System.out.println("VALID");
        return true;
    }
}

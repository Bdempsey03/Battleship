package Logic;

import java.util.Random;

public class CPU implements Player {

    private GameBoard board;
    private Random rnd = new Random();

    public CPU(Game game) {
        board = game.getCpuBoard();
    }

    @Override
    public void fire(Move move, GameBoard brd) {
        brd.setBoard(move.getRow(), move.getColumn(), true);
    }

    public int[] chooseShipLocations(int a, GameBoard b) {
        int[] shipMaker = new int[4];

        do {
            shipMaker[0] = rnd.nextInt(10);//x
            shipMaker[1] = rnd.nextInt(10);//y
            shipMaker[2] = rnd.nextInt(2);//orientation
            shipMaker[3] = a;
        } while ((shipMaker[0] + a > 9 && shipMaker[2] == 0) || (shipMaker[1] + a > 9 && shipMaker[2] == 1)
                || !legalPlacement(b, shipMaker[0], shipMaker[1], a, shipMaker[2]));
        return shipMaker;
    }

    public boolean legalPlacement(GameBoard theBoard, int x, int y, int length, int orientation) {//orientation 1 = vertical, 0 = hori
        if (orientation == 0) {
            while (length != 0) {
                if (theBoard.getBoard(x, y) == null || theBoard.getBoard(x, y).getStatus() != 1) {
//                    System.out.println("invalid");
                    return false;
                }
                length--;
                x++;
            }
        } else {
            while (length != 0) {
                if (theBoard.getBoard(x, y) == null || theBoard.getBoard(x, y).getStatus() != 1) {
//                    System.out.println("invalid");
                    return false;
                }
                length--;
                y++;
            }
        }
//        System.out.println("valid");
        return true;
    }
}

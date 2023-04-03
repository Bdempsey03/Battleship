package Logic;

import java.util.Random;

/**
 * This class is part of Battleship for Ben Dempsey's CPSC 281 - Winter 2023 Final project.
 *
 * THe Cpu player in Battleship
 */
public class CPU implements Player {

    private GameBoard board;
    private Random rnd = new Random();

    public CPU(Game game) {
        board = game.getCpuBoard();
    }

    /**
     * Method that sets the board to be hit
     * @param move where we are firing
     * @param brd the board we are firing on
     */
    @Override
    public void fire(Move move, GameBoard brd) {
        brd.setBoard(move.getRow(), move.getColumn(), true);
    }

    /**
     * Method to place ships as a user. Doesnt allow invalid input
     * @param a the ship being placed (corresponding with tile legend for ship int representation)
     * @param b the board we are placing on
     * @return int[] in form {(row ship starts on), (column ship starts on), (orientation of ship), (length)}
     */
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

    /**
     * Checks if a placement of a ship is valid
     * @param theBoard board being placed on
     * @param x starting row
     * @param y starting column
     * @param length length of the ship
     * @param orientation 0 = horizontal, 1 = vertical
     * @return valid placement?
     */
    public boolean legalPlacement(GameBoard theBoard, int x, int y, int length, int orientation) {//orientation 1 = vertical, 0 = hori
        if (orientation == 0) {
            while (length != 0) {//checks along an axis for the lenght of the ship to see if any space conflict
                if (theBoard.getBoard(x, y) == null || theBoard.getBoard(x, y).getStatus() != 1) {//hit edge or other ship
                    return false;
                }
                length--;
                x++;
            }
        } else {
            while (length != 0) {//same bot for orientation vertical
                if (theBoard.getBoard(x, y) == null || theBoard.getBoard(x, y).getStatus() != 1) {
                    return false;
                }
                length--;
                y++;
            }
        }
        return true;
    }
}

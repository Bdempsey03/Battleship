package Logic;

public class Human implements Player{
    /**
     * This class is part of Battleship for Ben Dempsey's CPSC 281 - Winter 2023 Final project.
     *
     * This class is one of the two players for a game of battleship. This player takes in user input to generate moves.
     */



    public Human(Game game){
    }

    /**
     * Fire takes in a move and changes the corresponding tile on a board to be a hit
     * @param move the move made by the user
     * @param brd the board we are firing at
     */
    @Override
    public void fire(Move move, GameBoard brd){
        brd.setBoard(move.getRow(),move.getColumn(), true);
    }

    @Override
    public int[] chooseShipLocations(int a, GameBoard b) {//not used
        return new int[0];
    }

    public boolean legalPlacement(GameBoard board, int x, int y, int length, int orientation) {//orientation 1 = vertical, 0 = hori
        boolean legal = true;
        if (orientation == 0) {
            while (length != 0) {
                if (board.getBoard(x, y) == null || board.getBoard(x, y).getStatus() != 1) {
                    return false;
                }
                length--;
                x++;
            }
        } else {
            while (length != 0) {
                if (board.getBoard(x, y) == null || board.getBoard(x, y).getStatus() != 1) {
                    return false;
                }
                length--;
                y++;
            }
        }
        return true;
    }
}

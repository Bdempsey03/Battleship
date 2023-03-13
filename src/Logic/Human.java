package Logic;

public class Human implements Player{

    private GameBoard board;


    public Human(Game game){
        board = game.getHumanBoard();
    }
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

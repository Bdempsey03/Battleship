package Logic;

public class CPU implements Player{

    private GameBoard board;

    public CPU(Game game){
        board = game.getCpuBoard();
    }

    @Override
    public void fire(Move move){
        board.setBoard(move.getRow(),move.getColumn(), true);
    }
}

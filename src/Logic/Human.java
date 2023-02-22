package Logic;

public class Human implements Player{

    private GameBoard board;


    public Human(Game game){
        board = game.getHumanBoard();
    }
    @Override
    public void fire(Move move){
        board.setBoard(move.getRow(),move.getColumn(), 1);
    }

}

package Logic;

/**
 * This class is part of Battleship for Ben Dempsey's CPSC 281 - Winter 2023 Final project.
 *
 * This is the user inputted move class. Generates a move with a row and a column corresponding to a location
 * on a Board.
 */
public class HumanMove implements Move {
    private int row;
    private int column;

    /**
     * This constructor creates a move from a given row and column. Invalid moves result in 0,0 being guessed
     * @param row row guessed
     * @param column column guessed
     */
    public HumanMove(int row, int column){
        this.row = row; // a -> 1. b -> 2, etc
        this.column = column;
        if(row < 0 || row > 9 || column < 0 || column > 9){//if invalid move then move is 0, 0 for now
            this.row = 0;
            this.column = 0;
        }
        }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public String toString(){
        return ((char)(row + 97)) + "-" + (column + 1);
    }
}
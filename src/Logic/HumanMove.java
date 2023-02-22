package Logic;

public class HumanMove implements Move {
    private int row;
    private int column;

    public HumanMove(int row, int column){
        this.row = row; // a -> 1. b -> 2, etc
        this.column = column;
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
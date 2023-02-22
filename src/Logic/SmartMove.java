package Logic;

import java.util.Random;
public class SmartMove implements Move {
    private Random rnd = new Random();

    private int row;
    private int column;
    public SmartMove(){
        row = (rnd.nextInt(10)); // a-j
        column = rnd.nextInt(10);
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

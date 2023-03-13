package Logic;

import java.awt.*;
import java.util.Random;
public class SmartMove implements Move {
    private Random rnd = new Random();
    private GameBoard opponentsBoard;

    private int row;
    private int column;

    public SmartMove(int level, GameBoard opponentsBoard) {
        this.opponentsBoard = opponentsBoard;

        if (level == 0)
            levelZero();
        if (level == 1)
            levelOne();

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
    public String toString() {
        return ((char) (row + 97)) + "-" + (column + 1);
    }

    private void levelZero() {
        row = (rnd.nextInt(10)); // a-j
        column = rnd.nextInt(10);
    }

    private void levelOne() {
        int length;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (opponentsBoard.getBoard(i, j).isHit() && opponentsBoard.getBoard(i, j).getStatus() != 1) {
                    int x = opponentsBoard.getBoard(i,j).getStatus();
                    if(x==2||x==3)
                        length = 2;
                    if(x==4||x==5)
                        length = 3;
                    if(x==6||x==7)
                        length = 4;
                    if(x==8||x==9)
                        length = 5;
                    if (opponentsBoard.getBoard(i + 1, j) != null && !opponentsBoard.getBoard(i + 1, j).isHit()) {
                        row = i + 1;
                        column = j;
                        return;
                    } else {
                        if (opponentsBoard.getBoard(i - 1, j) != null && !opponentsBoard.getBoard(i - 1, j).isHit()) {
                            row = i - 1;
                            column = j;
                            return;
                        } else {
                            if (opponentsBoard.getBoard(i, j + 1) != null && !opponentsBoard.getBoard(i, j + 1).isHit()) {
                                row = i;
                                column = j + 1;
                                return;
                            } else {
                                if (opponentsBoard.getBoard(i, j - 1) != null && !opponentsBoard.getBoard(i, j - 1).isHit()) {
                                    row = i;
                                    column = j - 1;
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
        row = rnd.nextInt(10);
        column = rnd.nextInt(10);
    }
}

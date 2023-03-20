package Logic;

import com.sun.source.tree.Tree;
import org.w3c.dom.ls.LSOutput;

import java.awt.*;
import java.util.Random;
import java.util.TreeSet;

public class SmartMove implements Move {
    private Random rnd = new Random();
    private GameBoard opponentsBoard;

    private int row;
    private int column;
    private float[][] boardMatrix = new float[10][10];
    private TreeSet<Integer> boardHits;

    public SmartMove(int level, GameBoard opponentsBoard) {
        this.opponentsBoard = opponentsBoard;
        this.boardHits = boardHits;

        if (level == 0)
            levelZero();
        if (level == 1)
            levelOne();
        if(level!=0 && level!=1)
            System.out.println("INVALID MOVE TYPE");
    }
    public SmartMove(int level, GameBoard opponentsBoard, TreeSet<Integer> boardHits) {
        this.opponentsBoard = opponentsBoard;
        this.boardHits = boardHits;

        if (level == 0)
            levelZero();
        if (level == 1)
            levelOne();
        if (level == 2)
            levelTwo();

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
    private void levelTwo(){
        //START OF BUILDING BOARD
        float[][] boardMatrix = new float[10][10];
        for(int i = 0; i < 100; i++){
            boardMatrix[i/10][i%10] = 1;
        }
        for(int i = 0; i < 100; i++){
            if(opponentsBoard.getBoard(i/10,i%10).isHit()) {
                if (opponentsBoard.getBoard(i / 10, i % 10).status != 1) {
                    boardMatrix[i / 10][i % 10] = -1;
                }else {
                    boardMatrix[i / 10][i % 10] = 0;
                }
            }
            if(!opponentsBoard.getBoard(i/10, i%10).isHit()){
                int a = i/10;
                int b = i%10;
                //4,4 - 5,5 - 4,5 - 5,4 are center
                if((a==4 && b == 4) || (a==4 && b == 5) || (a==5 && b == 5) || (a==5 && b == 4))
                    boardMatrix[a][b] = 0.5f;
                else {
                    a = (a + 1) / 2;// range (1-5)
                    b = (b + 1) / 2;//The math works out to be easier with a,b/2 since a 10 x 10 grid has a 2 x 2 middle
                    if(i/10==5 || i/10 == 4)
                        if(i%10<5)
                            boardMatrix[i/10][i%10] = 0.1f*(i%10);
                        else boardMatrix[i/10][i%10] = 0.1f*(10-(i%10));

                    if(i%10==5 || i%10 == 4)
                        if(i%10<5)
                            boardMatrix[i/10][i%10] = 0.1f*(i/10);
                        else boardMatrix[i/10][i%10] = 0.1f*(10-(i/10));

                    if(i/10>5 && i%10>5)
                        boardMatrix[i/10][i%10] = 0.1f*Math.max(10-i/10,10-i%10);
                    if(i/10>5 && i%10<4)
                        boardMatrix[i/10][i%10] = 0.1f*Math.max(10-i/10,i%10);
                    if(i/10<4 && i%10>5)
                        boardMatrix[i/10][i%10] = 0.1f*Math.max(i/10,10-i%10);
                    if(i/10<4 && i%10<4)
                        boardMatrix[i/10][i%10] = 0.1f*Math.max(i/10,i%10);
                }
            }
        }
        //END OF BUILDING BOARD


        printArr(boardMatrix);
        column = rnd.nextInt(10);
        row = rnd.nextInt(10);
    }
    public static void printArr(float[][] arr){
        System.out.println("_____________");
        for(int i = 0; i < 100; i++){
            if(arr[i%10][i/10]-0.1<0.01 && Math.abs(arr[i%10][i/10]-0.2)>0.01) {
                System.out.print(ColorLibrary.WHITE_BACKGROUND);
            }else if(arr[i%10][i/10]-0.2<0.01 && Math.abs(arr[i%10][i/10]-0.3)>0.01) {
                System.out.print(ColorLibrary.WHITE_BACKGROUND);
            }else if(arr[i%10][i/10]-0.3<0.01 && Math.abs(arr[i%10][i/10]-0.4)>0.01) {
                System.out.print(ColorLibrary.YELLOW_BACKGROUND);
            }else if(arr[i%10][i/10]-0.4<0.01 && Math.abs(arr[i%10][i/10]-0.5)>0.01) {
                System.out.print(ColorLibrary.YELLOW_BACKGROUND);
            }else if(arr[i%10][i/10]-0.5<0.01 && Math.abs(arr[i%10][i/10]-1)>0.01){
                System.out.print(ColorLibrary.BLACK_BACKGROUND);
            }else System.out.print(ColorLibrary.GREEN_BACKGROUND);
            if(arr[i%10][i/10]<0)
                System.out.print(ColorLibrary.RED_BACKGROUND);
            if(arr[i%10][i/10]<0.01 && arr[i%10][i/10]>-0.01 )
                System.out.print(ColorLibrary.WHITE_BACKGROUND_BRIGHT);
//            if



            System.out.printf("[%1.3f]",arr[i%10][i/10]);
            if(i%10==9)
                System.out.print(ColorLibrary.RESET + "\n");
        }
        System.out.println("\n____________");
    }
}

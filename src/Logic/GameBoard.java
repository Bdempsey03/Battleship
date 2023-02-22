package Logic;

public class GameBoard {
    private int[][] board;

    public GameBoard(int x, int y){
        board = new int[x][y];
        defaultSetup();
    }

    public void defaultSetup(){
        for(int i = 0; i < 100; i++){
            board[i/10][i%10] = 0;
        }
    }

    public int getBoard(int x, int y) {
        return board[x][y];
    }

    public void setBoard(int row, int column, int i) {
        board[row][column] = i;
    }

}

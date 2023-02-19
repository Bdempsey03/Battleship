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
            board[i%8+i/50][i%9] = 1;
        }
    }

    public int getBoard(int x, int y) {
        return board[x][y];
    }
}

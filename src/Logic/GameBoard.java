package Logic;


public class GameBoard {
/*
Key:
    1 = no ship
    2 = horizontal two boat
    3 = vertical 2 boat
    4 = horizontal 3 boat
    5 = vertical 3 boat
    6 = horizontal 4 boat
    7 = vertical 4 boat
    8 = horizontal 5 boat
    9 = vertical 5 boat

 */

    private Tile[][] board;
    private boolean isHuman;

    public GameBoard(int x, int y, boolean isHuman){
        board = new Tile[x][y];
        this.isHuman = isHuman;
        defaultSetup();
    }

    public void defaultSetup(){
        for(int i = 0; i < 100; i++){
            board[i/10][i%10] = new Tile(1, false);
        }
      }

    public Tile getBoard(int x, int y) {
        return board[x][y];
    }

    /**
     * To set a spot as hit or not hit
     * @param row
     * @param column
     * @param hit
     */
    public void setBoard(int row, int column, boolean hit) {
        board[row][column].hit = hit;
    }

    /**
     * To set boats
     * @param row
     * @param column
     * @param status
     */
    public void setBoard(int row, int column, int status){
        board[row][column].status = status;
    }
    public void addShip(int headLocationX, int headLocationY, int orientation, int length){

        int status = 0;

        switch (length) {
            case 2 -> status = 2;
            case 3 -> status = 4;
            case 4 -> status = 6;
            case 5 -> status = 8;
        }
        if(orientation == 1)//if vertical
            status++;


        if(orientation == 0){
            for(int i = 0; i < length; i++)
                setBoard(headLocationX + i, headLocationY, status);
            }else{
            for(int i = 0; i < length; i++) {
                setBoard(headLocationX, headLocationY + i, status);
            }
        }
    }
}

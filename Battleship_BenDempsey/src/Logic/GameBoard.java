package Logic;


/**
 * This class is part of Battleship for Ben Dempsey's CPSC 281 - Winter 2023 Final project.
 *
 * The logical board of a battleship game.
 */
public class GameBoard {
/*
Key:
    1 = no ship
    2 = horizontal two boat
    3 = vertical 2 boat
    4 = horizontal 3 cruiser
    5 = vertical 3 cruiser
    6 = horizontal 4 boat
    7 = vertical 4 boat
    8 = horizontal 5 boat
    9 = vertical 5 boat
    10 = horizontal submarine
    11 = vertical submarine

 */

    private Tile[][] board;
    private boolean isHuman;

    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_RESET = "\u001B[0m";


    private boolean subPlaced = false;



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
        if(x<0||x>11||y<0||y>11)
            return null;
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
            case 3 -> status = subPlaced?4:10;//if submarine not placed then we set the value to 4 (cruiser ID), else 10
            case 4 -> status = 6;
            case 5 -> status = 8;
        }
        if(length==3)
            subPlaced=true;
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
    public String toString(){
        String s = "";

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++) {
                if(board[i][j].isHit())
                    s+=ANSI_RED_BACKGROUND;
                else
                    s+=ANSI_BLUE_BACKGROUND;
                s +="[" + board[i][j] + "]" + ANSI_RESET;
            }
            s+="\n";
        }


        return s;
    }
    public boolean isHuman(){
        return isHuman;
    }
    public boolean hasShip(int ship){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(board[i][j].getStatus()==ship && !board[i][j].isHit()){
                    return true;
                }
            }
        }
        return false;
    }
}

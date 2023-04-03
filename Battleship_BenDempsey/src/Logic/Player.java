package Logic;

/**
 * This interface is part of Battleship for Ben Dempsey's CPSC 281 - Winter 2023 Final project.
 *
 * Player interface. Two are needed for a game of battleship. Implemented by Human and CPU
 */
public interface Player {
    void fire(Move move, GameBoard board);//to guess a location on the board
    int[] chooseShipLocations(int a, GameBoard b);//to choose valid ship locations
}


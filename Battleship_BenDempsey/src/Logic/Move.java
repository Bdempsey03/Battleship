package Logic;

/**
 * This class is part of Battleship for Ben Dempsey's CPSC 281 - Winter 2023 Final project.
 *
 * Interface for Moves. Implemented by SmartMove and HumanMove
 */
public interface Move {

    int getRow();
    int getColumn();

}

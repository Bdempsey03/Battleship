package Logic;

/**
 * This class is part of Battleship for Ben Dempsey's CPSC 281 - Winter 2023 Final project.
 *
 * Segments of the Battleship board. Board is a 10x10 grid of these
 */
public class Tile {
    public int status; // number corresponds to ship (see GameBoard for legend)
    public boolean hit; //has this tile been guessed

    public Tile(int status, boolean hit) {
        this.status = status;
        this.hit = hit;
    }

    public String toString() {
        return status + "";
    }

    public int getStatus() {
        return status;
    }

    public boolean isHit() {
        return hit;
    }
}

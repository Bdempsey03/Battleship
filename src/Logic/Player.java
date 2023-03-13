package Logic;

public interface Player {
    void fire(Move move, GameBoard board);
    int[] chooseShipLocations(int a, GameBoard b);
}


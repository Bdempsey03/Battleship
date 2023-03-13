package Logic;

public class Tile {
    public int status;
    public boolean hit;

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

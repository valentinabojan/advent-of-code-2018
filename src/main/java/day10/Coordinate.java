package day10;

public class Coordinate {

    private int x = 0;
    private int y = 0;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(String x, String y) {
        this.x = Integer.valueOf(x.trim());
        this.y = Integer.valueOf(y.trim());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

package day25;

public class Coordinate4D {

    private int x = 0;
    private int y = 0;
    private int z = 0;
    private int w = 0;

    public Coordinate4D(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Coordinate4D(String x, String y, String z, String w) {
        this.x = Integer.valueOf(x.trim());
        this.y = Integer.valueOf(y.trim());
        this.z = Integer.valueOf(z.trim());
        this.w = Integer.valueOf(w.trim());
    }

    public int getW() {
        return w;
    }

    public Integer getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }


    public int getZ() {
        return z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate4D that = (Coordinate4D) o;

        if (x != that.x) return false;
        if (y != that.y) return false;
        if (z != that.z) return false;
        return w == that.w;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        result = 31 * result + w;
        return result;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + "," + z + "," + w + ")";
    }
}

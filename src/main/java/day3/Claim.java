package day3;

public class Claim {

    private int id;
    private int leftOffset;
    private int topOffset;
    private int width;
    private int height;

    public Claim(int id, int leftOffset, int topOffset, int width, int height) {
        this.id = id;
        this.leftOffset = leftOffset;
        this.topOffset = topOffset;
        this.width = width;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public int getLeftOffset() {
        return leftOffset;
    }

    public int getTopOffset() {
        return topOffset;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

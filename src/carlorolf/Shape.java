package carlorolf;

/**
 * A rectangular shape
 */
public class Shape {
    private double width;
    private double height;

    /**
     * Creates a rectangle shape.
     */
    public Shape(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}

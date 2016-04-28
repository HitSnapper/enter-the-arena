package carlorolf;

public class Shape {
    private double width;
    private double height;

    /**
     * Creates a rectangle shape.
     *
     * @param width
     * @param height
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

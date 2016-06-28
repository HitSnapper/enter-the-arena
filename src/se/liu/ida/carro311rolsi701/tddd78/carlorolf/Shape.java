package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import java.util.List;

/**
 * A rectangular shape
 */
public class Shape {
    private List<Vector> nodes;

    public Shape(List<Vector> nodes) {
        this.nodes = nodes;
    }

    public List<Vector> getNodes() {
        return nodes;
    }

    //Gives width on x-axis
    public double getWidth() {
        Vector leftmost = new Vector(0, 0), rightmost = new Vector(0, 0);
        for (Vector node : nodes) {
            if (node.getX() < leftmost.getX()) {
                leftmost = node;
            }
            if (node.getX() > rightmost.getX()) {
                rightmost = node;
            }
        }
        return rightmost.getX() - leftmost.getX();
    }

    //Gives height on y-axis
    public double getHeight() {
        Vector topmost = new Vector(0, 0), bottommost = new Vector(0, 0);
        for (Vector node : nodes) {
            if (node.getY() < topmost.getY()) {
                topmost = node;
            }
            if (node.getY() > bottommost.getY()) {
                bottommost = node;
            }
        }
        return bottommost.getY() - topmost.getY();
    }
}

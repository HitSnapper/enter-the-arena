package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import java.util.ArrayList;
import java.util.List;

/**
 * A polygonial convex shape
 */
public class Shape {
    private List<Vector> nodes;
    private double width;
    private double height;

    public Shape(List<Vector> nodes) {
        this.nodes = nodes;
        updateSize();
    }

    public List<Vector> getNodes() {
        return nodes;
    }

    private void updateSize(){
        Vector topmost = new Vector(0, 0), bottommost = new Vector(0, 0), leftmost = new Vector(0, 0), rightmost = new Vector(0, 0);
        for (Vector node : nodes) {
            if (node.getY() < topmost.getY()) {
                topmost = node;
            }
            if (node.getY() > bottommost.getY()) {
                bottommost = node;
            }
            if (node.getX() < leftmost.getX()) {
                leftmost = node;
            }
            if (node.getX() > rightmost.getX()) {
                rightmost = node;
            }
        }
        this.height = bottommost.getY() - topmost.getY();
        this.width = rightmost.getX() - leftmost.getX();
    }

    //Gives width on x-axis
    public double getWidth() {
        return this.width;
    }

    //Gives height on y-axis
    public double getHeight() {
        return this.height;
    }
}

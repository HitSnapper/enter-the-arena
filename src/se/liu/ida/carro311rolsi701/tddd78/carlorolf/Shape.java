package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import java.util.ArrayList;
import java.util.List;

/**
 * A polygonial convex shape
 */
public class Shape {
    private List<Vector> nodes;

    public Shape(List<Vector> nodes) {
        this.nodes = nodes;
        giftWrap();
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

    private Vector getLeftMost(){
        Vector leftmost = nodes.get(0);
        for (Vector node : nodes) {
            if (node.getX() < leftmost.getX()) {
                leftmost = node;
            }
            else if (node.getX() == leftmost.getX() && node.getY() >= leftmost.getY()){
                leftmost = node;
            }
        }
        return leftmost;
    }

    private Vector getBottomLeftMost(){
        Vector leftmost = nodes.get(0);
        for (Vector node : nodes) {
            if (node.getX() <= leftmost.getX() && node.getY() <= leftmost.getY()) {
                leftmost = node;
            }
        }
        return leftmost;
    }

    private boolean isRightMost(Vector test){
        Vector rightmost = new Vector(0, 0);
        for (Vector node : nodes) {
            if (node.getX() > rightmost.getX()) {
                rightmost = node;
            }
            else if(node.getX() >= rightmost.getX() && node.getY() <= rightmost.getY()){
                rightmost = node;
            }
        }
        return test == rightmost;
    }

    private void giftWrap(){
        List<Vector> res = new ArrayList<>();
        boolean foundRightMost = false;
        Vector node = getLeftMost();
        res.add(node);
        while (node != getBottomLeftMost()){
            Vector mostLeft = null;
            for (Vector vector : nodes) {
                double inclination = node.getInclination(vector);
                if (node != vector && !foundRightMost && (mostLeft == null || inclination > node.getInclination(mostLeft)) && node.getX() - vector.getX() <= 0) {
                    mostLeft = vector;
                } else if (node != vector && foundRightMost && (mostLeft == null || inclination > node.getInclination(mostLeft)) && node.getX() - vector.getX() > 0) {
                    mostLeft = vector;
                }
            }
            if (isRightMost(mostLeft)){
                foundRightMost = true;
            }
            res.add(mostLeft);
            node = mostLeft;
        }
        nodes = res;
    }
}

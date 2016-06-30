package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import java.util.ArrayList;
import java.util.List;

/**
 * A polygonial convex shape
 */
public class Shape {
    private List<Vector> nodes;

    public Shape(List<Vector> nodes) {
        this.nodes = giftWrap(nodes);
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

    private Vector getLeftMost(List<Vector> vectors){
        Vector leftmost = vectors.get(0);
        for (Vector node : vectors) {
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

    //NÅGOT FEL
    private boolean oneSideEmpty(Vector v1, Vector v2, List<Vector> vectors){
        boolean onLeft = false;
        boolean onRight = false;
        double dY = v1.getY() - v2.getY();
        double dX = v1.getX() - v2.getX();

        if (dX != 0) {
            double k = dY/dX;
            double m = v1.getY() - k*v1.getX();
            for (Vector node : vectors) {
                double value = k * node.getX() + m;
                if (node.getY() > value) {
                    onLeft = true;
                } else if (node.getY() < value) {
                    onRight = true;
                }
            }
        }
        else{
            for (Vector node : vectors) {
                if (node.getX() < v1.getX()){
                    onLeft = true;
                }
                else if (node.getX() > v1.getX()){
                    onRight = true;
                }
            }
        }
        return onLeft != onRight;
    }

    private List<Vector> giftWrap(List<Vector> vectors){
        List<Vector> res = new ArrayList<>();
        Vector node = getLeftMost(vectors);
        if (node == new Vector(-8.2, -3.5)){
            System.out.println("AH!");
        }
        res.add(node);
        Vector leftMost = getLeftMost(vectors);
        while (true){
            Vector temp = null;
            for (Vector vector : vectors) {
                if (!res.contains(vector) && oneSideEmpty(node, vector, vectors)){
                    if (temp == null) {
                        temp = vector;
                    }
                    else if(Math.abs(node.getDistance(vector)) > Math.abs(node.getDistance(temp))){
                        temp = vector;
                    }
                }
                else if (vector == leftMost && res.size() > 2 && oneSideEmpty(node, vector, vectors)){
                    return res;
                }
            }
            if (temp == null){
                System.out.println(node);
            }
            res.add(temp);
            node = temp;
        }
    }
}

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

    private boolean oneSideEmpty(Vector v1, Vector v2, List<Vector> vectors){
        boolean onLeft = false;
        boolean onRight = true;
        for (Vector vector : vectors) {
            if (((v2.getX() - v1.getX())*(vector.getY() - v1.getY()) - (v2.getY() - v1.getY())*(vector.getX() - v1.getX())) > 0){
                onLeft = true;
            }
            else if(((v2.getX() - v1.getX())*(vector.getY() - v1.getY()) - (v2.getY() - v1.getY())*(vector.getX() - v1.getX())) < 0){
                onRight = true;
            }
        }
        return onLeft != onRight;
    }

    private List<Vector> giftWrap(List<Vector> vectors){
        List<Vector> copy = new ArrayList<>(vectors);
        List<Vector> res = new ArrayList<>();
        Vector node = getLeftMost(vectors);
        res.add(node);
        Vector leftMost = getLeftMost(vectors);
        while (true){
            Vector temp = null;
            for (Vector vector : copy) {
                if (!res.contains(vector) && oneSideEmpty(node, vector, copy)){
                    if (temp == null) {
                        temp = vector;
                    }
                    else if(Math.abs(node.getDistance(vector)) > Math.abs(node.getDistance(temp))){
                        temp = vector;
                    }
                }
                else if (vector == leftMost && res.size() > 2 && oneSideEmpty(node, vector, copy)){
                    return res;
                }
            }
            if (temp == null){
                res.remove(node);
                copy.remove(node);
                node = res.get(res.size() - 1);
            }
            else {
                res.add(temp);
                node = temp;
            }
        }
    }
}

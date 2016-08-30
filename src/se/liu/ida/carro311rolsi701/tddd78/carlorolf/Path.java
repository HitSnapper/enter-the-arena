package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HitSnapper on 2016-07-05.
 */
public class Path {
    private List<Vector> vectors;
    private double length;

    public Path() {
        vectors = new ArrayList<>();
        length = 0;
    }

    public Path(List<Node> nodes){
        this.vectors = new ArrayList<>();
        for (Node node : nodes) {
            if (vectors.size() > 0){
                length = 0;
            }
            this.vectors.add(node.getCoords());
        }
    }

    public void add(Vector vector){
        if (vectors.size() > 0){
            length += vectors.get(vectors.size() - 1).getDistance(vector);
        }
        vectors.add(vector);
    }

    public double getLength() {
        return length;
    }

    public int size(){
        return vectors.size();
    }

    public Vector getLast(){
        return vectors.get(vectors.size() - 1);
    }

    public Vector getFirst(){
        return vectors.get(0);
    }

    public List<Vector> getNodes() {
        return vectors;
    }

    public boolean isEmpty(){
        return vectors.isEmpty();
    }
}

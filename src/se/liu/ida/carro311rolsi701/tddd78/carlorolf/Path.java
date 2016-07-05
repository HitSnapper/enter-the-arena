package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HitSnapper on 2016-07-05.
 */
public class Path {
    private List<Vector> nodes;
    private double length;

    public Path() {
        nodes = new ArrayList<>();
        length = 0;
    }

    public Path(Path path) {
        nodes = path.getNodes();
        length = path.getLength();
    }

    public void add(Vector vector){
        if (nodes.size() > 0){
            length += nodes.get(nodes.size() - 1).getDistance(vector);
        }
        nodes.add(vector);
    }

    public double getLength() {
        return length;
    }

    public int size(){
        return nodes.size();
    }

    public Vector getLast(){
        return nodes.get(nodes.size() - 1);
    }

    public List<Vector> getNodes() {
        return nodes;
    }
}

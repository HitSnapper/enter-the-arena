package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private Vector coords;
    private List<Node> connectedNodes;

    public Node(Vector coords) {
        this.coords = coords;
        connectedNodes = new ArrayList<>();
    }

    public Vector getCoords() {
        return coords;
    }

    public void addConnection(Node node){
        if (!connectedNodes.contains(node) && node != this) {
            connectedNodes.add(node);
            node.addConnection(this);
        }
    }

    public void removeConnection(Node node){
        if (connectedNodes.contains(node)) {
            connectedNodes.remove(node);
            node.removeConnection(this);
        }
    }

    public void removeAllConnections(){
        for (Node node : connectedNodes) {
            removeConnection(node);
        }
    }

    public boolean connected(Node node){
        return connectedNodes.contains(node);
    }

    public List<Node> connections(){
        return connectedNodes;
    }

    public double getX(){
        return  coords.getX();
    }

    public double getY(){
        return coords.getY();
    }
}

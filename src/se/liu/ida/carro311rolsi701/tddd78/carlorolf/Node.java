package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import java.util.ArrayList;
import java.util.List;

//TODO: Should extend vector class!
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
        for (Node node : new ArrayList<>(connectedNodes)) {
            removeConnection(node);
        }
    }

    public boolean connected(Node node){
        return connectedNodes.contains(node);
    }

    public List<Node> neighbours(){
        return connectedNodes;
    }

    public boolean deadEnd(){
        return neighbours().size() == 1;
    }

    public boolean leadsToDeadEnd(Node cameFrom, Node goal){
        int size = neighbours().size();
        if (connected(goal)){
            return false;
        }
        if (size == 1){
            return true;
        }
        else if (size == 2){
            for (Node node : connectedNodes) {
                if (node != cameFrom){
                    return node.leadsToDeadEnd(this, goal);
                }
            }
        }
        return false;
    }

    public double getX(){
        return  coords.getX();
    }

    public double getY(){
        return coords.getY();
    }

    @Override
    public boolean equals(Object object){
        if (!(object instanceof Node)){
            return false;
        }
        else {
            Node node = (Node)object;
            return getX() == node.getX() && getY() == node.getY();
        }
    }
}

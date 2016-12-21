package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import java.util.ArrayList;
import java.util.List;

//TODO: Should extend vector class!
public class Node extends Vector{
    private List<Node> connectedNodes;

    public Node(final double x, final double y){
        super(x, y);
    }

    public Node(Vector coords) {
        super(coords.getX(), coords.getY());
        connectedNodes = new ArrayList<>();
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

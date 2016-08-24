package se.liu.ida.carro311rolsi701.tddd78.carlorolf;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linnea Sievert on 2016-08-24.
 */
public class Graph {
    private List<Node> nodes;
    private List<Line> edges;
    private Arena arena;

    public Graph(List<Node> nodes, Arena arena) {
        this.arena = arena;
        this.nodes = nodes;
        edges = new ArrayList<>();
        for (Node node : nodes) {
            for (Node node1 : node.connections()) {
                Line line = new Line(node, node1);
                Line line1 = new Line(node1, node);
                if (!(edges.contains(line) || edges.contains(line1))){
                    edges.add(line);
                }
            }
        }
        addLineOfSightConnections();
    }

    private void addLineOfSightConnections(){
        List<Line> res = new ArrayList<>();
        for (Node node : nodes) {
            for (Node node1 : nodes) {
                if (!node.connected(node1) && lineOfSight(node, node1)){
                    node.addConnection(node1);
                    Line line = new Line(node, node1);
                    res.add(line);
                }
            }
        }
        //TODO: Remove each line that is somehow connected to corresponding nodes indirectly in the original nodes list.
        //Perhaps have a indirect connect list in each node object?
        for (Line line : res) {
            edges.add(line);
        }
    }

    public boolean lineOfSight(Node n1, Node n2){
        Line line = new Line(n1, n2);
        for (Line line1 : edges) {
            if (line.intersectsLine(line1)){
                return false;
            }
            else if (line.equals(line1)){
                return false;
            }
        }
        return true;
    }

    public void draw(Graphics2D screen, Vector target, Dimension tileSize, double screenWidth, double screenHeight) {
        int numberOfPlayers = arena.getNumberOfAlivePlayers();
        if (numberOfPlayers == 0) {
            numberOfPlayers = 1;
        }

        screen.setColor(Color.CYAN);
        for (Line line : edges) {
            int startX = (int)(tileSize.getWidth() * (line.getStartX() - target.getX()) + screenWidth / numberOfPlayers);
            int startY = (int)(tileSize.getHeight() * (line.getStartY() - target.getY()) + screenHeight);
            int endX = (int)(tileSize.getWidth() * (line.getEndX() - target.getX()) + screenWidth / numberOfPlayers);
            int endY = (int)(tileSize.getHeight() * (line.getEndY() - target.getY()) + screenHeight);
            screen.drawLine(startX, startY, endX, endY);
        }
    }
}

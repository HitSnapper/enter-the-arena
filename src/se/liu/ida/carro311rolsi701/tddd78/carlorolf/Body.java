package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HitSnapper on 2016-06-28.
 */
public class Body {
    private Shape shape;
    private Vector coords;
    private ArenaObject owner;
    private boolean movable;

    public Body(Vector coords, Shape shape, boolean movable) {
        this.shape = shape;
        this.coords = coords;
        this.movable = movable;
    }

    public void setOwner(ArenaObject owner){
        this.owner = owner;
    }

    public boolean isMovable() {
        return movable;
    }

    public double getWidth(){
        return shape.getWidth();
    }

    public double getHeight(){
        return shape.getHeight();
    }

    public void draw(Graphics2D screen, Vector target, Dimension tileSize, double screenWidth, double screenHeight) {
        int numberOfPlayers = owner.getArena().getNumberOfAlivePlayers();
        if (numberOfPlayers == 0) {
            numberOfPlayers = 1;
        }

        //double objX = (getX() - width / 2.0);
        //double objY = (getY() - height / 2.0);
        //int xPos = (int) (tileSize.getWidth() * (objX - target.getX()) + screenWidth/numberOfPlayers);
        //int yPos = (int) (tileSize.getHeight() * (objY - target.getY()) + screenHeight);

        for (int i = 0; i < shape.getNodes().size(); i++) {
            screen.drawLine(
                    (int) (tileSize.getWidth() * (shape.getNodes().get(i).getX() + getX() - target.getX()) + screenWidth / numberOfPlayers),    // X of first line
                    (int) (tileSize.getHeight() * (shape.getNodes().get(i).getY() + getY() - target.getY()) + screenHeight),                      // Y of first line
                    (int) (tileSize.getWidth() * (shape.getNodes().get((i + 1) % shape.getNodes().size()).getX() + getX() - target.getX()) + screenWidth / numberOfPlayers),     // X of second line
                    (int) (tileSize.getHeight() * (shape.getNodes().get((i + 1) % shape.getNodes().size()).getY() + getY() - target.getY()) + screenHeight)                      // Y of second line
            );
        }
    }

    public Shape getShape() {
        return shape;
    }

    public double getX() {
        return coords.getX();
    }

    public double getY() {
        return coords.getY();
    }

    public Vector getCoords(){
        return coords;
    }

    public void addCoords(Vector vector){
        addX(vector.getX());
        addY(vector.getY());
    }

    public void addX(double add){
        coords.add(add, 0);
    }

    public void addY(double add){
        coords.add(0, add);
    }

    public List<Vector> getVectors(){
        List<Vector> res = new ArrayList<>();
        for (Vector vector : shape.getNodes()) {
            res.add(new Vector(getX() + vector.getX(), getY() + vector.getY()));
        }
        return res;
    }

    /**
     * Returns a list of vectors of the body where each vector is moved from it's body's origo with distance expanded.
     * @param expanded
     * @return
     */
    public List<Vector> getVectors(double expanded){
        List<Vector> res = new ArrayList<>();
        for (Vector vector : shape.getNodes()) {
            int xSign = (int)(vector.getX()/Math.abs(vector.getX()));
            int ySign = (int)(vector.getY()/Math.abs(vector.getY()));
            double d = Math.pow(2*Math.pow(expanded, 2), 0.5);
            double x = getX() + vector.getX() + xSign*d;
            double y = getY() + vector.getY() + ySign*d;
            res.add(new Vector(x, y));
        }
        return res;
    }

    /**
     * Returns a list of nodes of the body where each node is moved from it's body's origo with distance expanded.
     * @param expanded
     * @return
     */
    public List<Node> getNodes(double expanded) {
        List<Node> res = new ArrayList<>();
        for (Vector vector : getVectors(expanded)) {
            Node node = new Node(vector);
            res.add(node);
        }
        for (int i = 0; i < res.size(); i++){
            res.get(i).addConnection(res.get(Math.floorMod(i+1, res.size())));
        }
        return res;
    }
}

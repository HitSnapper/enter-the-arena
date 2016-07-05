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

    public void draw(Graphics2D screen, Vector target, Dimension tileSize, int screenWidth, int screenHeight) {
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

    public List<Vector> getNodes(){
        List<Vector> res = new ArrayList<>();
        for (Vector vector : shape.getNodes()) {
            res.add(new Vector(getX() + vector.getX(), getY() + vector.getY()));
        }
        return res;
    }

    /**
     * Returns all the nodes with multiplication by times, in other words, returns the body with upscaled shape.
     * @param times
     * @return
     */
    public List<Vector> getNodes(double times){
        List<Vector> res = new ArrayList<>();
        for (Vector vector : shape.getNodes()) {
            res.add(new Vector(getX() + vector.getX()*times, getY() + vector.getY()*times));
        }
        return res;
    }
}

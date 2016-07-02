package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by HitSnapper on 2016-06-28.
 */
public class Body {
    private Shape shape;
    private Vector coords;
    private Arena arena;

    public Body(Vector coords, Shape shape, Arena arena) {
        this.shape = shape;
        this.coords = coords;
        this.arena = arena;
    }

    public double getWidth(){
        return shape.getWidth();
    }

    public double getHeight(){
        return shape.getHeight();
    }

    public void draw(Graphics2D screen, Vector target, Dimension tileSize, int screenWidth, int screenHeight) {
        int numberOfPlayers = arena.getNumberOfAlivePlayers();
        if (numberOfPlayers == 0) {
            numberOfPlayers = 1;
        }

        for (int i = 0; i < shape.getNodes().size(); i++) {
            screen.drawLine(
                    (int) ((tileSize.getWidth() * (shape.getNodes().get(i).getX() + getX() - target.getX()) + screenWidth) / numberOfPlayers),    // X of first line
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

}

package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by HitSnapper on 2016-06-28.
 */
public class Body {
    private Shape shape;
    private double x, y;
    private Arena arena;

    public Body(double x, double y, Shape shape, Arena arena) {
        this.shape = shape;
        this.x = x;
        this.y = y;
        this.arena = arena;
    }

    public void draw(Graphics2D screen, Vector target, Dimension tileSize, int screenWidth, int screenHeight) {
        int numberOfPlayers = arena.getNumberOfAlivePlayers();
        if (numberOfPlayers == 0) {
            numberOfPlayers = 1;
        }
        /*
        for (Vector node : shape.getNodes()) {
            for (Vector node1 : shape.getNodes()) {
                if (node != node1) {
                    screen.drawLine(
                            (int) ((tileSize.getWidth()*(node.getX() + x - target.getX()) + screenWidth) / numberOfPlayers),    // X of first line
                            (int) (tileSize.getHeight()*(node.getY() + y - target.getY()) + screenHeight),                      // Y of first line
                            (int) (tileSize.getWidth()*(node1.getX() + x - target.getX()) + screenWidth / numberOfPlayers),     // X of second line
                            (int) (tileSize.getHeight()*(node1.getY() + y - target.getY()) + screenHeight)                      // Y of second line
                            );
                }
            }
        }
            */

        for (int i = 0; i < shape.getNodes().size(); i++) {
            if (i == shape.getNodes().size() - 1) {
                screen.drawLine(
                        (int) ((tileSize.getWidth() * (shape.getNodes().get(i).getX() + x - target.getX()) + screenWidth) / numberOfPlayers),    // X of first line
                        (int) (tileSize.getHeight() * (shape.getNodes().get(i).getY() + y - target.getY()) + screenHeight),                      // Y of first line
                        (int) (tileSize.getWidth() * (shape.getNodes().get(0).getX() + x - target.getX()) + screenWidth / numberOfPlayers),     // X of second line
                        (int) (tileSize.getHeight() * (shape.getNodes().get(0).getY() + y - target.getY()) + screenHeight)                      // Y of second line
                );
            } else {
                screen.drawLine(
                        (int) ((tileSize.getWidth() * (shape.getNodes().get(i).getX() + x - target.getX()) + screenWidth) / numberOfPlayers),    // X of first line
                        (int) (tileSize.getHeight() * (shape.getNodes().get(i).getY() + y - target.getY()) + screenHeight),                      // Y of first line
                        (int) (tileSize.getWidth() * (shape.getNodes().get(i + 1).getX() + x - target.getX()) + screenWidth / numberOfPlayers),     // X of second line
                        (int) (tileSize.getHeight() * (shape.getNodes().get(i + 1).getY() + y - target.getY()) + screenHeight)                      // Y of second line
                );
            }
        }
    }

    public Shape getShape() {
        return shape;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void addX(double add){
        x += add;
    }

    public void addY(double add){
        y += add;
    }

}

package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import java.awt.*;

/**
 * VisibleObject has an image, width, height, coordinates and a method to draw it.
 */
public abstract class VisibleObject {
    protected Image image;
    protected Vector coords;
    protected double width;
    protected double height;
    protected Arena arena;
    private double x;
    private double y;

    protected VisibleObject(double x, double y, double width, double height, Image image, Arena arena) {
        this.arena = arena;
        this.y = y;
        this.x = x;
        coords = new Vector(x, y);
        this.width = width;
        this.height = height;
        this.image = image;
        assert (image != null);
    }

    public void setImage(Image image){
        this.image = image;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public void setX(final double x) {
        this.x = x;
    }

    public void setXRelative(final double x) {
        this.x += x;
    }

    public void setYRelative(final double y) {
        this.y += y;
    }

    public void setY(final double y) {
        this.y = y;
    }

    public double getHeight() {
        return height;
    }

    public void draw(Graphics2D screen, Vector target, Dimension tileSize, int screenWidth, int screenHeight) {
        int numberOfPlayers = arena.getNumberOfAlivePlayers();
        if (numberOfPlayers == 0){
            numberOfPlayers = 1;
        }
        double objX = (x - width / 2);
        double objY = (y - height / 2);
        int xPos = (int) (tileSize.getWidth() * (objX - target.getX()) + screenWidth/numberOfPlayers);
        int yPos = (int) (tileSize.getHeight() * (objY - target.getY()) + screenHeight);
        screen.drawImage(image, xPos, yPos, (int) (tileSize.getWidth() * width), (int) (tileSize.getHeight() * height), null);
    }

    public void update(double deltaTime){
        x = coords.getX();
        y = coords.getY();
    }
}

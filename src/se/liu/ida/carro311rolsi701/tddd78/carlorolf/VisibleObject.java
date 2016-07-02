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

    protected VisibleObject(Vector coords, double width, double height, Image image, Arena arena) {
        this.arena = arena;
        this.coords = coords;
        this.width = width;
        this.height = height;
        this.image = image;
        assert (image != null);
    }

    public void setImage(Image image){
        this.image = image;
    }

    public double getX() {
        return coords.getX();
    }

    public double getY() {
        return coords.getY();
    }

    public double getWidth() {
        return width;
    }

    public void setX(final double x) {
        this.coords.setX(x);
    }

    public void addX(final double x) {
        this.coords.add(x, 0);
    }

    public void addY(final double y) {
        this.coords.add(0, y);
    }

    public void setY(final double y) {
        this.coords.setY(y);
    }

    public double getHeight() {
        return height;
    }

    public void draw(Graphics2D screen, Vector target, Dimension tileSize, int screenWidth, int screenHeight) {
        int numberOfPlayers = arena.getNumberOfAlivePlayers();
        if (numberOfPlayers == 0){
            numberOfPlayers = 1;
        }
        double objX = (getX() - width / 2.0);
        double objY = (getY() - height / 2.0);
        int xPos = (int) (tileSize.getWidth() * (objX - target.getX()) + screenWidth/numberOfPlayers);
        int yPos = (int) (tileSize.getHeight() * (objY - target.getY()) + screenHeight);
        screen.drawImage(image, xPos, yPos, (int) (tileSize.getWidth() * width), (int) (tileSize.getHeight() * height), null);
    }

    public void update(double deltaTime){
    }
}

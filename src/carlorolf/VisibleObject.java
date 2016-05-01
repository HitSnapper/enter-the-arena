package carlorolf;

import carlorolf.friendlycharacters.Player;

import java.awt.*;

/**
 * VisibleObject has an image, width, height, coordinates and a method to draw it.
 */
public abstract class VisibleObject {
    protected Image image;
    protected double x;
    protected double y;
    protected double width;
    protected double height;
    protected Arena arena;

    protected VisibleObject(double x, double y, double width, double height, Image image, Arena arena) {
        this.arena = arena;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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

    public void draw(Graphics screen, Dimension tileSize, int screenWidth, int screenHeight) {
        Player player = arena.getPlayer();

        double objX = (x - width / 2);
        double objY = (y - height / 2);
        int xPos = (int) (tileSize.getWidth() * (objX - player.getX()) + screenWidth);
        int yPos = (int) (tileSize.getHeight() * (objY - player.getY()) + screenHeight);
        screen.drawImage(image, xPos, yPos, (int) (tileSize.getWidth() * width), (int) (tileSize.getHeight() * height), null);
    }

    public abstract void update(double deltaTime);
}

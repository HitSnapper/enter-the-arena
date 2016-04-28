package carlorolf;

import java.awt.*;

abstract class VisibleObject {
    protected Image image;
    protected double x;
    protected double y;
    protected double width;
    protected double height;
    protected Arena arena;

    VisibleObject(double x, double y, double width, double height, Image image, Arena arena) {
        this.arena = arena;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    public Image getImage() {
        return image;
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

    public void draw(Graphics screen, Dimension tileSize) {
        Player player = arena.getPlayer();
        int aWidth = arena.getWidth();
        int aHeight = arena.getHeight();

        final double half = 0.5;

        double objX = (x - width / 2);
        double objY = (y - height / 2);
        int xPos = (int) (tileSize.getWidth() * (objX - player.getX() + (aWidth + 2) / 2));
        int yPos = (int) (tileSize.getHeight() * (objY - player.getY() + (aHeight + half) / 2));
        screen.drawImage(image, xPos, yPos, (int) (tileSize.getWidth() * width), (int) (tileSize.getHeight() * height), null);
    }

    public abstract void update(double deltaTime);
}

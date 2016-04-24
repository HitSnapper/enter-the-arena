package carlorolf;

import java.awt.*;

abstract class VisibleObject {
    protected Image image;
    protected Image armor_pic;
    protected double x;
    protected double y;
    protected double width;
    protected double height;

    public VisibleObject(double x, double y, double width, double height, Image image, Image armor_pic) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
        this.armor_pic = armor_pic;
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
        int x_pos = (int) (tileSize.getWidth() * (this.getX() - width / 2));
        int y_pos = (int) (tileSize.getHeight() * (this.getY() - height / 2));
        screen.drawImage(image, x_pos, y_pos, (int) (tileSize.getWidth() * width), (int) (tileSize.getHeight() * height), null);
        if(armor_pic != null)
            screen.drawImage(armor_pic, x_pos, y_pos, (int) (tileSize.getWidth() * width), (int) (tileSize.getHeight() * height), null);
    }

    public abstract void update();
}

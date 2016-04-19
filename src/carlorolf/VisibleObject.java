package carlorolf;

import java.awt.*;
import java.awt.Image;

public abstract class VisibleObject
{
    protected Image image;
    protected double x, y, width, height;

    public VisibleObject(double x, double y, double width, double height, Image image) {
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

    public void setXRelative(final double x){
	this.x += x;
    }

    public void setYRelative(final double y){
    	this.y += y;
    }

    public void setY(final double y) {
	this.y = y;
    }

    public double getHeight() {
	return height;
    }

    public void draw(Graphics screen, Dimension tileSize){
	int x_pos = (int)(tileSize.getWidth() * this.getX());
	int y_pos = (int)(tileSize.getHeight() * this.getY());
	screen.drawImage(image, x_pos, y_pos, (int) (tileSize.getWidth() * width), (int) (tileSize.getHeight() * height), null);
    }

    public abstract void update();
}

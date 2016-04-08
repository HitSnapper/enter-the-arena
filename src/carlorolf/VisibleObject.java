package carlorolf;

import java.awt.*;
import java.awt.Image;

public abstract class VisibleObject
{
    private Image image;
    protected double x, y;

    public VisibleObject(int x, int y, Image image) {
	this.x = x;
	this.y = y;
	this.image = image;
    }

    public double getX() {
	return x;
    }

    public double getY() {
	return y;
    }

    public void draw(Graphics screen, Dimension tileSize){
	int x_pos = (int)(tileSize.getWidth() * this.getX());
	int y_pos = (int)(tileSize.getHeight() * this.getY());
	screen.drawImage(image, x_pos, y_pos, (int) tileSize.getWidth(), (int) tileSize.getHeight(), null);
    }
}

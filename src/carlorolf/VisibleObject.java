package carlorolf;

import java.awt.Image;

public abstract class VisibleObject
{
    private Image image;
    private int x, y;


    public VisibleObject(int x, int y, Image image) {
	this.x = x;
	this.y = y;
	this.image = image;
    }


    public Image getImage() {
	return image;
    }

    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }
}

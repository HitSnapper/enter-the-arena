package carlorolf;

import java.awt.*;
import java.awt.Image;

public abstract class VisibleObject
{
    private Image image;
    private int x, y;

    public VisibleObject(int x, int y, String imageLink) {
	this.x = x;
	this.y = y;
	image = Toolkit.getDefaultToolkit().getImage(imageLink);
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

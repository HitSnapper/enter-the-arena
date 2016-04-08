package carlorolf;

import java.awt.*;
import java.awt.Image;

public abstract class VisibleObject
{
    private Image image;
    protected double x, y;

    public VisibleObject(int x, int y, String imageLink) {
	this.x = x;
	this.y = y;
	image = Toolkit.getDefaultToolkit().getImage(imageLink);
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

    public void draw(Graphics screen){

    }
}

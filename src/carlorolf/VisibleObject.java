package carlorolf;

import java.awt.*;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.RenderingHints;

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

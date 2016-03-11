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
    private String imageLink;

    public VisibleObject(int x, int y, String imageLink) {
	this.x = x;
	this.y = y;
	this.imageLink = imageLink;
    }


    public Image getImage() {
	Image image = Toolkit.getDefaultToolkit().getImage(imageLink);
	return image;
    }

    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }

    public Image getScaledImage(int scale){
	Image image = Toolkit.getDefaultToolkit().getImage(imageLink);
	try {
	    BufferedImage bi = new BufferedImage(scale, scale, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g2d = bi.createGraphics();
	    g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
	    boolean b = g2d.drawImage(image, 0, 0, 50, 50, null);
	    //ImageIO.write(bi, "png", new File(imageLink));
	}
	catch (Exception e){
	    e.printStackTrace();
	}
	return
    }
}

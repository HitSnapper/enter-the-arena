package carlorolf;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Wall extends ArenaObject
{
    public Wall(final double x, final double y, final int width, final int height, Image image,
    		     final CollisionHandler collisionHandler, final Arena arena)
        {
    	super(x, y, width, height, 0, 1, ShapeEnum.RECTANGLE, false, image, collisionHandler, arena);
    	int imageHeight = image.getHeight(null);
    	int imageWidth = image.getWidth(null);
    	BufferedImage img = new BufferedImage(width * imageWidth, height * imageHeight, BufferedImage.TYPE_INT_ARGB);
    	Graphics2D g2 = img.createGraphics();
    	for (int h = 0; h < width; h++){
    	    for (int j = 0; j < height; j++){
    		g2.drawImage(image, h*imageWidth, j*imageHeight, null);
    	    }
    	}
    	this.image = img;
        }

        @Override public void collision(final CollisionEvent e) {

        }

        @Override protected void move(final double movementSpeed) {

        }

        @Override protected void updateImage() {
        }

        @Override public void death(){

        }
}

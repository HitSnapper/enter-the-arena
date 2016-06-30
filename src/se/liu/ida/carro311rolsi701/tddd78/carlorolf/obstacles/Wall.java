package se.liu.ida.carro311rolsi701.tddd78.carlorolf.obstacles;

import se.liu.ida.carro311rolsi701.tddd78.carlorolf.Arena;
import se.liu.ida.carro311rolsi701.tddd78.carlorolf.ArenaObject;
import se.liu.ida.carro311rolsi701.tddd78.carlorolf.CollisionHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * An abstract class that describes a wall (BrickWall for example).
 */
public abstract class Wall extends ArenaObject {
    // WARNING: X and Y is in upper left corner!
    protected Wall(final double x, final double y, final int width, final int height, Image image,
                   final CollisionHandler collisionHandler, final Arena arena) {
        super(x + width / (double) 2, y + height / (double) 2, width, height, 0, 1, false, image, collisionHandler, arena);
        int imageHeight = image.getHeight(null);
        int imageWidth = image.getWidth(null);
        BufferedImage img = new BufferedImage(width * imageWidth, height * imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        for (int h = 0; h < width; h++) {
            for (int j = 0; j < height; j++) {
                g2.drawImage(image, h * imageWidth, j * imageHeight, null);
            }
        }
        //Assigning new image with image repetition
        //noinspection AssignmentToSuperclassField
        this.image = img;
    }

    //This object can't die, method is here to have the feature available
    @SuppressWarnings("NoopMethodInAbstractClass")
    @Override
    public void death() {

    }
}

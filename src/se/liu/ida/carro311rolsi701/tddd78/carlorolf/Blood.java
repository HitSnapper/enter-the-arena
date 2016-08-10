package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by HitSnapper on 2016-05-19.
 */
public class Blood extends Layer {
    private float lifeLength;
    private float age;

    public Blood(double x, double y, double width, double height, Arena arena) {
        super(x, y, width, height, Images.getImage("blood"), arena);
        lifeLength = (float) (height*width*10);
    }

    private void applyTransparency(){
        int imgWidth = image.getWidth(null);
        int imgHeight = image.getHeight(null);
        BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) img.getGraphics();
        int rule = AlphaComposite.SRC_OVER;
        float alpha = Math.max(0, 1 - age / lifeLength);
        g.setComposite(AlphaComposite.getInstance(rule, alpha));
        g.drawImage(Images.getImage("blood"), 0, 0, imgWidth, imgHeight, null);
        image = img;
    }

    @Override public void update(double deltaTime){
        age += 3*deltaTime;
        if (age > lifeLength){
            arena.removeBackgroundLayer(this);
        }
        applyTransparency();
    }
}

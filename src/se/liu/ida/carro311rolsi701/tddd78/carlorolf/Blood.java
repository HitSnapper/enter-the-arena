package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import java.awt.*;

/**
 * Created by HitSnapper on 2016-05-19.
 */
public class Blood extends Layer {
    private double lifeLength;
    private double age;

    public Blood(double x, double y, double width, double height, Arena arena) {
        super(x, y, width, height, Images.getImage("blood"), arena);
        lifeLength = height*width*10;
    }

    @Override public void update(double deltaTime){
        age += deltaTime;
        if (age > lifeLength){
            arena.removeBackgroundLayer(this);
        }
    }

    @Override public void draw(Graphics2D screen, Vector target, Dimension tileSize, int screenWidth, int screenHeight){
        int numberOfPlayers = arena.getNumberOfAlivePlayers();
        if (numberOfPlayers == 0){
            numberOfPlayers = 1;
        }
        double objX = (getX() - width / 2);
        double objY = (getY() - height / 2);
        int xPos = (int) (tileSize.getWidth() * (objX - target.getX()) + screenWidth/numberOfPlayers);
        int yPos = (int) (tileSize.getHeight() * (objY - target.getY()) + screenHeight);
        int rule = AlphaComposite.SRC_OVER;
        float alpha = Math.max(0, 1 - (float) age / (float) lifeLength);

        Composite old = screen.getComposite();
        screen.setComposite(AlphaComposite.getInstance(rule , alpha));
        screen.drawImage(image, xPos, yPos, (int) (tileSize.getWidth() * width), (int) (tileSize.getHeight() * height), null);
        screen.setComposite(old);
    }
}

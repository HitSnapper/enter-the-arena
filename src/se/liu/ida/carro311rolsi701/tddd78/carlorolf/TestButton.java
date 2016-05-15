package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import javax.swing.*;
import java.awt.*;

/**
 * Created by HitSnapper on 2016-05-15.
 */
public class TestButton extends JButton {
    private final double screenX;
    private final double screenY;
    private final double screenWidth;
    private final double screenHeight;
    private ArenaComponent arenaComponent;
    public TestButton(String text, double screenX, double screenY, double screenWidth, double screenHeight, ArenaComponent arenaComponent) {
        super(text);
        this.screenX = screenX;
        this.screenY = screenY;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.arenaComponent = arenaComponent;
        setFocusable(false);

        updatePosition();
        customizeLooks();
    }

    public void updatePosition(){
        int x = (int)(arenaComponent.getWidth() * screenX);
        int y = (int)(arenaComponent.getHeight() * screenY);
        int width = (int)(arenaComponent.getWidth() * screenWidth);
        int height = (int)(arenaComponent.getHeight() * screenHeight);
        setBounds(x, y, width, height);
    }

    private void customizeLooks(){
        setBackground(new Color(0, 0, 0, 50));
        setBorderPainted(false);
        setForeground(new Color(0, 0, 0));
    }
}

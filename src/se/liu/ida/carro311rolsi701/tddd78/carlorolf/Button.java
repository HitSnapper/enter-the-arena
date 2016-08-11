package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton {
    private ArenaComponent arenaComponent;
    private double onScreenX, onScreenY, onScreenHeight, onScreenWidth;

    public Button(String text, double x, double y, double width, double height, ArenaComponent arenaComponent) {
        super(text);
        onScreenX = x;
        onScreenY = y;
        onScreenWidth = width;
        onScreenHeight = height;
        this.arenaComponent = arenaComponent;
        arenaComponent.add(this);
        updatePosition();
        this.setFocusable(false);

        //Customizing looks
        Color color = new Color(40, 50, 60, 200);
        this.setBackground(color);
        //text color
        Color yellow = new Color(160, 170, 0, 230);
        this.setForeground(yellow);
    }

    private int getPreferedFontSize(){
        double screenWidth = arenaComponent.getWidth();
        double screenHeight = arenaComponent.getHeight();
        int width = (int)(screenWidth * onScreenWidth);
        int height = (int)(screenHeight * onScreenHeight);
        int textLength = this.getText().length();
        int fontSize;
        if ((width/textLength) < height){
            fontSize = (int)(width/(0.8*textLength));
        }
        else{
            fontSize = (int)(0.65*height);
        }

        return fontSize;
    }

    public void updatePosition(){
        double screenWidth = arenaComponent.getWidth();
        double screenHeight = arenaComponent.getHeight();
        int width = (int)(screenWidth * onScreenWidth);
        int height = (int)(screenHeight * onScreenHeight);
        int x = (int)(screenWidth * onScreenX - width/2);
        int y = (int)(screenHeight * onScreenY - height/2);
        this.setFont(new Font(Font.MONOSPACED, Font.BOLD, getPreferedFontSize()));

        this.setBounds(x, y, width, height);
    }
}

package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import se.liu.ida.carro311rolsi701.tddd78.carlorolf.friendlycharacters.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Button implements MouseListener {
    private boolean visible;
    private List<Action> actions;
    private String text;
    private ArenaComponent arenaComponent;
    private boolean focused;
    private Color textColor;
    private Color backgroundColor;
    private int x, y, width, height;
    private double onScreenX, onScreenY, onScreenHeight, onScreenWidth;

    public Button(String text, double x, double y, double width, double height, ArenaComponent arenaComponent) {
        visible = true;
        onScreenX = x;
        onScreenY = y;
        onScreenWidth = width;
        onScreenHeight = height;
        this.text = text;
        this.arenaComponent = arenaComponent;
        arenaComponent.addMouseListener(this);
        focused = false;
        actions = new ArrayList<>();
        textColor = new Color(200, 200, 200);
        backgroundColor = new Color(130, 100, 20);
        updatePosition();
    }

    public void addAction(Action action) {
        actions.add(action);
    }

    public void updatePosition(){
        double screenWidth = arenaComponent.getWidth();
        double screenHeight = arenaComponent.getHeight();
        x = (int)(screenWidth * onScreenX);
        y = (int)(screenHeight * onScreenY);
        width = (int)(screenWidth * onScreenWidth);
        height = (int)(screenHeight * onScreenHeight);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (focused && visible) {
            for (Action action : actions) {
                action.actionPerformed(null);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void update() {
        if (visible) {
            Point mousePos = arenaComponent.getMousePosition();
            if (mousePos != null) {
                int mouseX = mousePos.x;
                int mouseY = mousePos.y;
                focused = (mouseX > (x - width / 2) && mouseX < (x + width / 2) &&
                        mouseY > (y - height / 2) && mouseY < (y + height / 2));
            }
        }
        else{
            focused = false;
        }
    }

    public void draw(Graphics screen) {
        if (visible) {
            int xPos = x - width / 2;
            int yPos = y - height / 2;
            if (focused){
                int r = (int)(0.5*backgroundColor.getRed());
                int g = (int)(0.5*backgroundColor.getGreen());
                int b = (int)(0.5*backgroundColor.getBlue());
                screen.setColor(new Color(r, g, b));
            }
            else{
                screen.setColor(backgroundColor);
            }
            screen.fillRect(xPos, yPos, width, height);
            int drawSize = (int) (1.5 * width / text.length());
            int textX = xPos + (width / 2 - text.length() * drawSize / 3);
            int textY = yPos + (drawSize / 2 + height * 3 / 7);
            screen.setColor(textColor);
            screen.setFont(new Font("SansSerif", Font.BOLD, drawSize));
            screen.drawString(text, textX, textY);
        }
    }

    public void show(){
        visible = true;
    }
    public void hide(){
        visible = false;
    }
}

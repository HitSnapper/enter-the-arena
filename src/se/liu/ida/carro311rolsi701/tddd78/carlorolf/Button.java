package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import se.liu.ida.carro311rolsi701.tddd78.carlorolf.friendlycharacters.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Button extends VisibleObject implements MouseListener {
    private boolean visible;
    private List<Action> actions;
    private String text;
    private ArenaComponent arenaComponent;
    private boolean focused;
    private Color textColor;
    private Color backgroundColor;

    public Button(String text, double x, double y, double width, double height, Arena arena, ArenaComponent arenaComponent) {
        super(x, y, width, height, Images.getImage("object_none"), arena);
        visible = true;
        this.text = text;
        this.arenaComponent = arenaComponent;
        arenaComponent.addMouseListener(this);
        focused = false;
        actions = new ArrayList<>();
        textColor = new Color(200, 200, 200);
        backgroundColor = new Color(130, 100, 20);
    }

    public void addAction(Action action) {
        actions.add(action);
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

    @Override
    public void update(double deltaTime) {
        if (visible) {
            Point mousePos = arenaComponent.getMousePosition();
            if (mousePos != null) {
                int mouseX = mousePos.x;
                int mouseY = mousePos.y;
                Dimension tileSize = arenaComponent.getTileSize();
                focused = (mouseX > (x - width / 2) * tileSize.getWidth() && mouseX < (x + width / 2) * tileSize.getWidth() &&
                        mouseY > (y - height / 2) * tileSize.getHeight() && mouseY < (y + height / 2) * tileSize.getHeight());
            }
        }
        else{
            focused = false;
        }
    }

    @Override
    public void draw(Graphics screen, Player player, Dimension tileSize, int screenWidth, int screenHeight) {
        if (visible) {
            int xPos = (int) ((x - (width / 2)) * tileSize.getWidth());
            int yPos = (int) ((y - (height / 2)) * tileSize.getHeight());
            if (focused){
                int r = (int)(0.5*backgroundColor.getRed());
                int g = (int)(0.5*backgroundColor.getGreen());
                int b = (int)(0.5*backgroundColor.getBlue());
                screen.setColor(new Color(r, g, b));
            }
            else{
                screen.setColor(backgroundColor);
            }
            screen.fillRect(xPos, yPos, (int) (width * tileSize.getWidth()), (int) (height * tileSize.getHeight()));
            int drawSize = (int) (1.5 * width * tileSize.getWidth() / text.length());
            int textX = xPos + (int) (width * tileSize.getWidth() / 2 - text.length() * drawSize / 3);
            int textY = yPos + (int) (drawSize / 2 + height * 3*tileSize.getHeight() / 7);
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

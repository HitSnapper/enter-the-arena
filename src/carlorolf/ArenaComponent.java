package carlorolf;

import javafx.geometry.Dimension2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

public class ArenaComponent extends JComponent implements ArenaListener
{
    private Dimension tileSize;
    private final Arena arena;
    private GameState gameState;
    private Keyboard keyboard;

    public GameState getGameState() {
	return gameState;
    }

    public void setGameState(final GameState gameState) {
	this.gameState = gameState;
    }

    private List<Button> menuButtons;
    public ArenaComponent(final Arena arena) {
	this.arena = arena;
	arena.addArenaListener(this);
	tileSize = new Dimension(40, 40);
	gameState = GameState.MENU;
	keyboard = new Keyboard(arena.getPlayer());
	menuButtons = new ArrayList<Button>();

	final Action exitAction = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		System.exit(0);
	    }
	};

	this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "exit");
	this.getActionMap().put("exit", exitAction);

	final Action menuAction = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		System.exit(0);
	    }
	};

	Button b = new Button("Menu", 0, 0, 0, 0);
	b.addAction(menuAction);
	menuButtons.add(b);

	final Action rightAction = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		arena.getPlayer().movePlayer(Direction.EAST);
	    }
	};

	this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "right");
	this.getActionMap().put("right", rightAction);
	final Action rightReleaseAction = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		//arena.getPlayer().stopMovingInDirection(Direction.EAST);
	    }
	};
	this.getInputMap().put(KeyStroke.getKeyStroke(37, 1, true), "released");
	this.getActionMap().put("released", rightReleaseAction);

	final Action righetAction = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		arena.getPlayer().movePlayer(Direction.EAST);

	    }
	};

    }

    public void updateKeyInput(){
	for (Button menuButton : menuButtons) {
	    menuButton.click();
	}
    }

    @Override public void arenaChanged() {
	repaint();
    }

    private void updateTileSize(Dimension size){
	double width = (size.getWidth()/arena.getWidth());
	double height = ((size.getHeight())/arena.getHeight());
	tileSize.setSize(width, height);
    }

    @Override protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;
	updateTileSize(new Dimension(getWidth(), getHeight()));
	g2d.setColor(Color.pink);
	g2d.fillRect(0, 0, getWidth(), getHeight());



	if (gameState == GameState.MENU){
	    switch(gameState.getSubState()){
	    	case OPTIONS:
		case PLAYMENU:
		default:
	    }
	}

	else {
	    //Drawing objects
	    for (Grass grass : arena.getGrassList()) {
		int x = (int)(grass.getX() * tileSize.getWidth());
		int y = (int)(grass.getY() * tileSize.getHeight());


		Image tile = grass.getImage();
		g2d.drawImage(tile, x, y, (int) tileSize.getWidth(), (int) tileSize.getHeight(), this);


	    }
	    switch (gameState.getSubState()){

		case PAUSEMENU:
		default:
	    }
	}
	Player player = arena.getPlayer();
	g2d.drawImage(player.getImage(), (int)(player.getX()*tileSize.getWidth()), (int)(player.getY()*tileSize.getHeight()), (int)tileSize.getWidth(), (int) tileSize.getHeight(), this);
    }
}

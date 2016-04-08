package carlorolf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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

	this.addKeyListener(keyboard);
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
	double height = ((size.getHeight() - 57)/arena.getHeight());
	tileSize.setSize(width, height);
    }

    @Override protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;
	updateTileSize(new Dimension(getWidth(), getHeight()));

	Image screenImage = createImage(getWidth(), getHeight());
	Graphics screen = screenImage.getGraphics();

	screen.setColor(getBackground());
	screen.fillRect(0, 0, getWidth(), getHeight() - 57);
	screen.setColor(getForeground());

	if (gameState == GameState.MENU){
	    switch(gameState.getSubState()){
	    	case OPTIONS:
		case PLAYMENU:
		default:
	    }
	}

	else {
	    //Drawing objects
	    for (VisibleObject visibleObject : arena.getBackgroundList()) {
		visibleObject.draw(screen, tileSize);
	    }
	    switch (gameState.getSubState()){
		case PAUSEMENU:
		default:
	    }
	}

	for (ArenaObject object : arena.getObjectList()) {
	    object.draw(screen, tileSize);
	}

	g2d.drawImage(screenImage, 0, 0, this);
    }
}

package carlorolf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.awt.image.BufferedImage;

public class ArenaComponent extends JComponent implements ArenaListener
{
    private Dimension tileSize;
    private Arena arena;
    private GameState gameState;
    private CollisionHandler collisionHandler;
    private List<JButton> menuButtons;
    private List<JButton> pauseMenuButtons;

    public ArenaComponent(int width, int height, int arenaWidth, int arenaHeight) {
	tileSize = new Dimension(40, 40);
	gameState = new GameState();
	menuButtons = new ArrayList<>();
	pauseMenuButtons = new ArrayList<>();
	collisionHandler = new CollisionHandler();
	this.arena = new Arena(arenaWidth, arenaHeight, collisionHandler);
	collisionHandler.addArena(arena);
	arena.addArenaListener(this);
	Keyboard keyboard = new Keyboard(arena, this);
	updateTileSize(new Dimension(getWidth(), getHeight()));

	final Action exitAction = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		System.exit(0);
	    }
	};

	this.getInputMap().put(KeyStroke.getKeyStroke("U"), "exit");
	this.getActionMap().put("exit", exitAction);

	this.addKeyListener(keyboard);

	final JButton playButton = new JButton("PLAY");
	playButton.setBounds(width / 2 - 100, height / 2 - 250, 200, 100);
	// Initializing buttons
	final Action playAction = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		if (arena.isGameOver()) {
		    arena.restart();
		}
		setGamePhase(Phase.INGAME);
		gameState.setState(State.NONE);
		hideStartMenu();
	    }
	};
	playButton.addActionListener(playAction);
	this.add(playButton);
	menuButtons.add(playButton);

	this.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enter");
	this.getActionMap().put("enter", playAction);

	final JButton exitButton = new JButton("EXIT");
	exitButton.setBounds(width / 2 - 100, height / 2 - 50, 200, 100);
	// Initializing buttons
	exitButton.addActionListener(exitAction);
	this.add(exitButton);
	menuButtons.add(exitButton);

	JButton returnToMenu = new JButton("RETURN TO MENU");
	returnToMenu.setBounds(0, 0, 200, 100);
	final ActionListener returnAction = new AbstractAction()
	{
	    public void actionPerformed(ActionEvent e) {
		setGamePhase(Phase.MENU);
		hidePauseMenu();
		showStartMenu();
	    }
	};
	returnToMenu.addActionListener(returnAction);
	returnToMenu.setVisible(false);
	this.add(returnToMenu);
	pauseMenuButtons.add(returnToMenu);
    }

    public void showPauseMenu() {
	gameState.setState(State.PAUSEMENU);
	for (JButton pauseMenuButton : pauseMenuButtons) {
	    pauseMenuButton.setVisible(true);
	}
    }

    public void hidePauseMenu() {
	gameState.setState(State.NONE);
	for (JButton pauseMenuButton : pauseMenuButtons) {
	    pauseMenuButton.setVisible(false);
	}
    }

    public void showStartMenu() {
	for (JButton menuButton : menuButtons) {
	    menuButton.setVisible(true);
	}
    }

    public void hideStartMenu() {
	for (JButton menuButton : menuButtons) {
	    menuButton.setVisible(false);
	}
    }

    public GameState getGameState() {
	return gameState;
    }

    public void setGameState(final GameState gameState) {
	this.gameState = gameState;
    }

    public Phase getGamePhase() {
	return gameState.getPhase();
    }

    public void setGamePhase(final Phase phase) {
	gameState.setPhase(phase);
    }

    @Override public void arenaChanged() {
	if (gameState.getState() != State.PAUSEMENU) collisionHandler.update();
	repaint();
    }

    private void updateTileSize(Dimension size) {
	double height = ((size.getHeight() - 60) / arena.getHeight());
	//double width = (size.getWidth()/arena.getWidth());
	double width = height;
	tileSize.setSize(width, height);
    }

    @Override protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;
	updateTileSize(new Dimension(getWidth(), getHeight()));

	Image screenImage = createImage(getWidth(), getHeight());
	Graphics screen = screenImage.getGraphics();

	screen.setColor(Color.DARK_GRAY);
	screen.fillRect(0, 0, getWidth(), getHeight() - 57);
	screen.setColor(getForeground());

	//Drawing background
	for (VisibleObject visibleObject : arena.getBackgroundList()) {
	    visibleObject.draw(screen, tileSize);
	}

	//Drawing background layers
	for (VisibleObject object : arena.getLayers()) {
	    object.draw(screen, tileSize);
	}

	//Drawing in game objects
	if (gameState.getPhase() == Phase.INGAME) {
	    //Drawing objects
	    for (ArenaObject object : arena.getObjects()) {
		object.draw(screen, tileSize);

	    }
	    BufferedImage gameScreen = (BufferedImage) screenImage;
	    gameScreen.getSubimage((int) arena.getPlayer().getX(), (int) arena.getPlayer().getY(), 10, 10);

	    g2d.drawImage(gameScreen, 0, 0, null);
	} else {
	    g2d.drawImage(screenImage, 0, 0, this);
	}
    }

    public void update(double deltaTime) {
	if (gameState.getState() != State.PAUSEMENU && gameState.getPhase() != Phase.MENU) {
	    arena.update(deltaTime);
	}
    }

    public CollisionHandler getCollisionHandler() {
	return collisionHandler;
    }
}

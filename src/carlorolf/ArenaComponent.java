package carlorolf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class ArenaComponent extends JComponent implements ArenaListener
{
    private Dimension tileSize;
    private final Arena arena;
    private GameState gameState;
    private Keyboard keyboard;
    private CollisionHandler collisionHandler;
    private List<JButton> menuButtons;
    private List<JButton> pauseMenuButtons;

    public ArenaComponent(final Arena arena, int width, int height) {
	this.arena = arena;
	arena.addArenaListener(this);
	tileSize = new Dimension(40, 40);
	updateTileSize(new Dimension(getWidth(), getHeight()));
	gameState = new GameState();
	keyboard = new Keyboard(arena.getPlayer(), this);
	menuButtons = new ArrayList<JButton>();
	pauseMenuButtons = new ArrayList<JButton>();
	collisionHandler = new CollisionHandler(arena);

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
	playButton.setBounds(width/2 - 100, height/2 - 250, 200, 100);
	// Initializing buttons
	final ActionListener playAction = new AbstractAction() {
	    public void actionPerformed(ActionEvent e) {
		setGamePhase(Phase.INGAME);
		gameState.setState(State.NONE);
		hideStartMenu();
	    }
	};
	playButton.addActionListener(playAction);
	this.add(playButton);
	menuButtons.add(playButton);


	final JButton exitButton = new JButton("EXIT");
	exitButton.setBounds(width/2 - 100, height/2 - 50, 200, 100);
	// Initializing buttons
	exitButton.addActionListener(exitAction);
	this.add(exitButton);
	menuButtons.add(exitButton);

	JButton returnToMenu = new JButton("RETURN TO MENU");
	returnToMenu.setBounds(0, 0, 200, 100);
	final ActionListener returnAction = new AbstractAction() {
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
    public void showPauseMenu(){
	gameState.setState(State.PAUSEMENU);
	for (JButton pauseMenuButton : pauseMenuButtons) {
	    pauseMenuButton.setVisible(true);
	}
    }
    public void hidePauseMenu(){
	gameState.setState(State.NONE);
	for (JButton pauseMenuButton : pauseMenuButtons) {
	    pauseMenuButton.setVisible(false);
	}
    }
    public void showStartMenu(){
	for (JButton menuButton : menuButtons) {
	    menuButton.setVisible(true);
	}
    }
    public void hideStartMenu(){
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

    public Phase getGamePhase(){
	return gameState.getPhase();
    }

    public void setGamePhase(final Phase phase){
	gameState.setPhase(phase);
    }

    @Override public void arenaChanged() {
	if (gameState.getState() != State.PAUSEMENU)
		collisionHandler.update();
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

	for (VisibleObject visibleObject : arena.getBackgroundList()) {
	    visibleObject.update();
	    visibleObject.draw(screen, tileSize);
	}

	if (gameState.getPhase() == Phase.INGAME) {
	    //Drawing objects
	    for (ArenaObject object : arena.getObjectList()) {
		object.draw(screen, tileSize);
	    }
	}

	g2d.drawImage(screenImage, 0, 0, this);
    }

    public void update(){
	if (gameState.getState() != State.PAUSEMENU && gameState.getPhase() != Phase.MENU){
	    arena.update();
	}
    }
}

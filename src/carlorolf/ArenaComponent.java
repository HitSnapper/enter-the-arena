package carlorolf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

public class ArenaComponent extends JComponent implements ArenaListener {
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
        KeyListener keyboard = new Keyboard(arena, this);
        updateTileSize(new Dimension(getWidth(), getHeight()));

        final Action exitAction = new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                System.exit(0);
            }
        };

        this.getInputMap().put(KeyStroke.getKeyStroke("U"), "exit");
        this.getActionMap().put("exit", exitAction);

        this.addKeyListener(keyboard);

        final JButton playButton = new JButton("PLAY");
        playButton.setBounds(width / 2 - width/6, height / 4, width/3, 100);
        // Initializing buttons
        final Action playAction = new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
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
        exitButton.setBounds(width / 2 - width/6, height / 2, width/3, 100);
        // Initializing buttons
        exitButton.addActionListener(exitAction);
        this.add(exitButton);
        menuButtons.add(exitButton);

        JButton returnToMenu = new JButton("RETURN TO MENU");
        returnToMenu.setBounds(0, 0, width/3, 100);
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

    @Override
    public void arenaChanged() {
        if (gameState.getState() != State.PAUSEMENU) collisionHandler.update();
    }

    private void updateTileSize(Dimension size) {
	final int drawingAreaOutsideWindow = 57;
        double sizeOfTile = ((size.getHeight() - drawingAreaOutsideWindow) / arena.getHeight());
	tileSize.setSize(sizeOfTile, sizeOfTile);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;
        updateTileSize(new Dimension(getWidth(), getHeight()));

        Image screenImage = createImage(getWidth(), getHeight());
        Graphics screen = screenImage.getGraphics();

        screen.setColor(Color.DARK_GRAY);

	final int drawingAreaOutsideWindow = 57;

        screen.fillRect(0, 0, getWidth(), getHeight() - drawingAreaOutsideWindow);
        screen.setColor(getForeground());

        //Drawing background tiles
        Image image = arena.getBackground();
        for (int x = -1; x < arena.getWidth() + 3; x++){
            for (int y = -1; y < arena.getHeight() + 2; y++){
                double pX = arena.getPlayer().getX();
                double pY = arena.getPlayer().getY();
                screen.drawImage(image, (int)((x - pX%1)*tileSize.getWidth()), (int)((y - pY%1)*tileSize.getHeight()), (int)tileSize.getWidth(), (int)tileSize.getHeight(),null);
            }
        }

        //Drawing in game objects
        if (gameState.getPhase() == Phase.INGAME) {
            //Drawing background layers
            for (VisibleObject object : arena.getLayers()) {
                object.draw(screen, tileSize);
            }

            //Drawing objects
            for (ArenaObject object : arena.getObjects()) {
                object.draw(screen, tileSize);
            }

            //Drawing top layers, like tree leaves
            for (VisibleObject visibleObject : arena.getTopLayers()) {
                visibleObject.draw(screen, tileSize);
            }

            //Drawing wave
	    final int drawsize = 30;
            screen.setColor(Color.BLACK);
            screen.setFont(new Font("SansSerif", Font.ITALIC, drawsize));
            screen.drawString("Wave: " + arena.getWave(), 5, drawsize);
        }

        //Drawing players attack delay
        int windowHeight = (int) (arena.getHeight() * tileSize.getHeight());
        int windowWidth = (int) (arena.getWidth() * tileSize.getWidth());

        screen.setColor(Color.MAGENTA);
        Player player = arena.getPlayer();
        int attackBarHeight = 4;
        screen.fillRect(0, windowHeight - attackBarHeight, (int) (windowWidth / (player.getAttackSpeed() / player.getAttackTimer())), attackBarHeight);
        if (gameState.getState() == State.PLAYMENU) {
            screen.fillRect(10, 10, windowWidth - 10, windowHeight - 10);
            screen.setColor(Color.RED);
	    final int drawsize = 30;
            Font font = new Font("SansSerif", Font.PLAIN, drawsize);
            int rowspace = drawsize*2;
            screen.setFont(font);
            screen.drawString("Player", rowspace / 2, rowspace);
	    final int imageX = 350;
	    final int imageY = 50;
	    final int imageSize = 200;
            screen.drawImage(Images.getImage("object_none.png"), imageX, imageY, imageSize, imageSize, null);
            screen.drawString("Attackspeed:" + Double.toString(arena.getPlayer().getAttackSpeed()), rowspace / 2, rowspace * 2);
            screen.drawString("Damage:" + Double.toString(arena.getPlayer().getWeapon().getDamage()), rowspace / 2, rowspace * 3);


        }
        g2d.drawImage(screenImage, 0, 0, this);
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

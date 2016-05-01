package carlorolf;

import carlorolf.friendlycharacters.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * ArenaComponent handles drawing of the arena and it's objects, keyboard input, drawing objects, amongst other things.
 */
public class ArenaComponent extends JComponent implements ArenaListener {
    private Dimension tileSize;
    private Arena arena;
    private GameState gameState;
    private CollisionHandler collisionHandler;
    private List<JButton> menuButtons;
    private List<JButton> pauseMenuButtons;
    private BufferedImage backgroundImage;

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
        updateTileSize(height);
        final double proportionalSize = 1.5;
        backgroundImage = new BufferedImage((int) (width * proportionalSize), (int) (height * proportionalSize), BufferedImage.TYPE_INT_ARGB);

        generateBackground();

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
        playButton.setBounds(width / 2 - width / 6, height / 4, width / 3, 100);
        // Initializing buttons
        final Action playAction = new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (arena.isGameOver()) {
                    arena.restart();
                    generateBackground();
                }
                setGamePhase(Phase.INGAME);
                gameState.setState(State.NONE);
                hideStartMenu();
                hidePauseMenu();
            }
        };
        playButton.addActionListener(playAction);
        this.add(playButton);
        menuButtons.add(playButton);

        this.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enter");
        this.getActionMap().put("enter", playAction);

        final JButton exitButton = new JButton("EXIT");
        exitButton.setBounds(width / 2 - width / 6, height / 2, width / 3, 100);
        // Initializing buttons
        exitButton.addActionListener(exitAction);
        this.add(exitButton);
        menuButtons.add(exitButton);

        JButton returnToMenu = new JButton("RETURN TO MENU");
        returnToMenu.setBounds(0, 0, width / 3, 100);
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

    public void setGamePhase(final Phase phase) {
        gameState.setPhase(phase);
    }

    @Override
    public void arenaChanged() {
        if (gameState.getState() != State.PAUSEMENU) collisionHandler.update();
    }

    private void updateTileSize(int size) {
        final int drawingAreaOutsideWindow = 58;
        double sizeOfTile = ((size - drawingAreaOutsideWindow) / (double) arena.getHeight());
        tileSize.setSize(sizeOfTile, sizeOfTile);
    }


    private void generateBackground() {
        Graphics screen = backgroundImage.getGraphics();
        Image image = arena.getBackground();
        for (int x = 0; x < backgroundImage.getWidth() / tileSize.getWidth(); x++) {
            for (int y = 0; y < backgroundImage.getHeight() / tileSize.getHeight(); y++) {
                screen.drawImage(image, (int) (x * tileSize.getWidth()), (int) (y * tileSize.getHeight()), (int) tileSize.getWidth(), (int) tileSize.getHeight(), this);
            }
        }
    }

    private void paintInGame(Graphics screen, int screenWidth, int screenHeight) {
        //Drawing background layers
        for (VisibleObject visibleObject : arena.getBackgroundLayers()) {
            visibleObject.draw(screen, tileSize, screenWidth, screenHeight);
        }

        //Drawing objects
        for (ArenaObject object : arena.getObjects()) {
            object.draw(screen, tileSize, screenWidth, screenHeight);
        }

        //Drawing top layers, like tree leaves
        for (VisibleObject visibleObject : arena.getTopLayers()) {
            visibleObject.draw(screen, tileSize, screenWidth, screenHeight);
        }

        //Drawing wave
        final int drawSize = 30;
        screen.setColor(Color.BLACK);
        screen.setFont(new Font("SansSerif", Font.ITALIC, drawSize));
        screen.drawString("Wave: " + arena.getWave(), 5, drawSize);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;
        Player player = arena.getPlayer();
        final int drawingAreaOutsideWindow = 58;
        int screenWidth = getWidth() / 2;
        int screenHeight = (getHeight() - drawingAreaOutsideWindow) / 2;

        BufferedImage screenImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics screen = screenImage.getGraphics();

        screen.setColor(Color.DARK_GRAY);

        screen.fillRect(0, 0, getWidth(), getHeight() - drawingAreaOutsideWindow);
        screen.setColor(getForeground());

        screen.drawImage(backgroundImage, -1 - (int) ((player.getX() % 1) * tileSize.getWidth()), -1 - (int) ((player.getY() % 1) * tileSize.getHeight()), this);

        //Drawing in game objects
        if (gameState.getPhase() == Phase.INGAME) {
            paintInGame(screen, screenWidth, screenHeight);
        }

        //Drawing players attack delay
        int windowHeight = screenHeight * 2;
        int windowWidth = screenWidth * 2;

        screen.setColor(Color.MAGENTA);
        int attackBarHeight = 10;
        screen.fillRect(0, windowHeight - attackBarHeight,
                (int) (windowWidth / (player.getAttackSpeed() / player.getAttackTimer())), attackBarHeight);
        if (gameState.getState() == State.PLAYMENU) {
            screen.fillRect(10, 10, windowWidth - 10, windowHeight - 10);
            screen.setColor(Color.RED);
            final int fontSize = 30;
            Font font = new Font("SansSerif", Font.PLAIN, fontSize);
            int rowSpace = fontSize * 2;
            screen.setFont(font);
            screen.drawString("Player", rowSpace / 2, rowSpace);
            final int imageX = 350;
            final int imageY = 50;
            final int imageSize = 200;
            screen.drawImage(Images.getImage("object_none"), imageX, imageY, imageSize, imageSize, null);
            screen.drawString("Attackspeed:" + Double.toString(player.getAttackSpeed()), rowSpace / 2, rowSpace * 2);
            screen.drawString("Damage:" + Double.toString(player.getWeapon().getDamage()), rowSpace / 2,
                    rowSpace * 3);


        }
        g2d.drawImage(screenImage, 0, 0, this);
    }

    public void update(double deltaTime) {
        if (gameState.getState() != State.PAUSEMENU && gameState.getPhase() != Phase.MENU) {
            arena.update(deltaTime);
        }
    }

}

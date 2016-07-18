package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import se.liu.ida.carro311rolsi701.tddd78.carlorolf.friendlycharacters.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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
    private List<Button> menuButtons;
    private List<Button> playMenuButtons;
    private List<Button> pauseMenuButtons;
    private BufferedImage backgroundImage;
    private List<Button> buttons;
    private boolean debugging;
    private List<Integer> frameSpeedList;
    private List<Integer> physicsSpeedList;

    public ArenaComponent(int width, int height, int arenaWidth, int arenaHeight) {
        frameSpeedList = new ArrayList<>();
        physicsSpeedList = new ArrayList<>();
        debugging = false;
        gameState = new GameState();
        menuButtons = new ArrayList<>();
        pauseMenuButtons = new ArrayList<>();
        playMenuButtons = new ArrayList<>();
        collisionHandler = new CollisionHandler();
        this.setBounds(0, 0, width, height);
        updateTileSize(arenaHeight);
        generateBackground();
        initializeButtons(arenaWidth, arenaHeight);
    }

    private void initializeButtons(int arenaWidth, int arenaHeight) {
        //this.add(new TestButton("YES!", 0.5, 0.5, 0.5, 0.5, this));

        final Action exitAction = new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                System.exit(0);
            }
        };

        this.getInputMap().put(KeyStroke.getKeyStroke("U"), "exit");
        this.getActionMap().put("exit", exitAction);

        // Initializing buttons
        final Action playAction = new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                gameState.setState(State.PLAYMENU);
                hideStartMenu();
                hidePauseMenu();
                showPlayMenu();
            }
        };
        final Action singleplayerAction = new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                hidePlayMenu();
                if (arena == null) {
                    initializeArena(arenaWidth, arenaHeight, 1);
                } else {
                    arena.restart(1);
                    generateBackground();
                }
                gameState.setPhase(Phase.INGAME);
                gameState.setState(State.NONE);
            }
        };
        final Action multiplayerAction = new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                hidePlayMenu();
                if (arena == null) {
                    initializeArena(arenaWidth, arenaHeight, 2);
                } else {
                    arena.restart(2);
                    generateBackground();
                }
                gameState.setPhase(Phase.INGAME);
                gameState.setState(State.NONE);
            }
        };
        final Action returnAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setGamePhase(Phase.MENU);
                hidePauseMenu();
                showStartMenu();
            }
        };

        final double buttonWidth = 1.0 / 3.0;
        final double buttonHeight = 1.0 / 5.0;

        Button playButton = new Button("PLAY", 0.5, 1.0 / 3.0, buttonWidth, buttonHeight, this);
        Button exitButton = new Button("EXIT", 0.5, 2.0 / 3.0, buttonWidth, buttonHeight, this);
        Button returnButton = new Button("RETURN TO MENU", buttonWidth / 2, buttonHeight / 2, buttonWidth, buttonHeight, this);
        Button singleplayerButton = new Button("SINGLEPLAYER", 0.5, 1.0 / 3.0, buttonWidth, buttonHeight, this);
        Button multiplayerButton = new Button("MULTIPLAYER", 0.5, 2.0 / 3.0, buttonWidth, buttonHeight, this);
        singleplayerButton.hide();
        multiplayerButton.hide();
        returnButton.hide();
        playButton.addAction(playAction);
        menuButtons.add(playButton);
        exitButton.addAction(exitAction);
        menuButtons.add(exitButton);
        returnButton.addAction(returnAction);
        pauseMenuButtons.add(returnButton);
        singleplayerButton.addAction(singleplayerAction);
        playMenuButtons.add(singleplayerButton);
        multiplayerButton.addAction(multiplayerAction);
        playMenuButtons.add(multiplayerButton);

        buttons = new ArrayList<>();
        buttons.add(playButton);
        buttons.add(exitButton);
        buttons.add(returnButton);
        buttons.add(singleplayerButton);
        buttons.add(multiplayerButton);
    }

    private void initializeArena(int arenaWidth, int arenaHeight, int numberOfPlayers) {
        arena = new Arena(arenaWidth, arenaHeight, numberOfPlayers, collisionHandler);
        arena.addArenaListener(this);
        collisionHandler.addArena(arena);
        updateTileSize();
        generateBackground();
        KeyListener keyboard = new Keyboard(arena, this);
        this.addKeyListener(keyboard);
    }

    public void showPlayMenu() {
        for (Button playMenuButton : playMenuButtons) {
            playMenuButton.show();
        }
    }

    public void hidePlayMenu() {
        for (Button playMenuButton : playMenuButtons) {
            playMenuButton.hide();
        }
    }

    public Dimension getTileSize() {
        return tileSize;
    }

    public void toggleDebug() {
        debugging = !debugging;
    }

    public void showPauseMenu() {
        gameState.setState(State.PAUSEMENU);
        for (Button pauseMenuButton : pauseMenuButtons) {
            pauseMenuButton.show();
        }
    }

    public void hidePauseMenu() {
        gameState.setState(State.NONE);
        for (Button pauseMenuButton : pauseMenuButtons) {
            pauseMenuButton.hide();
        }
    }

    public void showStartMenu() {
        for (Button menuButton : menuButtons) {
            menuButton.show();
        }
    }

    public void hideStartMenu() {
        for (Button menuButton : menuButtons) {
            menuButton.hide();
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGamePhase(final Phase phase) {
        gameState.setPhase(phase);
    }

    public void arenaChanged() {
        if (gameState.getState() != State.PAUSEMENU) collisionHandler.update();
    }

    public void updateResolution(int width, int height) {
        this.setBounds(0, 0, width, height);
        updateTileSize();
        updateButtonPositions();
        //must fix so that it generates it according to screen size
        generateBackground();
    }

    private void updateButtonPositions() {
        for (Button button : buttons) {
            button.updatePosition();
        }
    }

    private void updateTileSize() {
        double sizeOfTile;
        if (arena != null) {
            sizeOfTile = (getHeight() / (double) arena.getHeight());
        } else {
            sizeOfTile = (getHeight() / 20);
        }
        tileSize.setSize(sizeOfTile, sizeOfTile);
    }

    public void updateTileSize(int size) {
        double sizeOfTile = (getHeight() / size);
        if (tileSize != null) {
            tileSize.setSize(sizeOfTile, sizeOfTile);
        } else {
            tileSize = new Dimension((int) sizeOfTile, (int) sizeOfTile);
        }
    }

    public void updateFrameTick(int tick) {
        frameSpeedList.add(tick);
        int sum = 0;
        for (int t : frameSpeedList) {
            sum += t;
        }
        while (sum > 1000){
            sum -= frameSpeedList.get(0);
            frameSpeedList.remove(0);
        }
    }

    public void updatePhysicsTick(int tick) {
        physicsSpeedList.add(tick);
        int sum = 0;
        for (int t : physicsSpeedList) {
            sum += t;
        }
        while (sum > 1000){
            sum -= physicsSpeedList.get(0);
            physicsSpeedList.remove(0);
        }
    }

    private void generateBackground() {
        final double proportionalSize = 1.5;
        backgroundImage = new BufferedImage((int) (getWidth() * proportionalSize), (int) (getHeight() * proportionalSize),
                BufferedImage.TYPE_INT_ARGB);
        Image image;
        if (arena != null) {
            image = arena.getBackground();
        } else {
            image = Images.getImage("grass");
        }
        Graphics screen = backgroundImage.getGraphics();
        for (int x = 0; x < backgroundImage.getWidth() / tileSize.getWidth(); x++) {
            for (int y = 0; y < backgroundImage.getHeight() / tileSize.getHeight(); y++) {
                screen.drawImage(image, (int) (x * tileSize.getWidth()), (int) (y * tileSize.getHeight()),
                        (int) tileSize.getWidth(), (int) tileSize.getHeight(), this);
            }
        }
    }

    private void paintInGame(Graphics2D screen, Player player, int screenWidth, int screenHeight) {
        Vector target = player.getCoords();

        /*
        Note: Important to make a copy of each list, since drawing works separate from updates and an update may
        remove a object during iteration.
         */

        // Drawing background
        screen.drawImage(backgroundImage, - 1 - (int)((target.getX() - (int)target.getX()) * tileSize.getWidth()),
                - 1 - (int)((target.getY() - (int)target.getY()) * tileSize.getHeight()), null);

        //Drawing background layers
        List<VisibleObject> temp = new ArrayList<>(arena.getBackgroundLayers());
        for (VisibleObject visibleObject : temp) {
            visibleObject.draw(screen, target, tileSize, screenWidth, screenHeight);
        }
        //Drawing objects
        List<ArenaObject> temp1 = new ArrayList<>(arena.getObjects());
        for (ArenaObject object : temp1) {
            object.draw(screen, target, tileSize, screenWidth, screenHeight);
        }

        //Draw collision edges if debugging
        if (debugging) {
            paintCollisionDebug(screen, target, tileSize, screenWidth, screenHeight);
        }

        //Drawing top layers, like tree leaves
        temp = new ArrayList<>(arena.getTopLayers());
        for (VisibleObject visibleObject : temp) {
            visibleObject.draw(screen, target, tileSize, screenWidth, screenHeight);
        }

        if (arena.getNumberOfAlivePlayers() > 0) {
            //Drawing attack cool down
            final int barWidth = 4;
            screen.setColor(Color.MAGENTA);
            screen.fillRect(0, getHeight() - barWidth, (int) (getWidth() / arena.getNumberOfAlivePlayers() * player.getAttackTimer() / player.getAttackSpeed()), barWidth);
        }

        //Drawing wave
        final int drawSize = 30;
        screen.setColor(Color.BLACK);
        screen.setFont(new Font("SansSerif", Font.ITALIC, drawSize));
        screen.drawString("Wave: " + arena.getWave(), 5, drawSize);
    }

    private void paintCollisionDebug(Graphics2D screen, Vector target, Dimension tileSize, int screenWidth, int screenHeight) {
        screen.setColor(Color.MAGENTA);
        List<ArenaObject> temp1 = new ArrayList<>(arena.getObjects());
        for (ArenaObject object : temp1) {
            object.getBody().draw(screen, target, tileSize, screenWidth, screenHeight);
        }
    }

    private void paintFrameDebug(Graphics2D screen) {
        final int drawSize = 30;
        screen.setColor(Color.MAGENTA);
        screen.setFont(new Font("SansSerif", Font.ITALIC, drawSize));
        screen.drawString("Frame tick speed: " + frameSpeedList.size(), 0, 50);
        screen.drawString("Physics tick speed: " + physicsSpeedList.size(), 0, 100);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;
        int screenWidth = getWidth() / 2;
        int screenHeight = getHeight() / 2;

        BufferedImage screenImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D screen = (Graphics2D) screenImage.getGraphics();

        screen.setColor(Color.DARK_GRAY);
        screen.fillRect(0, 0, getWidth(), getHeight());

        if (gameState.getPhase() == Phase.MENU) {
            screen.drawImage(backgroundImage, -1, -1, null);
        }

        if (arena != null) {
            List<Player> alivePlayers = arena.getAlivePlayers();
            int numberOfPlayers = arena.getNumberOfAlivePlayers();
            if (numberOfPlayers == 0) {
                numberOfPlayers = 1;
            }
            for (int n = 0; n < numberOfPlayers; n++) {
                Player player;
                if (alivePlayers.size() == 0) {
                    player = arena.getLastSurvivor();
                } else {
                    player = alivePlayers.get(n);
                }
                BufferedImage playerImage = new BufferedImage(getWidth() / numberOfPlayers, getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D playerScreen = (Graphics2D) playerImage.getGraphics();

                //Drawing in game objects
                if (gameState.getPhase() == Phase.INGAME) {//Drawing background
                    paintInGame(playerScreen, player, screenWidth, screenHeight);
                }
                screen.drawImage(playerImage, n * getWidth() / numberOfPlayers, 0, this);
            }

            // Drawing light
            screen.setColor(new Color(0, 0, 0, 255*arena.getTime()/arena.getDayLength()));
            screen.fillRect(0, 0, getWidth(), getHeight());

            if (gameState.getPhase() == Phase.INGAME && numberOfPlayers > 1) {
                final int borderWidth = 1;
                screen.setColor(Color.BLACK);
                for (int n = 1; n < numberOfPlayers; n++) {
                    screen.fillRect(n * getWidth() / numberOfPlayers - borderWidth, 0, borderWidth * 2, getHeight());
                }
            }
        }
        if (gameState.getState() == State.PAUSEMENU) {
            screen.setColor(new Color(0, 0, 0, 60));
            screen.fillRect(0, 0, getWidth(), getHeight());
        }
        for (Button button : buttons) {
            button.draw(screen);
        }
        if (debugging) {
            paintFrameDebug(screen);
        }
        g2d.drawImage(screenImage, 0, 0, this);
    }

    public void update(double deltaTime) {
        if (gameState.getState() != State.PAUSEMENU && gameState.getPhase() != Phase.MENU) {
            arena.update(deltaTime);
        }
        for (Button button : buttons) {
            button.update();
        }
    }
}

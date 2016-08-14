package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import se.liu.ida.carro311rolsi701.tddd78.carlorolf.friendlycharacters.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.awt.Graphics;
import java.awt.Image;

/**
 * ArenaComponent handles drawing of the arena and it's objects, keyboard input, drawing objects, amongst other things.
 */
public class ArenaComponent extends JComponent {
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
    private boolean deepDebugging;
    private List<Integer> frameSpeedList;
    private List<Integer> physicsSpeedList;
    private long frameTime;
    private long physicsTime;
    private BufferedImage frame;
    private BufferedImage screenCapture;
    private boolean calledRePaint;
    private Keyboard keyboard;

    public ArenaComponent(int width, int height, int arenaWidth, int arenaHeight) {
        frameTime = System.currentTimeMillis();
        physicsTime = System.currentTimeMillis();
        frameSpeedList = new ArrayList<>();
        physicsSpeedList = new ArrayList<>();
        debugging = false;
        gameState = new GameState();
        gameState.setPhase(Phase.MENU);
        gameState.setState(State.NONE);
        menuButtons = new ArrayList<>();
        pauseMenuButtons = new ArrayList<>();
        playMenuButtons = new ArrayList<>();
        collisionHandler = new CollisionHandler();
        this.setBounds(0, 0, width, height);
        updateTileSize(arenaHeight);
        generateBackground();
        initializeButtons(arenaWidth, arenaHeight);
        keyboard = new Keyboard(this);
        this.addKeyListener(keyboard);
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
                if (gameState.getPhase() == Phase.MENU && gameState.getState() == State.NONE) {
                    gameState.setState(State.PLAYMENU);
                    showPlayMenu();
                }
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
        playButton.addActionListener(playAction);
        menuButtons.add(playButton);
        exitButton.addActionListener(exitAction);
        menuButtons.add(exitButton);
        returnButton.addActionListener(returnAction);
        pauseMenuButtons.add(returnButton);
        singleplayerButton.addActionListener(singleplayerAction);
        playMenuButtons.add(singleplayerButton);
        multiplayerButton.addActionListener(multiplayerAction);
        playMenuButtons.add(multiplayerButton);

        buttons = new ArrayList<>();
        buttons.add(playButton);
        buttons.add(exitButton);
        buttons.add(returnButton);
        buttons.add(singleplayerButton);
        buttons.add(multiplayerButton);
    }

    public void initializeArena(int arenaWidth, int arenaHeight, int numberOfPlayers) {
        arena = new Arena(arenaWidth, arenaHeight, numberOfPlayers, collisionHandler);
        collisionHandler.addArena(arena);
        keyboard.setArena(arena);
        updateTileSize();
        generateBackground();
    }

    public void showPlayMenu() {
        hideStartMenu();
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
        if (gameState.getPhase() == Phase.INGAME) {
            if (debugging && !deepDebugging) {
                deepDebugging = true;
            } else if (debugging && deepDebugging) {
                debugging = false;
                deepDebugging = false;
            } else {
                debugging = true;
            }
        }
        else{
            deepDebugging = false;
            debugging = !debugging;
        }
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
        hidePlayMenu();
        hidePauseMenu();
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

    private void updateFrameTick() {
        long oldTime = frameTime;
        frameTime = System.currentTimeMillis();
        long deltaTime = frameTime - oldTime;
        frameSpeedList.add((int) deltaTime);
        int sum = 0;
        for (int t : frameSpeedList) {
            sum += t;
        }
        while (sum > 1000) {
            sum -= frameSpeedList.get(0);
            frameSpeedList.remove(0);
        }
    }

    private void updatePhysicsTick() {
        long oldTime = physicsTime;
        physicsTime = System.currentTimeMillis();
        long deltaTime = physicsTime - oldTime;
        physicsSpeedList.add((int) deltaTime);
        int sum = 0;
        for (int t : physicsSpeedList) {
            sum += t;
        }
        while (sum > 1000) {
            sum -= physicsSpeedList.get(0);
            physicsSpeedList.remove(0);
        }
    }

    public void generateBackground() {
        backgroundImage = new BufferedImage(getWidth() + 2*(int)tileSize.getWidth(), getHeight() + 2*(int)tileSize.getHeight(),
                BufferedImage.TYPE_INT_RGB);
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

    private void screenCapture(){
        int screenWidth = getWidth() / 2;
        int screenHeight = getHeight() / 2;

        BufferedImage screenImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D screen = (Graphics2D) screenImage.getGraphics();

        if (arena != null) {
            //Drawing in game objects
            if (gameState.getPhase() == Phase.INGAME) {//Drawing background
                paintInGame(screen, arena.getLastSurvivor(), screenWidth, screenHeight);
            }
        }
        screenCapture = screenImage;
    }

    private void paintInGame(Graphics2D screen, Player player, int screenWidth, int screenHeight) {
        Vector target = player.getCoords();

        /*
        Note: Important to make a copy of each list, since drawing works separate from updates and an update may
        remove a object during iteration.
         */

        // Drawing background
        screen.drawImage(backgroundImage, - (int) ((target.getX() - (int) target.getX() + 1) * tileSize.getWidth()),
                 - (int) ((target.getY() - (int) target.getY() + 1) * tileSize.getHeight()), null);

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

        //Drawing top layers, like tree leaves
        temp = new ArrayList<>(arena.getForegroundLayers());
        for (VisibleObject visibleObject : temp) {
            visibleObject.draw(screen, target, tileSize, screenWidth, screenHeight);
        }

        //Draw collision edges if debugging
        if (deepDebugging) {
            paintCollisionDebug(screen, target, tileSize, screenWidth, screenHeight);
        }

        if (arena.getNumberOfAlivePlayers() > 0) {
            //Drawing attack cool down
            final int barWidth = 4;
            screen.setColor(Color.MAGENTA);
            screen.fillRect(0, getHeight() - barWidth, (int) (getWidth() / arena.getNumberOfAlivePlayers() * player.getAttackTimer() / player.getAttackSpeed()), barWidth);
        }
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
        screen.setFont(new Font("SansSerif", Font.ITALIC, drawSize));
        screen.setColor(Color.GREEN);
        if (frameSpeedList.size() < 60) {
            screen.setColor(Color.ORANGE);
        }
        if (frameSpeedList.size() < 50) {
            screen.setColor(Color.RED);
        }
        screen.drawString("Frame tick speed: " + frameSpeedList.size(), 0, 50);

        screen.setColor(Color.GREEN);
        if (physicsSpeedList.size() < 500) {
            screen.setColor(Color.ORANGE);
        }
        if (physicsSpeedList.size() < 200) {
            screen.setColor(Color.RED);
        }
        screen.drawString("Physics tick speed: " + physicsSpeedList.size(), 0, 100);
    }

    @Override
    protected void paintComponent(Graphics g) {
        updateFrameTick();
        g.drawImage(frame, 0, 0, this);
        calledRePaint = false;
    }

    private void drawGameOverText(Graphics2D screen, int screenWidth, int screenHeight){
        screen.setColor(new Color(160, 170, 0, 230));
        Map<TextAttribute, Integer> fontAttributes = new HashMap<>();
        fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        screen.setFont(new Font("Monospaced", Font.BOLD, 30).deriveFont(fontAttributes));
        screen.rotate(-Math.PI/5);
        screen.drawString("PATHETIC", (int)(screenWidth/1.5), (int)(screenHeight*2.43));
        screen.rotate(Math.PI/5);
        screen.setColor(new Color(100, 0, 0));
        screen.setFont(new Font("Monospaced", Font.BOLD, 200));
        screen.drawString("DEAD", screenWidth/2, screenHeight);
        int drawSize = 35;
        screen.setColor(new Color(40, 20, 140));
        screen.setFont(new Font("Monospaced", Font.BOLD, drawSize));
        screen.drawString("YOU GOT TO WAVE " + arena.getWave(), screenWidth - drawSize*4, screenHeight + drawSize);
    }

    public void makeGraphics(){
        int screenWidth = getWidth() / 2;
        int screenHeight = getHeight() / 2;

        BufferedImage screenImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D screen = (Graphics2D) screenImage.getGraphics();

        if (gameState.getPhase() == Phase.MENU) {
            screen.drawImage(backgroundImage, -1, -1, null);
        }
        List<Player> alivePlayers = new ArrayList<>();
        int numberOfPlayers = 0;
        if (arena != null) {
            alivePlayers = arena.getAlivePlayers();
            numberOfPlayers = arena.getNumberOfAlivePlayers();
        }
        if (arena != null && numberOfPlayers != 0 && gameState.getPhase() == Phase.INGAME) {
            for (int n = 0; n < numberOfPlayers; n++) {
                Player player = alivePlayers.get(n);
                BufferedImage playerImage = new BufferedImage(getWidth() / numberOfPlayers, getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D playerScreen = (Graphics2D) playerImage.getGraphics();

                //Drawing in game objects
                paintInGame(playerScreen, player, screenWidth, screenHeight);
                screen.drawImage(playerImage, n * getWidth() / numberOfPlayers, 0, this);
            }

            if (numberOfPlayers > 1) {
                final int borderWidth = 1;
                screen.setColor(Color.BLACK);
                for (int n = 1; n < numberOfPlayers; n++) {
                    screen.fillRect(n * getWidth() / numberOfPlayers - borderWidth, 0, borderWidth * 2, getHeight());
                }
            }

            //Drawing wave
            final int drawSize = 35;
            screen.setColor(new Color(40, 20, 140));
            screen.setFont(new Font("SansSerif", Font.BOLD, drawSize));
            screen.drawString("Wave " + arena.getWave(), screenWidth - drawSize*2, drawSize);
        }
        else if (gameState.getPhase() == Phase.INGAME){
            screenCapture();
            screen.drawImage(screenCapture, 0, 0, null);
            drawGameOverText(screen, screenWidth, screenHeight);
        }

        if (gameState.getState() == State.PAUSEMENU) {
            screen.setColor(new Color(0, 0, 0, 60));
            screen.fillRect(0, 0, getWidth(), getHeight());
        }

        if (debugging) {
            paintFrameDebug(screen);
        }
        frame = screenImage;
    }

    public long draw() {
        long start = System.currentTimeMillis();
        makeGraphics();
        if (!calledRePaint) {
            calledRePaint = true;
            repaint();
        }
        return System.currentTimeMillis() - start;
    }

    public long update(double deltaTime) {
        long start = System.currentTimeMillis();
        updatePhysicsTick();
        if (gameState.getState() != State.PAUSEMENU && gameState.getPhase() != Phase.MENU) {
            arena.update(deltaTime);
        }
        collisionHandler.update();
        grabFocus();
        return System.currentTimeMillis() - start;
    }
}

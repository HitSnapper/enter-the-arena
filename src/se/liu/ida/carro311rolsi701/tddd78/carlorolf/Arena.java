package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import se.liu.ida.carro311rolsi701.tddd78.carlorolf.enemies.DragonBoss;
import se.liu.ida.carro311rolsi701.tddd78.carlorolf.enemies.Dust;
import se.liu.ida.carro311rolsi701.tddd78.carlorolf.friendlycharacters.Healer;
import se.liu.ida.carro311rolsi701.tddd78.carlorolf.friendlycharacters.Player;
import se.liu.ida.carro311rolsi701.tddd78.carlorolf.obstacles.BrickWall;
import se.liu.ida.carro311rolsi701.tddd78.carlorolf.obstacles.Stone;
import se.liu.ida.carro311rolsi701.tddd78.carlorolf.obstacles.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Image;

/**
 * This is the arena and it carries all the objects contained in the arena.
 */
public class Arena {
    private int width;
    private int height;
    private List<ArenaListener> arenaListeners;
    private List<Layer> backgroundLayers;
    private List<VisibleObject> topLayers;
    private List<ArenaObject> objects;
    private List<ArenaObject> removeObjectsList;
    private List<VisibleObject> removeTopLayersList;
    private List<Layer> removeBackgroundLayersList;
    private List<Player> players;
    private CollisionHandler collisionHandler;
    private boolean gameOver;
    private int wave;
    private List<ArenaObject> enemies;
    private Image background;
    private Player lastSurvivor;
    private double time;
    private final int dayLength = 25;

    public Arena(int width, int height, int numberOfPlayers, CollisionHandler collisionHandler) {
        gameOver = false;
        this.collisionHandler = collisionHandler;
        this.width = width;
        this.height = height;
        this.arenaListeners = new ArrayList<>();
        this.objects = new ArrayList<>();
        this.backgroundLayers = new ArrayList<>();
        this.removeObjectsList = new ArrayList<>();
        this.removeBackgroundLayersList = new ArrayList<>();
        this.topLayers = new ArrayList<>();
        this.wave = 0;
        this.enemies = new ArrayList<>();
        this.removeTopLayersList = new ArrayList<>();
        this.players = new ArrayList<>();
        lastSurvivor = null;
        time = 0;
        generateArena(numberOfPlayers);
    }

    public Player getLastSurvivor() {
        return lastSurvivor;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void addObject(ArenaObject arenaObject) {
        objects.add(arenaObject);
    }

    public void addArenaListener(ArenaListener listener) {
        arenaListeners.add(listener);
    }

    public List<VisibleObject> getTopLayers() {
        return topLayers;
    }

    private void removeQueued() {
        for (ArenaObject object : removeObjectsList) {
            enemies.remove(object);
            objects.remove(object);
        }
        for (VisibleObject topLayer : removeTopLayersList) {
            topLayers.remove(topLayer);
        }
        for (Layer layer : removeBackgroundLayersList){
            backgroundLayers.remove(layer);
        }
        removeObjectsList.clear();
        removeTopLayersList.clear();
    }

    private boolean allPlayersDead() {
        for (Player player : players) {
            if (!player.isDead()) {
                return false;
            }
        }
        return true;
    }

    public void addTopLayer(VisibleObject object) {
        topLayers.add(object);
    }

    public void removeTopLayer(VisibleObject object) {
        removeTopLayersList.add(object);
    }

    private void notifyListeners() {
        arenaListeners.forEach(ArenaListener::arenaChanged);
    }

    private void spawnEnemies() {
        for (int i = 0; i <= wave; i++) {
            Dust enemy = new Dust(width + 4, (double) height / 2 + 1.0 / (i * 5 + 1), collisionHandler, this);
            enemy.setArmor(new Armor(i * 5, enemy, this, Images.getImage("helmet")));
            enemies.add(enemy);
        }
        if (wave % 3 == 0 && wave != 0) {
            enemies.add(new DragonBoss(width + 4, (double) height / 2, collisionHandler, this));
        }
        for (ArenaObject enemy : enemies) {
            objects.add(enemy);
        }
    }

    public void removeBackgroundLayer(Layer layer){
        removeBackgroundLayersList.add(layer);
    }

    public int getNumberOfPlayers() {
        return players.size();
    }

    public int getNumberOfAlivePlayers() {
        return getAlivePlayers().size();
    }

    public List<Player> getAlivePlayers() {
        List<Player> res = new ArrayList<>();
        for (Player player : players) {
            if (!player.isDead()) {
                res.add(player);
            }
        }
        return res;
    }

    public List<Layer> getBackgroundLayers() {
        return backgroundLayers;
    }

    public void addBackgroundLayer(Layer object) {
        backgroundLayers.add(object);
    }

    private void generateBackground() {
        Random rand = new Random();
        if (rand.nextInt(2) == 1) {
            background = Images.getImage("grass");
        } else {
            background = Images.getImage("sand");
        }
    }

    public void removeObject(ArenaObject object) {
        removeObjectsList.add(object);
    }

    public Image getBackground() {
        return background;
    }

    public List<ArenaObject> getObjects() {
        return objects;
    }

    public Player getPlayer(int index) {
        return players.get(index);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getWave() {
        return wave;
    }

    public int getTime() {
        return (int)time;
    }

    public int getDayLength() {
        return dayLength;
    }

    public void restart(int numberOfPlayers) {
        gameOver = false;
        backgroundLayers.clear();
        objects.clear();
        removeObjectsList.clear();
        collisionHandler.clearAll();
        enemies.clear();
        topLayers.clear();
        players.clear();
        wave = 0;
        lastSurvivor = null;
        generateArena(numberOfPlayers);
    }

    private void generateArena(int numberOfPlayers) {
        generateBackground();
        final double playerX = 2.5;
        final double playerY = 2.5;
        for (int n = 0; n < numberOfPlayers; n++) {
            Controls controls = new Controls(n);
            Player player = new Player(playerX, playerY, controls, collisionHandler, this);
            players.add(player);
            objects.add(player);
        }

        Random rand = new Random();

        for (int i = 0; i < 4; i++) {
            final double stoneSize = 1;
            objects.add(
                    new Stone(rand.nextInt(width - 2) + 1, rand.nextInt(width - 2) + 1, stoneSize, collisionHandler,
                            this));
            objects.add(new Healer(rand.nextInt(width - 2) + 1, rand.nextInt(width - 2) + 1, collisionHandler, this));
            objects.add(new Tree(rand.nextInt(width - 2) + 1, rand.nextInt(width - 2) + 1, 1, collisionHandler, this));
        }

        List<VisibleObject> temp = new ArrayList<>();
        VisibleObject visibleObject = new Layer(0, 100, 0, 0, Images.getImage("grass"), this);
        for (int i = topLayers.size(); i >= 0; i--) {
            for (VisibleObject topLayer : topLayers) {
                if (visibleObject.getY() > topLayer.getY()) visibleObject = topLayer;
            }
            temp.add(visibleObject);
            topLayers.remove(visibleObject);
            visibleObject = new Layer(0, 100, 0, 0, Images.getImage("grass"), this);
        }

        topLayers = temp;

        //Defines the width of the walls, shouldn't be named height
        final int wallWidth = 1;

        objects.add(new BrickWall(-wallWidth, -wallWidth, wallWidth, height + wallWidth, collisionHandler, this));
        //noinspection SuspiciousNameCombination
        objects.add(new BrickWall(-wallWidth, height, width + wallWidth * 2, wallWidth, collisionHandler, this));
        //noinspection SuspiciousNameCombination
        objects.add(new BrickWall(0, -wallWidth, width + wallWidth, wallWidth, collisionHandler, this));
        objects.add(new BrickWall(width, 0, wallWidth, height / 3, collisionHandler, this));
        objects.add(new BrickWall(width, 2 * height / 3 + 1, wallWidth, height / 3, collisionHandler, this));
        //noinspection SuspiciousNameCombination
        objects.add(new BrickWall(width + wallWidth, height / 3 - wallWidth, width / 3, wallWidth, collisionHandler, this));
        //noinspection SuspiciousNameCombination
        objects.add(new BrickWall(width + wallWidth, 2 * height / 3 + 1, width / 3, wallWidth, collisionHandler, this));
        objects.add(new BrickWall(width + wallWidth + width / 3, height / 3 - wallWidth, wallWidth, 2 * height / 3 + 2 * wallWidth - height / 3 + 1,
                collisionHandler, this));
    }

    public void update(double deltaTime) {
        removeQueued();
        time += deltaTime;
        time = time%dayLength;
        System.out.println(time);
        List<ArenaObject> temp = new ArrayList<>(objects);
        for (ArenaObject arenaObject : temp) {
            arenaObject.update(deltaTime);
        }
        List<Layer> temp1 = new ArrayList<>(backgroundLayers);
        for (Layer layer : temp1) {
            layer.update(deltaTime);
        }
        if (getNumberOfAlivePlayers() == 1) {
            lastSurvivor = getAlivePlayers().get(0);
        }
        if (allPlayersDead()) {
            gameOver = true;
        }
        if (enemies.isEmpty()) {
            spawnEnemies();
            wave += 1;
        }
        notifyListeners();
    }
}

package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import se.liu.ida.carro311rolsi701.tddd78.carlorolf.enemies.DragonBoss;
import se.liu.ida.carro311rolsi701.tddd78.carlorolf.enemies.StandardEnemy;
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
    private List<VisibleObject> backgroundLayers;
    private List<VisibleObject> topLayers;
    private List<ArenaObject> objects;
    private List<ArenaObject> removeObjectsList;
    private List<VisibleObject> removeTopLayersList;
    private Player player;
    private CollisionHandler collisionHandler;
    private boolean gameOver;
    private int wave;
    private List<ArenaObject> enemies;
    private Image background;

    public Arena(int width, int height, CollisionHandler collisionHandler) {
        gameOver = false;
        this.collisionHandler = collisionHandler;
        this.width = width;
        this.height = height;
        this.arenaListeners = new ArrayList<>();
        this.objects = new ArrayList<>();
        this.backgroundLayers = new ArrayList<>();
        this.removeObjectsList = new ArrayList<>();
        this.topLayers = new ArrayList<>();
        this.wave = 0;
        this.enemies = new ArrayList<>();
        this.removeTopLayersList = new ArrayList<>();
        generateArena();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void addArenaListener(ArenaListener listener) {
        arenaListeners.add(listener);
    }

    public List<VisibleObject> getTopLayers() {
        return topLayers;
    }

    public void removeQueued() {
        for (ArenaObject object : removeObjectsList) {
            enemies.remove(object);
            objects.remove(object);
        }
        for (VisibleObject topLayer : removeTopLayersList) {
            topLayers.remove(topLayer);
        }
    }

    public void update(double deltaTime) {
        removeQueued();
        for (ArenaObject arenaObject : objects) {
            arenaObject.update(deltaTime);
        }
        if (player.isDead()) {
            gameOver = true;
        }
        if (enemies.isEmpty()) {
            spawnEnemies();
            wave += 1;
        }
        notifyListeners();
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
        for (int i = 0; i <= wave + 20; i++) {
            StandardEnemy enemy = new StandardEnemy(width + 4, (double) height / 2 + 1.0 / (i * 5 + 1), collisionHandler, this);
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

    private void generateArena() {
        generateBackground();
        final double playerX = 2.5;
        final double playerY = 2.5;
        player = new Player(playerX, playerY, collisionHandler, this);
        objects.add(player);

        Random rand = new Random();

        for (int i = 0; i < 4; i++) {
            final double stoneSize = 1.5;
            objects.add(
                    new Stone(rand.nextInt(width - 2) + 1, rand.nextInt(width - 2) + 1, stoneSize, stoneSize, collisionHandler,
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
        final int wallWidth = 2;

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
        objects.add(new BrickWall(width + wallWidth + width / 3, height / 3 - wallWidth, wallWidth, height / 2 + wallWidth,
                collisionHandler, this));
    }

    public List<VisibleObject> getBackgroundLayers() {
        return backgroundLayers;
    }

    public void addBackgroundLayer(VisibleObject object) {
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

    public Player getPlayer() {
        return player;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getWave() {
        return wave;
    }

    public void restart() {
        gameOver = false;
        backgroundLayers.clear();
        objects.clear();
        removeObjectsList.clear();
        collisionHandler.clearAll();
        enemies.clear();
        topLayers.clear();
        wave = 0;
        generateArena();
    }
}

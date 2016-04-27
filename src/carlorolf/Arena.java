package carlorolf;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Arena {
    private int width;
    private int height;
    private List<ArenaListener> arenaListeners;
    private List<VisibleObject> backgroundList;
    private List<VisibleObject> layerList;
    private List<VisibleObject> topLayers;
    private List<ArenaObject> objects;
    private List<ArenaObject> removeObjectsList;
    private Player player;
    private CollisionHandler collisionHandler;
    private boolean gameOver;
    private int wave;
    private List<ArenaObject> enemies;

    public Arena(int width, int height, CollisionHandler collisionHandler) {
        gameOver = false;
        this.collisionHandler = collisionHandler;
        this.width = width;
        this.height = height;
        this.arenaListeners = new ArrayList();
        this.backgroundList = new ArrayList();
        this.objects = new ArrayList();
        this.layerList = new ArrayList<>();
        this.removeObjectsList = new ArrayList<>();
        this.topLayers = new ArrayList<>();
        this.wave = 0;
        this.enemies = new ArrayList();
        generateArena();
    }

    public void setWave(final int wave) {
        this.wave = wave;
    }

    public List<ArenaObject> getEnemies() {
        return enemies;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void addArenaListener(ArenaListener listener) {
        arenaListeners.add(listener);
    }

    public List<VisibleObject> getTopLayers() {
        return topLayers;
    }

    public void update(double deltaTime) {
        System.out.println(enemies.size());
        for (ArenaObject object : removeObjectsList) {
            enemies.remove(object);
            objects.remove(object);
        }
        for (ArenaObject arenaObject : objects) {
            arenaObject.update(deltaTime);
        }

        if (getPlayer().isDead()) {
            gameOver = true;
        }
        System.out.println(enemies.size());
        if (enemies.isEmpty()) {
            spawnEnemies();
            wave += 1;

        }

        notifyListeners();
    }

    public void addBackgroundLayer(VisibleObject object) {
        layerList.add(object);
    }

    public void addTopLayer(VisibleObject object) {
        topLayers.add(object);
    }

    public void removeTopLayer(VisibleObject object) {
        topLayers.remove(object);
    }

    private void notifyListeners() {
        arenaListeners.forEach(ArenaListener::arenaChanged);
    }

    private void spawnEnemies() {
        Random rand = new Random();
        for (int i = 0; i <= wave; i++) {
            enemies.add(new Enemy(width + 4 , height/2, collisionHandler, this));
	}
    	if(wave % 3 == 0 && wave != 0){
	    enemies.add(new DragonBoss(width + 4 , height/2, collisionHandler, this));

        for (ArenaObject enemy : enemies) {
            objects.add(enemy);
        }
    }}

    private void generateArena() {
        //generateBackground();
        player = new Player(2.5, 2.5, collisionHandler, this);
        objects.add(player);

        Random rand = new Random();

        for (int i = 0; i < 2; i++) {
            objects.add(new Stone(rand.nextInt(width - 2) + 1, rand.nextInt(width - 2) + 1, 1.5, 1.5, collisionHandler, this));
            objects.add(new MovableObject(rand.nextInt(width - 2) + 1, rand.nextInt(width - 2) + 1, collisionHandler, this));
            objects.add(new Tree(rand.nextInt(width - 2) + 1, rand.nextInt(width - 2) + 1, 1, collisionHandler, this));
        }
        Comparator<VisibleObject> comp = new Comparator<VisibleObject>() {
            @Override
            public int compare(VisibleObject o1, VisibleObject o2) {
                if (o1.getY() < o2.getY())
                    return 0;
                else{
                    return 1;
                }
            }
        };

        getTopLayers().sort(comp);

        int wallWidth = 2;

        objects.add(new BrickWall(-wallWidth, -wallWidth, wallWidth, height + wallWidth, collisionHandler, this));
        objects.add(new BrickWall(-wallWidth, height, width + wallWidth*2, wallWidth, collisionHandler, this));
        objects.add(new BrickWall(0, -wallWidth, width + wallWidth, wallWidth, collisionHandler, this));
        objects.add(new BrickWall(width, 0, wallWidth, height/3, collisionHandler, this));
        objects.add(new BrickWall(width, 2*height/3 + 1, wallWidth, height/3, collisionHandler, this));
        objects.add(new BrickWall(width + wallWidth, height/3 - wallWidth, width/3, wallWidth, collisionHandler, this));
        objects.add(new BrickWall(width + wallWidth, 2*height/3 + 1, width/3, wallWidth, collisionHandler, this));
        objects.add(new BrickWall(width + wallWidth + width/3, height/3 - wallWidth, wallWidth, height/2 + wallWidth, collisionHandler, this));
    }

    public List<VisibleObject> getLayers() {
        return layerList;
    }

    public void addLayer(VisibleObject object) {
        layerList.add(object);
    }

    private void generateBackground() {
        Random rand = new Random();

        if (rand.nextInt(2) == 0) {
            for (double x = 0.5; x < width * 4; x++) {
                for (double y = 0.5; y < height * 4; y++) {
                    backgroundList.add(new Sand(x - (width / 1), y - height / 1, this));
                }
            }

        } else {
            for (double x = 0.5; x < width * 4; x++) {
                for (double y = 0.5; y < height * 4; y++) {
                    backgroundList.add(new Grass(x - (width / 1), y - height / 1, this));
                }
            }
        }
    }

    public void removeObject(ArenaObject object) {
        removeObjectsList.add(object);
    }

    public List<VisibleObject> getBackgroundList() {
        return backgroundList;
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

    public int getWave(){
        return wave;
    }

    public void restart() {
        gameOver = false;
        backgroundList.clear();
        layerList.clear();
        objects.clear();
        removeObjectsList.clear();
        collisionHandler.clearAll();
        enemies.clear();
        topLayers.clear();
        generateArena();
    }
}

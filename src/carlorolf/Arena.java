package carlorolf;

import java.util.ArrayList;
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
        generateArena();
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
        for (ArenaObject object : removeObjectsList) {
            objects.remove(object);
        }
        for (ArenaObject arenaObject : objects) {
            arenaObject.update(deltaTime);
        }

        if (getPlayer().isDead()) {
            gameOver = true;
        }

        notifyListeners();
    }

    public void addBackgroundLayer(VisibleObject object) {
        layerList.add(object);
    }

    public void addTopLayer(VisibleObject object){
        topLayers.add(object);
    }

    public void removeTopLayer(VisibleObject object){
        topLayers.remove(object);
    }

    private void notifyListeners() {
        arenaListeners.forEach(ArenaListener::arenaChanged);
    }

    private void generateArena() {
        generateBackground();
        player = new Player(2.5, 2.5, collisionHandler, this);
        objects.add(player);

	Random rand = new Random();

        for(int i = 0; i < 2; i++){
            objects.add(new Enemy(rand.nextInt(width - 2) + 1, rand.nextInt(width - 2)+1, collisionHandler, this));
            objects.add(new DragonBoss(rand.nextInt(width - 2) + 1, rand.nextInt(width - 2)+1, collisionHandler, this));
            objects.add(new Stone(rand.nextInt(width - 2) + 1, rand.nextInt(width - 2)+1, 1.5, 1.5, collisionHandler, this));
            objects.add(new MovableObject(rand.nextInt(width - 2) + 1, rand.nextInt(width - 2)+1, collisionHandler, this));
            objects.add(new Tree(rand.nextInt(width - 2) + 1, rand.nextInt(width - 2) +1, 1, collisionHandler, this));
        }

        int wallWidth = 2;
	int wallTall = height + wallWidth;
	int wallWide = width + wallWidth;

        objects.add(new BrickWall(-wallWidth, -wallWidth, wallWidth, wallTall, collisionHandler, this));
        objects.add(new BrickWall(-wallWidth, height, wallWide, wallWidth, collisionHandler, this));
        objects.add(new BrickWall(width, -wallWidth, wallWidth, wallTall + wallWidth, collisionHandler, this));
        objects.add(new BrickWall(0, -wallWidth, width/2 - 1, wallWidth, collisionHandler, this));
	objects.add(new BrickWall(width/2 + 1, -wallWidth, width/2 - 1, wallWidth, collisionHandler, this));
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
	    for (double x = 0.5; x < width * 2; x++) {
		for (double y = 0.5; y < height * 2; y++) {
			backgroundList.add(new Sand(x - (width / 2), y - height / 2, this));
		}
	    }

	} else {
	    for (double x = 0.5; x < width * 2; x++) {
		for (double y = 0.5; y < height * 2; y++) {
			backgroundList.add(new Grass(x - (width / 2), y - height / 2, this));
		}
	    }
	}
    }

    public void removeObject(ArenaObject object) {
        removeObjectsList.remove(object);
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

    public void restart() {
        gameOver = false;
        backgroundList.clear();
        layerList.clear();
        objects.clear();
        removeObjectsList.clear();
        collisionHandler.clearAll();
        generateArena();
    }
}

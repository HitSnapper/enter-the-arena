package carlorolf;

import java.util.ArrayList;
import java.util.List;

public class Arena
{
    private int width;
    private int height;
    private List<ArenaListener> arenaListeners;
    private List<VisibleObject> backgroundList;
    private List<VisibleObject> layerList;
    private List<ArenaObject> objects;
    private List<ArenaObject> removeObjectsList;
    private List<Player> playerList;
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
	this.layerList = new ArrayList<VisibleObject>();
        playerList = new ArrayList<Player>();
	this.removeObjectsList = new ArrayList<ArenaObject>();
	generateArena();
    }

    public int getWidth() {
            return width;
        }

    public int getHeight() {
            return height;
        }

    public void addArenaListener(ArenaListener listener){
        arenaListeners.add(listener);
    }

    public void update(){
	for (ArenaObject object : removeObjectsList) {
	    objects.remove(object);
	}
	for (ArenaObject arenaObject : objects) {
	    arenaObject.update();
	}

	if (getPlayer().isDead()){
	    gameOver = true;
	}

	notifyListeners();
    }

    public void addBackgroundLayer(VisibleObject object){
	layerList.add(object);
    }

    public void notifyListeners(){
	for (ArenaListener arenaListener : arenaListeners) {
	    arenaListener.arenaChanged();
	}
    }

    private void generateArena(){
        generateBackground();
        Player player = new Player(2.5,2.5, collisionHandler, this);
        playerList.add(player);
        objects.add(player);
	objects.add(new Enemy(10.5, 10.5, collisionHandler, this));
	objects.add(new Enemy(11.5, 11.5, collisionHandler, this));

        objects.add(new Stone(7.5, 6.5, 1.5, 1.5, collisionHandler, this));
	objects.add(new MovableObject(2.5, 5.5, collisionHandler, this));
        objects.add(new MovableObject(3.5, 5.5, collisionHandler, this));
        objects.add(new MovableObject(4.5, 5.5, collisionHandler, this));
        objects.add(new MovableObject(5.5, 5.5, collisionHandler, this));
	objects.add(new Tree(4.5, 9.5, 1.5, collisionHandler, this));

	int stone_width = 1;
	int stone_height = 1;
        for (double x = 0.5; x < width/stone_width; x++){
            objects.add(new Stone(x * stone_width, 0.5, stone_width, stone_height, collisionHandler, this));
            objects.add(new Stone(x * stone_width, height - stone_height + 0.5, stone_width, stone_height, collisionHandler, this));
        }
	for (double y = 1.5; y < height/stone_height - 1; y++){
	    objects.add(new Stone(0.5, y * stone_height, stone_width, stone_height, collisionHandler, this));
	    objects.add(new Stone(width - stone_width + 0.5, y * stone_height, stone_width, stone_height, collisionHandler, this));
	}
    }

    public List<VisibleObject> getLayers(){
	return layerList;
    }

    public void addLayer(VisibleObject object){
	layerList.add(object);
    }

    private void generateBackground(){
        for (double x = 0.5; x < width; x ++){
            for (double y = 0.5; y < height; y ++){
                backgroundList.add(new Grass(x,y));
            }
        }

    }

    public void removeObject(ArenaObject object){
	removeObjectsList.remove(object);
    }

    public List<VisibleObject> getBackgroundList() {
	return backgroundList;
    }

    public List<ArenaObject> getObjects(){
	return objects;
    }

    public Player getPlayer(){
        return playerList.get(0);
    }

    public boolean isGameOver(){
	return gameOver;
    }

    public void restart(){
	gameOver = false;
	backgroundList.clear();
	layerList.clear();
	objects.clear();
	removeObjectsList.clear();
	playerList.clear();
	collisionHandler.clearAll();
	generateArena();
    }
}

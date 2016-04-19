package carlorolf;

import java.util.ArrayList;
import java.util.List;

public class Arena
{
    private int width;
    private int height;
    private List<ArenaListener> arenaListeners;
    private List<VisibleObject> backgroundList;
    private List<ArenaObject> objectList;
    private List<Player> playerList;
    private CollisionHandler collisionHandler;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Arena(int width, int height, CollisionHandler collisionHandler) {
	this.collisionHandler = collisionHandler;
        this.width = width;
        this.height = height;
        this.arenaListeners = new ArrayList();
	this.backgroundList = new ArrayList();
        this.objectList = new ArrayList();
        playerList = new ArrayList<Player>();
	generateArena();
    }

    public void addArenaListener(ArenaListener listener){
        arenaListeners.add(listener);
    }

    public void update(){
	for (ArenaObject arenaObject : objectList) {
	    arenaObject.update();
	}
	notifyListeners();
    }

    public void notifyListeners(){
	for (ArenaListener arenaListener : arenaListeners) {
	    arenaListener.arenaChanged();
	}
    }

    private void generateArena(){
        generateBackground();
        Player player = new Player(2,2, collisionHandler);
        playerList.add(player);
        objectList.add(player);
	objectList.add(new Enemy(10, 10, player, collisionHandler));

        objectList.add(new Stone(7, 6, 1, 1, collisionHandler));
	objectList.add(new MovableObject(2, 5, collisionHandler));
        objectList.add(new MovableObject(3, 5, collisionHandler));
        objectList.add(new MovableObject(4, 5, collisionHandler));
        objectList.add(new MovableObject(5, 5, collisionHandler));
	objectList.add(new Tree(4,9, collisionHandler));

	int stone_width = 1;
	int stone_height = 1;
        for (int x = 0; x < width/stone_width; x++){
            objectList.add(new Stone(x*stone_width, 0, stone_width, stone_height, collisionHandler));
            objectList.add(new Stone(x*stone_width, height - stone_height, stone_width, stone_height, collisionHandler));
        }
	for (int y = 1; y < height/stone_height - 1; y++){
	    objectList.add(new Stone(0, y*stone_height, stone_width, stone_height, collisionHandler));
	    objectList.add(new Stone(width - stone_width, y*stone_height, stone_width, stone_height, collisionHandler));
	}
    }

    private void generateBackground(){
        for (int x = 0; x < width; x ++){
            for (int y = 0; y < height; y ++){
                backgroundList.add(new Grass(x,y));
            }
        }

    }

    public List<VisibleObject> getBackgroundList() {
	return backgroundList;
    }

    public List<ArenaObject> getObjectList(){
	return objectList;
    }

    public Player getPlayer(){
        return playerList.get(0);
    }
}

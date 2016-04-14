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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Arena(int width, int height) {

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
        Player player = new Player(2,2);
        playerList.add(player);
        objectList.add(player);
	objectList.add(new Enemy(10, 10, player));

        objectList.add(new Stone(7, 6));
        objectList.add(new Movableobject(2, 5));
        objectList.add(new Movableobject(3, 5));
        objectList.add(new Movableobject(4, 5));
        objectList.add(new Movableobject(5, 5));

        for (int x = 0; x < width; x++){
            objectList.add(new Stone(x, 0));
            objectList.add(new Stone(x, height - 1));
        }
	for (int y = 1; y < height - 1; y++){
	    objectList.add(new Stone(0, y));
	    objectList.add(new Stone(width - 1, y));
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

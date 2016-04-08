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
	generateBackground();
        playerList = new ArrayList<Player>();
        Player player = new Player(2,2);
        playerList.add(player);
	objectList.add(player);

	objectList.add(new Stone(7, 8));
    }

    public void addArenaListener(ArenaListener listener){
        arenaListeners.add(listener);
    }

    public void update(){
        getPlayer().update();
	notifyListeners();
    }

    public void notifyListeners(){
	for (ArenaListener arenaListener : arenaListeners) {
	    arenaListener.arenaChanged();
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

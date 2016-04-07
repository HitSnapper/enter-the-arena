package carlorolf;

import java.util.ArrayList;
import java.util.List;

public class Arena
{
    private int width;
    private int height;
    private List<ArenaListener> arenaListeners;
    private List<Grass> grassList;
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
        this.grassList = new ArrayList();
        playerList = new ArrayList<Player>();
        playerList.add(new Player(2, 2));
	generateBackground();
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
                grassList.add(new Grass(x,y));
            }
        }

    }

    public List<Grass> getGrassList() {
	return grassList;
    }

    public Player getPlayer(){
        return playerList.get(0);
    }
}

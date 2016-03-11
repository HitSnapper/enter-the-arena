package carlorolf;

import java.util.ArrayList;
import java.util.List;

public class Arena
{
    private int width;
    private int height;
    private int tileSize;
    private List<ArenaListener> arenaListeners;
    private List<Grass> grassList;
    public Arena(int width, int height, int tileSize) {
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.arenaListeners = new ArrayList();
        this.grassList = new ArrayList();
	generateBackground();
    }

    public void addArenaListener(ArenaListener listener){
        arenaListeners.add(listener);
    }

    public void update(){
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

    public int getTileSize() {
	return tileSize;
    }
}

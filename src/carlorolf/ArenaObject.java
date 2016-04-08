package carlorolf;

import java.awt.Image;

public abstract class ArenaObject extends VisibleObject
{
    private boolean movable;
    public ArenaObject(int x, int y, boolean movable, Image image) {
	super(x, y, image);
	this.movable = movable;
    }
    public void update(){

    }
}

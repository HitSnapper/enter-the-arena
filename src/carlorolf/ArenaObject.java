package carlorolf;

import java.awt.Image;

public abstract class ArenaObject extends VisibleObject
{
    private boolean movable;
    private Shape shape;
    public ArenaObject(int x, int y, double width, double height, boolean movable, Image image) {
	super(x, y, width, height, image);
	this.movable = movable;
    }
    public void update(){

    }

    public abstract void Collision(CollisionEvent e);
}

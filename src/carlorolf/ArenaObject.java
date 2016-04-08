package carlorolf;

public abstract class ArenaObject extends VisibleObject
{
    private boolean movable;
    public ArenaObject(int x, int y, boolean movable, String imageLink) {
	super(x, y, "/home/rolsi701/enter-the-arena/src/carlorolf/object.png");
	this.movable = movable;
    }
    public void update(){

    }
}

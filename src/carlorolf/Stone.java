package carlorolf;

public class Stone extends ArenaObject
{
    public Stone(final int x, final int y) {
	super(x, y, false, Images.getImage("stone.png"));
    }
}

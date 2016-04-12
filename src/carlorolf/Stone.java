package carlorolf;

public class Stone extends ArenaObject
{
    public Stone(final int x, final int y) {
	super(x, y, 2, 2, false, Images.getImage("stone.png"));
    }

    @Override public void Collision(final CollisionEvent e) {

    }
}

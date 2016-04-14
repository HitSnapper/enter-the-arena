package carlorolf;

public class Stone extends ArenaObject
{
    public Stone(final int x, final int y) {
	super(x, y, 0.8, 0.8, 0.1, ShapeEnum.RECTANGLE, false, Images.getImage("stone.png"));
    }

    @Override public void update() {

    }

    @Override public void Collision(final CollisionEvent e) {

    }
}

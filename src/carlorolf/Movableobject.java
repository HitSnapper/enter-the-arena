package carlorolf;

public class Movableobject extends ArenaObject
{
    public Movableobject(final int x, final int y)
    {
	super(x, y, 0.7, 0.7, 0.1, ShapeEnum.RECTANGLE, true, Images.getImage("object.png"));
    }

    @Override public void update() {

    }

    @Override public void Collision(final CollisionEvent e) {
    }
}

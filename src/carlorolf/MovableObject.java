package carlorolf;


public class MovableObject extends ArenaObject
{
    public MovableObject(final int x, final int y, CollisionHandler collisionHandler)
    {
	super(x, y, 0.7, 0.7, 0.1, ShapeEnum.RECTANGLE, true, Images.getImage("object_none.png"), collisionHandler);
    }

    @Override public void Collision(final CollisionEvent e) {
    }

    @Override protected void move() {

    }

    @Override protected void updateImage() {

    }
}
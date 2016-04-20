package carlorolf;

public class Stone extends ArenaObject
{
    public Stone(final int x, final int y, final double width, final double height, CollisionHandler collisionHandler, Arena arena) {
	super(x, y, width, height, 50, 100, ShapeEnum.RECTANGLE, false, Images.getImage("stone.png"), collisionHandler, arena);
    }

    @Override public void Collision(final CollisionEvent e) {

    }

    @Override protected void move(double movementSpeed) {

    }

    @Override protected void updateImage() {

    }
}

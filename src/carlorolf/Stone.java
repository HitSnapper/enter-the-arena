package carlorolf;

public class Stone extends ArenaObject
{
    public Stone(final int x, final int y, final double width, final double height, CollisionHandler collisionHandler) {
	super(x, y, width, height, 0.1, ShapeEnum.RECTANGLE, false, Images.getImage("stone.png"), collisionHandler);
    }

    @Override public void Collision(final CollisionEvent e) {

    }

    @Override protected void move() {

    }

    @Override protected void updateImage() {

    }
}

package carlorolf;

public class Tree extends ArenaObject
{

    public Tree(final int x, final int y, CollisionHandler collisionHandler, Arena arena) {
	super(x, y, 1.2, 1.2, 10, 1000, ShapeEnum.RECTANGLE, false, Images.getImage("tree.png"), collisionHandler, arena);
    }

    @Override public void Collision(final CollisionEvent e) {
    }

    @Override protected void move(final double movementSpeed) {

    }

    @Override protected void updateImage() {

    }

}

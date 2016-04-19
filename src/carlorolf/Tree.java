package carlorolf;

public class Tree extends ArenaObject
{

    public Tree(final int x, final int y, CollisionHandler collisionHandler) {
	super(x, y, 1.2, 1.2, 0.1, ShapeEnum.RECTANGLE, false, Images.getImage("tree.png"), collisionHandler);
    }

    @Override public void Collision(final CollisionEvent e) {
    }

    @Override protected void move() {

    }

    @Override protected void updateImage() {

    }

}

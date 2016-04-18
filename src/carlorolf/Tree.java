package carlorolf;

public class Tree extends ArenaObject
{

    public Tree(final int x, final int y) {
	super(x, y, 1.2, 1.2, 0.1, ShapeEnum.RECTANGLE, false, Images.getImage("tree.png"));
    }

    @Override public void update() {

    }


    @Override public void Collision(final CollisionEvent e) {


    }

}

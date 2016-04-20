package carlorolf;

public class Tree extends ArenaObject
{

    public Tree(final double x, final double y, final double width, CollisionHandler collisionHandler, Arena arena) {
	super(x, y, width, width, 10, 500, ShapeEnum.RECTANGLE, false, Images.getImage("stock.png"), collisionHandler, arena);
        layers.add(new VisibleObject(x, y, width*3.5, height*3.5, Images.getImage("tree_leaves.png"))
	{
	    @Override public void update() {

	    }
	});
    }

    @Override public void Collision(final CollisionEvent e) {
    }

    @Override protected void move(final double movementSpeed) {

    }
    @Override public void death(){
	this.layers.clear();
    }

    @Override protected void updateImage() {

    }

}

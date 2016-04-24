package carlorolf;


public class MovableObject extends ArenaObject {
    public MovableObject(final double x, final double y, CollisionHandler collisionHandler, Arena arena) {
        super(x, y, 0.7, 0.7, 10, 100, ShapeEnum.RECTANGLE, true, Images.getImage("object_none.png"), null, collisionHandler, arena);
    }

    @Override
    public void Collision(final CollisionEvent e) {
    }

    @Override
    protected void move(final double movementSpeed) {

    }

    @Override
    protected void updateImage() {

    }
}
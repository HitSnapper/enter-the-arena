package carlorolf;


public class MovableObject extends ArenaObject {
    public MovableObject(final double x, final double y, CollisionHandler collisionHandler, Arena arena) {
        super(x, y, 0.7, 0.7, 10, 100, ShapeEnum.RECTANGLE, true, Images.getImage("object_none.png"), collisionHandler, arena);
        armor = new Armor(100, this, arena, Images.getImage("helmet.png"));
    }

    @Override
    public void collision(final CollisionEvent e) {
    }

    @Override
    protected void move(final double movementSpeed) {

    }

    @Override
    protected void updateImage() {

    }
}
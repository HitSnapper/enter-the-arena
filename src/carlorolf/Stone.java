package carlorolf;

public class Stone extends ArenaObject {
    public Stone(final double x, final double y, final double width, final double height, CollisionHandler collisionHandler, Arena arena) {
        super(x, y, width, height, 50, 100, ShapeEnum.RECTANGLE, false, Images.getImage("stone_highres.png"), collisionHandler, arena);
    }

    @Override
    public void collision(final CollisionEvent e) {

    }

    @Override
    protected void move(double movementSpeed) {

    }

    @Override
    protected void updateImage() {

    }

    @Override
    public void death() {
    }
}

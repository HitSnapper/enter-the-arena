package carlorolf;

/**
 * A tree which gives the arena some personality. If the player want some more view of the arena he can chop it down.
 */
public class Tree extends ArenaObject {

    public Tree(final double x, final double y, final double width, CollisionHandler collisionHandler, Arena arena) {
        super(x, y, width, width, 10, 100, ShapeEnum.RECTANGLE, false, Images.getImage("tree.png"), collisionHandler, arena);
        final int leavesImageWidth = 73;
        final int leavesImageHeight = 82;
        final int stumpWidth = 17;
        final int stumpHeight = 14;
        double leavesWidth = width * leavesImageWidth / stumpWidth;
        double leavesHeight = height * leavesImageHeight / stumpHeight;
        layers.add(new VisibleObject(x, y + height / 2 - leavesHeight / 2, leavesWidth, leavesHeight, Images.getImage("leaves.png"), arena) {
            @Override
            public void update(double deltaTime) {

            }
        });
        for (VisibleObject layer : layers) {
            arena.addTopLayer(layer);
        }
    }

    @Override
    protected void move(final double movementSpeed) {

    }

    @Override
    public void death() {
        for (VisibleObject layer : layers) {
            arena.removeTopLayer(layer);
        }
        this.layers.clear();
    }

    @Override
    protected void updateImage() {

    }

}

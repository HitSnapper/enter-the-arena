package carlorolf;

public class Tree extends ArenaObject {

    public Tree(final double x, final double y, final double width, CollisionHandler collisionHandler, Arena arena) {
        super(x, y, width, width, 10, 100, ShapeEnum.RECTANGLE, false, Images.getImage("tree.png"), collisionHandler, arena);
        double leavesWidth = width * 73 / 17;
        double leavesHeight = height * 82 / 14;
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
    public void collision(final CollisionEvent e) {
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

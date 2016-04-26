package carlorolf;

public class Tree extends ArenaObject {

    public Tree(final double x, final double y, final double width, CollisionHandler collisionHandler, Arena arena) {
        super(x, y, width, width, 10, 100, ShapeEnum.RECTANGLE, false, Images.getImage("stock.png"), collisionHandler, arena);
        layers.add(new VisibleObject(x, y, width * 3.5, height * 3.5, Images.getImage("tree_leaves.png"), arena) {
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

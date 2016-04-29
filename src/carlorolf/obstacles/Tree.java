package carlorolf.obstacles;

import carlorolf.Arena;
import carlorolf.ArenaObject;
import carlorolf.CollisionHandler;
import carlorolf.Images;
import carlorolf.VisibleObject;

/**
 * A tree which the player can chop down.
 */
public class Tree extends ArenaObject
{

    public Tree(final double x, final double y, final double diameter, CollisionHandler collisionHandler, Arena arena) {
        super(x, y, diameter, diameter, 10, 100, false, Images.getImage("tree.png"), collisionHandler, arena);
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

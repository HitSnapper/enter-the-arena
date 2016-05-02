package se.liu.ida.carro311rolsi701.tddd78.carlorolf.obstacles;

import se.liu.ida.carro311rolsi701.tddd78.carlorolf.*;

/**
 * A tree which the player can chop down.
 */
public class Tree extends ArenaObject {

    public Tree(final double x, final double y, final double diameter, CollisionHandler collisionHandler, Arena arena) {
        super(x, y, diameter, diameter, 10, 100, false, Images.getImage("tree"), collisionHandler, arena);
        final int leavesImageWidth = 73;
        final int leavesImageHeight = 82;
        final int stumpWidth = 17;
        final int stumpHeight = 14;
        double leavesWidth = width * leavesImageWidth / stumpWidth;
        double leavesHeight = height * leavesImageHeight / stumpHeight;
        layers.add(new Layer(x, y + height / 2 - leavesHeight / 2, leavesWidth, leavesHeight, Images.getImage("leaves"), arena));
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

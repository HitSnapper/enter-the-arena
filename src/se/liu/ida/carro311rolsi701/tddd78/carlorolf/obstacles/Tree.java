package se.liu.ida.carro311rolsi701.tddd78.carlorolf.obstacles;

import se.liu.ida.carro311rolsi701.tddd78.carlorolf.*;

/**
 * A tree which the player can chop down.
 */
public class Tree extends ArenaObject {
    private Layer leaves;

    public Tree(final double x, final double y, final double diameter, CollisionHandler collisionHandler, Arena arena) {
        super(new Body(new Vector(x, y), ShapeMaker.getRectangle(diameter, diameter), arena), 10, 500, false, Images.getImage("tree"), collisionHandler, arena);
        final int leavesImageWidth = 73;
        final int leavesImageHeight = 82;
        final int stumpWidth = 17;
        final int stumpHeight = 14;
        double leavesWidth = width * leavesImageWidth / stumpWidth;
        double leavesHeight = height * leavesImageHeight / stumpHeight;
        leaves = new Layer(x, y + height / 2 - leavesHeight / 2, leavesWidth, leavesHeight, Images.getImage("leaves"), arena);
        layers.add(leaves);
        arena.addTopLayer(leaves);
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
        if (!isDead()) {
            if (hp / (double)maximumHp < 0.2) {
                leaves.setImage(Images.getImage("leaves_damaged3"));
            } else if (hp / (double)maximumHp < 0.4) {
                leaves.setImage(Images.getImage("leaves_damaged2"));
            } else if (hp / (double)maximumHp < 0.6) {
                leaves.setImage(Images.getImage("leaves_damaged1"));
            } else if (hp / (double)maximumHp < 0.8) {
                leaves.setImage(Images.getImage("leaves_damaged0"));
            }
        }
    }
}

package se.liu.ida.carro311rolsi701.tddd78.carlorolf.obstacles;


import se.liu.ida.carro311rolsi701.tddd78.carlorolf.*;

/**
 * Stones are passive objects
 */
public class Stone extends ArenaObject {
    public Stone(final double x, final double y, final double radius, CollisionHandler collisionHandler,
                 Arena arena) {
        super(new Body(new Vector(x, y), ShapeMaker.getPolygon(8, radius), false), 100, 100, Images.getImage("stone"), 1, collisionHandler, arena);
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

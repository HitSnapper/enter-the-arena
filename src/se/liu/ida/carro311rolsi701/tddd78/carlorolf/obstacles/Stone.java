package se.liu.ida.carro311rolsi701.tddd78.carlorolf.obstacles;


import se.liu.ida.carro311rolsi701.tddd78.carlorolf.*;

/**
 * Stones are passive objects
 */
public class Stone extends ArenaObject
{
    public Stone(final double x, final double y, final double width, final double height, CollisionHandler collisionHandler,
		 Arena arena)
    {
	super(new Body(new Vector(x, y), ShapeMaker.getRectangle(width, height), arena), 100, 100, false, Images.getImage("stone"), collisionHandler, arena);
    }

    @Override protected void move(double movementSpeed) {

    }

    @Override protected void updateImage() {

    }

    @Override public void death() {
    }
}

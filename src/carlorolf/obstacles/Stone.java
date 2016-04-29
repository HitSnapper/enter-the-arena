package carlorolf.obstacles;


import carlorolf.Arena;
import carlorolf.ArenaObject;
import carlorolf.CollisionHandler;
import carlorolf.Images;

/**
 * Stones are passive objects
 */
public class Stone extends ArenaObject
{
    public Stone(final double x, final double y, final double width, final double height, CollisionHandler collisionHandler, Arena arena) {
        super(x, y, width, height, 100, 100, false, Images.getImage("stone_new.png"), collisionHandler, arena);
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

package carlorolf.obstacles;


import carlorolf.Arena;
import carlorolf.CollisionHandler;
import carlorolf.Images;

/**
 * The brickwall sets the boundaries of the arena and keeps the arenaObjects inside.
 */
public class BrickWall extends Wall {
    public BrickWall(final int x, final int y, final int width, final int height,
                     final CollisionHandler collisionHandler, final Arena arena) {
        super(x, y, width, height, Images.getImage("bluebrick"), collisionHandler, arena);
    }

    @Override protected void move(final double movementSpeed) {

    }

    @Override protected void updateImage() {

    }
}

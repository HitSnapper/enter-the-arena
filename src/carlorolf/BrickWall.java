package carlorolf;


/**
 * The brickwall sets the boundaries of the arena and keeps the arenaObjects inside.
 */
public class BrickWall extends Wall {
    public BrickWall(final double x, final double y, final int width, final int height,
                     final CollisionHandler collisionHandler, final Arena arena) {
        super(x, y, width, height, Images.getImage("bluebrick.png"), collisionHandler, arena);
    }
}

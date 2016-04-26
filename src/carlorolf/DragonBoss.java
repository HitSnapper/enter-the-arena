package carlorolf;

import java.awt.*;

public class DragonBoss extends Character
{
    public DragonBoss(final double x, final double y, final double width, final double height, final double movementSpeed,
		      final int hp, final ShapeEnum shapeEnum, final boolean movable, final Image image,
		      final CollisionHandler collisionHandler, final Arena arena)
    {
	super(x, y, 4, 4, 1, 1000, ShapeEnum.RECTANGLE, true, image, collisionHandler, arena);
	weapon = new Weapon(x, y, 10, 2 * width / 6, 0.5, this);
    }

    @Override
        protected void move(double movementSpeed) {
            if (coords.getDistance(target.coords) > width / 2) {
                double pX = target.getX() - x;
                double pY = target.getY() - y;
                double absP = Math.sqrt(Math.pow(pX, 2) + Math.pow(pY, 2));
                double k = absP / movementSpeed;
                double dX = pX / k;
                double dY = pY / k;
                x += dX;
                y += dY;
            }
        }
    

    @Override protected void hit() {

    }
}

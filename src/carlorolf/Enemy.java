package carlorolf;

import java.awt.*;

public class Enemy extends ArenaObject
{
    public Enemy(final int x, final int y, final double width, final double height, final ShapeEnum shapeEnum,
		 final boolean movable, final Image image)
    {
	super(x, y, width, height, shapeEnum, movable, image);
    }

    @Override public void Collision(final CollisionEvent e) {

    }
}

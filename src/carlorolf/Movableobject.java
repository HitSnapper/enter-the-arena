package carlorolf;

import java.awt.*;

public class Movableobject extends ArenaObject
{
    public Movableobject(final int x, final int y)
    {
	super(x, y, 0.5, 0.5, ShapeEnum.RECTANGLE, true, Images.getImage("object.png"));
    }

    @Override public void Collision(final CollisionEvent e) {
	width *= 1.001;
	height *= 1.001;
    }
}

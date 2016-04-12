package carlorolf;

import java.awt.Image;

public abstract class CollisionObject extends VisibleObject
{
    private Shape shape;

    public CollisionObject(int x, int y, Image image) {
	super(x, y, 0.5, 0.5, image);
    }

    public abstract void Collision(CollisionEvent e);
}

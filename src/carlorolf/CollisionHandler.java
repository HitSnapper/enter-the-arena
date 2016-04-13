package carlorolf;

import java.util.List;

public class CollisionHandler
{
    List<ArenaObject> objects;

    public CollisionHandler(Arena arena) {
	objects = arena.getObjectList();
    }

    public void update() {
	for (ArenaObject obj1 : objects) {
	    for (ArenaObject obj2 : objects) {
		if (!obj1.equals(obj2)) {
		    handleCollision(obj1, obj2);
		}
	    }
	}
    }

    private void handleCollision(ArenaObject a1, ArenaObject a2) {
	double a1_width = a1.getWidth() / 2;
	double a1_height = a1.getHeight() / 2;
	double a2_width = a2.getWidth() / 2;
	double a2_height = a2.getHeight() / 2;

	double dX = a2_width + a2.getX() - a1_width - a1.getX();
	double dY = a2_height + a2.getY() - a1_height - a1.getY();

	double horizontalCorrection = a1_width + a2_width - Math.abs(dX);
	double verticalCorrection = a1_height + a2_height - Math.abs(dY);

	boolean collision = !((a1_width + a2_width < Math.abs(dX)) || (a1_height + a2_height < Math.abs(dY)));

	if (collision) {
	    // Horizontal collision
	    if (horizontalCorrection < verticalCorrection) {
		if (!a1.isMovable()) {
		    a2.setXRelative((dX / Math.abs(dX)) * (a1_width + a2_width - Math.abs(dX)));
		} else if (!a2.isMovable()) {
		    a1.setXRelative((dX / Math.abs(dX)) * (a1_width + a2_width - Math.abs(dX)));
		} else if (a1.isMovable() && a2.isMovable()) {
		    a2.setXRelative((dX / Math.abs(dX)) * (a1_width + a2_width - Math.abs(dX)) / 2);
		    a1.setXRelative((dX / Math.abs(dX)) * (a1_width + a2_width - Math.abs(dX)) / -2);
		}
	    }
	    // Vertical collision
	    else if (verticalCorrection < horizontalCorrection) {
		if (!a1.isMovable()) {
		    a2.setYRelative((dY / Math.abs(dY)) * (a1_height + a2_height - Math.abs(dY)));
		} else if (!a2.isMovable()) {
		    a1.setYRelative((dY / Math.abs(dY)) * (a1_height + a2_height - Math.abs(dY)));
		} else if (a1.isMovable() && a2.isMovable()) {
		    a2.setYRelative((dY / Math.abs(dY)) * (a1_width + a2_width - Math.abs(dY)) / 2);
		    a1.setYRelative((dY / Math.abs(dY)) * (a1_width + a2_width - Math.abs(dY)) / -2);
		}
	    }
	    else{

	    }
	    a1.Collision(new CollisionEvent());
	    a2.Collision(new CollisionEvent());
	}
    }
}

package carlorolf;

import java.util.ArrayList;
import java.util.List;

public class CollisionHandler
{
    List<ArenaObject> objects;
	List<Weapon> weapons;

    public CollisionHandler(Arena arena) {
		objects = arena.getObjectList();
		weapons = new ArrayList<>();
    }

    public void update() {
		// Checking collision between weapons and ArenaObjects
		for (Weapon weapon : weapons) {
			for (ArenaObject arenaObject : objects) {
				handleWeaponCollision(weapon, arenaObject);
			}
		}

		// Checking collision between ArenaObjects
		for (ArenaObject obj1 : objects) {
			for (ArenaObject obj2 : objects) {
				if (!obj1.equals(obj2)) {
					handleCollision(obj1, obj2);
				}
			}
		}
    }

	public void addWeapon(Weapon weapon){
		weapons.add(weapon);
	}

	private void handleWeaponCollision(Weapon weapon, ArenaObject arenaObject){

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
			a1.Collision(new CollisionEvent(a2, Direction.NONE));
			a2.Collision(new CollisionEvent(a1, Direction.NONE));
		}
    }
}

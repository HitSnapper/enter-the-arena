package carlorolf;

import java.util.ArrayList;
import java.util.List;

public class CollisionHandler
{
    List<ArenaObject> objects;
    List<Weapon> weapons;

    public CollisionHandler() {
	weapons = new ArrayList<Weapon>();
    }

    public void addArena(Arena arena){
	objects = arena.getObjectList();
    }

    public void update() {
	// Collision between ArenaObjects and Weapons
	for (ArenaObject arenaObject : objects) {
	    for (Weapon weapon : weapons) {
		if (weapon.getOwner() != arenaObject && arenaObject.isMovable())
		    handleWeaponCollision(weapon, arenaObject);
	    }
	}
	weapons.clear();

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

    public void addWeapon(Weapon weapon) {
	weapons.add(weapon);
    }

    private void handleWeaponCollision(Weapon weapon, ArenaObject arenaObject) {
	if (collisionWeaponObject(weapon, arenaObject)){
	    arenaObject.weaponCollision(weapon);
	}
    }

    private boolean collisionWeaponObject(Weapon weapon, ArenaObject arenaObject){
	double weapon_width = weapon.getWidth() / 2;
	double weapon_height = weapon.getHeight() / 2;
	double arenaObject_width = arenaObject.getWidth() / 2;
	double arenaObject_height = arenaObject.getHeight() / 2;

	double dX = arenaObject_width + arenaObject.getX() - weapon_width - weapon.getX();
	double dY = arenaObject_height + arenaObject.getY() - weapon_height - weapon.getY();

	boolean collision = !((weapon_width + arenaObject_width < Math.abs(dX)) || (weapon_height + arenaObject_height < Math.abs(dY)));

	return collision;
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


	if (collision && (a2.isMovable() || a1.isMovable()) && a2.isMovable() != a1.isMovable()){
	    ArenaObject moved = new Player(0, 0, null);
	    ArenaObject notMoved = new Player(0, 0, null);
	    if (!a1.isMovable() && a2.isMovable()) {
		moved = a2;
		notMoved = a1;
	    }
	    else if (!a2.isMovable() && a1.isMovable()) {
		moved = a1;
		notMoved = a2;
	    }
	    boolean moved_right = (moved.getX() - moved.getOldCoords().getX() > 0);
	    boolean moved_left = (moved.getX() - moved.getOldCoords().getX() < 0);
	    boolean moved_up = (moved.getY() - moved.getOldCoords().getY() < 0);
	    boolean moved_down = (moved.getY() - moved.getOldCoords().getY() > 0);

	    if (Math.abs(moved.getX() - moved.getOldCoords().getX()) > Math.abs(moved.getY() - moved.getOldCoords().getY()) ){
		//(horizontalCorrection < verticalCorrection && (moved_left || moved_right)) || (horizontalCorrection >= verticalCorrection && (moved_left || moved_right) && !moved_up && !moved_down)){
		if (moved_left){
		    moved.setX(notMoved.getX() + notMoved.getWidth());
		}
		else{
		    moved.setX(notMoved.getX() - moved.getWidth());
		}
	    }
	    else {
		if (moved_down){
		    moved.setY(notMoved.getY() - moved.getHeight());
		}
		else{
		    moved.setY(notMoved.getY() + notMoved.getHeight());
		}
	    }
	}


	else if (collision){// && !(a2.isMovable() || a1.isMovable()) && a2.isMovable() != a1.isMovable()) {
	    // Horizontal collision
	    if (horizontalCorrection < verticalCorrection) {
		if (!a1.isMovable() && a2.isMovable()) {
		    a2.setXRelative((dX / Math.abs(dX)) * (a1_width + a2_width - Math.abs(dX)));
		} else if (!a2.isMovable() && a1.isMovable()) {
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

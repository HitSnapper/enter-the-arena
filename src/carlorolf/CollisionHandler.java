package carlorolf;

import java.util.ArrayList;
import java.util.List;

/**
 * The CollisionHandler decides whats gonna happen when different kinds of objects collide.
 */
public class CollisionHandler {
    private List<ArenaObject> objects;
    private List<Weapon> weapons;
    private List<ArenaObject> removeObjectsList;

    public CollisionHandler() {
        weapons = new ArrayList<>();
        removeObjectsList = new ArrayList<>();

    }

    public void addArena(Arena arena) {
        objects = arena.getObjects();
    }

    public void update() {
        for (ArenaObject arenaObject : removeObjectsList) {
            objects.remove(arenaObject);

        }
        // Collision between ArenaObjects and Weapons
        for (ArenaObject arenaObject : objects) {
            for (Weapon weapon : weapons) {
                if (weapon.getOwner() != arenaObject)
                    handleWeaponCollision(weapon, arenaObject);
            }
        }
        weapons.clear();

        // Checking collision between ArenaObjects
        for (ArenaObject obj1 : objects) {
            for (ArenaObject obj2 : objects) {
                if (!obj1.equals(obj2)) {
                    handleCollision(obj1, obj2);
                }
            }
        }
    }

    public void removeObject(ArenaObject object) {
        removeObjectsList.add(object);
    }

    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }

    private void handleWeaponCollision(Weapon weapon, ArenaObject arenaObject) {
        if (collisionWeaponObject(weapon, arenaObject)) {
            arenaObject.weaponCollision(weapon);
        }
    }

    private boolean collisionWeaponObject(Weapon weapon, ArenaObject arenaObject) {
        double weaponWidth = weapon.getWidth() / 2;
        double weaponHeight = weapon.getHeight() / 2;
        double arenaObjectWidth = arenaObject.getWidth() / 2;
        double arenaObjectHeight = arenaObject.getHeight() / 2;

        double dX = arenaObject.getX() - weapon.getX();
        double dY = arenaObject.getY() - weapon.getY();

        boolean collision =
                !((weaponWidth + arenaObjectWidth < Math.abs(dX)) || (weaponHeight + arenaObjectHeight < Math.abs(dY)));

        return collision;
    }

    private void handleCollision(ArenaObject a1, ArenaObject a2) {
        double a1Width = a1.getWidth() / 2;
        double a1Height = a1.getHeight() / 2;
        double a2Width = a2.getWidth() / 2;
        double a2Height = a2.getHeight() / 2;

        double dX = a2.getX() - a1.getX();
        double dY = a2.getY() - a1.getY();

        double horizontalCorrection = a1Width + a2Width - Math.abs(dX);
        double verticalCorrection = a1Height + a2Height - Math.abs(dY);

        boolean collision = !((a1Width + a2Width < Math.abs(dX)) || (a1Height + a2Height < Math.abs(dY)));

        if (collision) {
            // Horizontal collision
            if (horizontalCorrection < verticalCorrection) {
                if (!a1.isMovable() && a2.isMovable()) {
                    a2.setXRelative((dX / Math.abs(dX)) * (a1Width + a2Width - Math.abs(dX)));
                } else if (!a2.isMovable() && a1.isMovable()) {
                    a1.setXRelative((dX / Math.abs(dX)) * (a1Width + a2Width - Math.abs(dX)));
                } else if (a1.isMovable() && a2.isMovable()) {
                    a2.setXRelative((dX / Math.abs(dX)) * (a1Width + a2Width - Math.abs(dX)) / 2);
                    a1.setXRelative((dX / Math.abs(dX)) * (a1Width + a2Width - Math.abs(dX)) / -2);
                }
            }
            // Vertical collision
            else if (verticalCorrection < horizontalCorrection) {
                if (!a1.isMovable()) {
                    a2.setYRelative((dY / Math.abs(dY)) * (a1Height + a2Height - Math.abs(dY)));
                } else if (!a2.isMovable()) {
                    a1.setYRelative((dY / Math.abs(dY)) * (a1Height + a2Height - Math.abs(dY)));
                } else if (a1.isMovable() && a2.isMovable()) {
                    a2.setYRelative((dY / Math.abs(dY)) * (a1Width + a2Width - Math.abs(dY)) / 2);
                    a1.setYRelative((dY / Math.abs(dY)) * (a1Width + a2Width - Math.abs(dY)) / -2);
                }
            }
            a1.collision(new CollisionEvent(a2, Direction.NONE));
            a2.collision(new CollisionEvent(a1, Direction.NONE));
        }
    }

    public void clearAll() {
        weapons.clear();
        objects.clear();
        removeObjectsList.clear();
    }
}

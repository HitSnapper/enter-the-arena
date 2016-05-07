package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import java.util.ArrayList;
import java.util.List;

/**
 * The CollisionHandler decides what's going to happen when objects collide.
 */
public class CollisionHandler {
    private List<ArenaObject> objects;
    private List<Weapon> weapons;
    private List<ArenaObject> removeObjectsList;

    public CollisionHandler() {
        weapons = new ArrayList<>();
        removeObjectsList = new ArrayList<>();
        objects = new ArrayList<>();
    }

    public void addArena(Arena arena) {
        objects = arena.getObjects();
    }

    public void update() {
        // Collision between ArenaObjects and Weapons
        List<ArenaObject> tempObjects = new ArrayList<>(objects);
        List<Weapon> tempWeapons = new ArrayList<>(weapons);
        for (ArenaObject arenaObject : tempObjects) {
            for (Weapon weapon : tempWeapons) {
                if (!weapon.getOwner().equals(arenaObject)) handleWeaponCollision(weapon, arenaObject);
            }
        }
        weapons.clear();

        // Checking collision between ArenaObjects
        for (ArenaObject obj1 : tempObjects) {
            for (ArenaObject obj2 : tempObjects) {
                if (!obj1.equals(obj2)) {
                    handleCollision(obj1, obj2);
                }
            }
        }
    }

    public void addObject(ArenaObject arenaObject){
        objects.add(arenaObject);
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
        }
    }

    public void clearAll() {
        weapons.clear();
        objects.clear();
        removeObjectsList.clear();
    }
}
package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import se.liu.ida.carro311rolsi701.tddd78.carlorolf.friendlycharacters.Player;

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

    public void addObject(ArenaObject arenaObject) {
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

    private Shape minkowskiSum(Body a, Body b) {
        List<Vector> sum = new ArrayList<>();
        double aX = a.getX();
        double aY = a.getY();
        double bX = b.getX();
        double bY = b.getY();
        for (Vector vector : a.getShape().getNodes()) {
            for (Vector vector1 : b.getShape().getNodes()) {
                sum.add(new Vector(aX + vector.getX() - bX - vector1.getX(), aY + vector.getY() - bY - vector1.getY()));
            }
        }
        return new Shape(sum);
    }

    private Vector getOriginDistance(Shape poly) {
        Vector res = null;
        for (int i = 0; i < poly.getNodes().size(); i++){
            Vector a = poly.getNodes().get(i);
            Vector b = poly.getNodes().get((i + 1) % poly.getNodes().size());
            Vector ab = b.addition(new Vector(-a.getX(), -a.getY()));
            Vector a0 = new Vector(-a.getX(), -a.getY());
            double projection = a0.dot(ab);
            double lengthSquared = ab.dot(ab);
            double distance = projection/lengthSquared;
            Vector temp = ab.times(distance).addition(a);
            if (res == null){
                res = temp;
            }
            else if(res.getDistanceToOrigin() > temp.getDistanceToOrigin()){
                res = temp;
            }
        }
        return res;
    }

    private boolean originInPolygon(List<Vector> vectors) {
        int i, j, nvert = vectors.size();
        boolean c = false;

        for (i = 0, j = nvert - 1; i < nvert; j = i++) {
            if (((vectors.get(i).getY() >= 0) != (vectors.get(j).getY() >= 0)) &&
                    (0 <= (vectors.get(j).getX() - vectors.get(i).getX()) * (0 - vectors.get(i).getY()) / (vectors.get(j).getY() - vectors.get(i).getY()) + vectors.get(i).getX())
                    ) {
                c = !c;
            }
        }

        return c;
    }

    private void handleCollision(ArenaObject a1, ArenaObject a2) {
        Shape poly = minkowskiSum(a1.getBody(), a2.getBody());
        boolean collision = originInPolygon(poly.getNodes());
        if (a1 instanceof Player && collision) {
            System.out.println(a2);
        }
        if (collision) {
            Vector distance = getOriginDistance(poly);
            int a1movable = (a1.isMovable()) ? 1:0;
            int a2movable = (a2.isMovable()) ? 1:0;
            boolean bothMovable = a1.isMovable() && a2.isMovable();
            if (bothMovable){
                Vector half = distance.times(0.5);
                a1.addCoords(half.times(-a1movable));
                a2.addCoords(half.times(a2movable));
            }
            else {
                a1.addCoords(distance.times(-a1movable));
                a2.addCoords(distance.times(a2movable));
            }
        }
    }

    public void clearAll() {
        weapons.clear();
        objects.clear();
        removeObjectsList.clear();
    }
}

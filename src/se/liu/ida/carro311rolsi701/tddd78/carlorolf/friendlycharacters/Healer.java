package se.liu.ida.carro311rolsi701.tddd78.carlorolf.friendlycharacters;

import se.liu.ida.carro311rolsi701.tddd78.carlorolf.*;
import se.liu.ida.carro311rolsi701.tddd78.carlorolf.Character;

import java.awt.Image;

/**
 * Movable objects wich you can to gain its health
 */
public class Healer extends Character {
    //This is static so it can be accessed in the super constructor
    final static double SIZE = 0.7;
    private Image sad;
    private Image normal;
    private Vector spawnCoords;
    private double healthRegen;

    public Healer(final double x, final double y, CollisionHandler collisionHandler, Arena arena) {
        super(new Body(new Vector(x, y), ShapeMaker.getRectangle(SIZE, SIZE), true), 1, 100, 0, true, "object", collisionHandler, arena);
        sad = Images.getImage("object_sad");
        normal = Images.getImage("object_none");
        spawnCoords = new Vector(x, y);
        healthRegen = 0;
    }

    @Override
    protected void move(final double movementSpeed) {
        Player unTarget = null;
        if (arena.getAlivePlayers().size() > 0) {
            unTarget = arena.getAlivePlayers().get(0);
        }
        for (Player player : arena.getAlivePlayers()) {
            if (unTarget != null) {
                if (coords.getDistance(unTarget.getCoords()) > coords.getDistance(player.getCoords())) {
                    unTarget = player;
                }
            }
        }
        if (unTarget != null && coords.getDistance(unTarget.getCoords()) < 4) {
            image = sad;
            if (coords.getDistance(unTarget.getCoords()) > getWidth() / 2) {
                double pX = unTarget.getX() - getX();
                double pY = unTarget.getY() - getY();
                double absP = Math.sqrt(Math.pow(pX, 2) + Math.pow(pY, 2));
                double k = absP / movementSpeed;
                double dX = pX / k;
                double dY = pY / k;
                addX(-dX);
                addY(-dY);
            }
        } else {
            image = normal;
            if (spawnCoords.getDistance(coords) > getWidth() / 2) {
                double pX = spawnCoords.getX() - getX();
                double pY = spawnCoords.getY() - getY();
                double absP = Math.sqrt(Math.pow(pX, 2) + Math.pow(pY, 2));
                double k = absP / movementSpeed;
                double dX = pX / k;
                double dY = pY / k;
                addX(dX);
                addY(dY);
            }
        }
    }

    @Override
    public void weaponCollision(Weapon weapon) {
        super.weaponCollision(weapon);
        if (this.hp > 0 && armor.getToughness() > 0) {
            weapon.getOwner().heal(
                    (int) (((double) (armor.getMaxToughness() - armor.getToughness()) / armor.getMaxToughness()) *
                            arena.getPlayer(0).getWeapon().getDamage()));
        } else {
            weapon.getOwner().heal(arena.getPlayer(0).getWeapon().getDamage());
        }
    }

    @Override
    protected void updateImage() {

    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
    }
}
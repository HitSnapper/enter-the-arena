package carlorolf.friendlycharacters;

import carlorolf.Arena;
import carlorolf.Character;
import carlorolf.CollisionHandler;
import carlorolf.Images;
import carlorolf.Weapon;

import java.awt.Image;

/**
 * Movable objects wich you can to gain its health
 */
public class Healer extends Character
{
    //This is static so it can be accessed in the super constructor
    final static double SIZE = 0.7;
    private Image sad;
    private Image normal;

    public Healer(final double x, final double y, CollisionHandler collisionHandler, Arena arena) {
        super(x, y, SIZE, SIZE, 1, 100, 0, true, "object", collisionHandler, arena);
        sad = Images.getImage("object_sad");
        normal = Images.getImage("object_none");
    }

    @Override
    protected void move(final double movementSpeed) {
        Player unTarget = arena.getPlayer();
        if (coords.getDistance(unTarget.getCoords()) < 4) {
            image = sad;
            if (coords.getDistance(unTarget.getCoords()) > width / 2) {
                double pX = unTarget.getX() - x;
                double pY = unTarget.getY() - y;
                double absP = Math.sqrt(Math.pow(pX, 2) + Math.pow(pY, 2));
                double k = absP / movementSpeed;
                double dX = pX / k;
                double dY = pY / k;
                x -= dX;
                y -= dY;
            }
        }
        else
            image = normal;
    }
    @Override public void weaponCollision(Weapon weapon) {
        super.weaponCollision(weapon);
        if (this.hp > 0 && armor.getToughness() > 0) {
                 arena.getPlayer().addHealth((int) (((double) (armor.getMaxToughness() - armor.getToughness()) / armor.getMaxToughness()) * arena.getPlayer().getWeapon().getDamage()));
             } else {
                 arena.getPlayer().addHealth( arena.getPlayer().getWeapon().getDamage());
             }

    }

    @Override
    protected void updateImage() {

    }
}
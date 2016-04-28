package carlorolf;

import java.awt.Image;

/**
 * Enemies with a simple Ai wich follows the player and damages it if its in range.
 */
public abstract class Enemy extends Character {
    private ArenaObject target;

    protected Enemy(final double x, final double y, double width, double height, double movementSpeed, int hp, double attackSpeed, Image image, CollisionHandler collisionHandler, Arena arena) {
        super(x, y, width, height, movementSpeed, hp, attackSpeed, ShapeEnum.RECTANGLE, true, image, collisionHandler, arena);
        this.target = arena.getPlayer();
    }

    @Override
    protected void move(double movementSpeed) {
        if (coords.getDistance(target.coords) > width / 2) {
            double pX = target.getX() - x;
            double pY = target.getY() - y;
            double absP = Math.sqrt(Math.pow(pX, 2) + Math.pow(pY, 2));
            double k = absP / movementSpeed;
            double dX = pX / k;
            double dY = pY / k;
            x += dX;
            y += dY;
        }
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        if (coords.getDistance(target.coords) - target.getWidth() - width / 2 < weapon.getRange()) {
            hit();
        }

        // Perhaps add an ArenaObject as a main target?
    }

    @Override
    protected void hit() {
        if (canAttack && weapon != null) {
            double dX = x - target.getX();
            double dY = y - target.getY();
            double wAbs = width / 2 + weapon.getRange() / 2;
            double k = wAbs / coords.getDistance(target.getCoords());
            double wX = x - k * dX;
            double wY = y - k * dY;

            weapon.setHittingDirection(movingDirection, wX, wY);
            collisionHandler.addWeapon(weapon);
        }
    }
}

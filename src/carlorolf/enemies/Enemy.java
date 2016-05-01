package carlorolf.enemies;

import carlorolf.Arena;
import carlorolf.ArenaObject;
import carlorolf.Character;
import carlorolf.CollisionHandler;
import carlorolf.Direction;

/**
 * Enemies with a simple AI wich follows the player and damages it if its in range.
 */
public abstract class Enemy extends Character
{
    private ArenaObject target;

    protected Enemy(final double x, final double y, double width, double height, double movementSpeed, int hp, double attackSpeed, String imageName, CollisionHandler collisionHandler, Arena arena) {
        super(x, y, width, height, movementSpeed, hp, attackSpeed, true, imageName, collisionHandler, arena);
        this.target = arena.getPlayer();
    }

    @Override
    protected void move(double movementSpeed) {
        if (coords.getDistance(target.getCoords()) > width / 2) {
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

    private void updateDirection() {
        double dX = x - oldCoords.getX();
        double dY = y - oldCoords.getY();

        double angle = dY / dX;
        // Used to check the angle of the object's moving direction
        final double temp = 0.5;

        // Calculating what direction the enemy is looking in

        if (Math.abs(dY / dX) < temp || Math.abs(dX / dY) < temp) {
            if (Math.abs(dX) > Math.abs(dY) && dX > 0) {
                movingDirection = Direction.EAST;
            } else if (Math.abs(dX) < Math.abs(dY) && dY > 0) {
                movingDirection = Direction.SOUTH;
            } else if (Math.abs(dX) > Math.abs(dY) && dX < 0) {
                movingDirection = Direction.WEST;
            } else if (Math.abs(dX) < Math.abs(dY) && dY < 0) {
                movingDirection = Direction.NORTH;
            }
        } else {
            if (angle > 0 && dX > 0) {
                movingDirection = Direction.SOUTHEAST;
            } else if (angle < 0 && dX > 0) {
                movingDirection = Direction.NORTHEAST;
            } else if (angle > 0 && dX < 0) {
                movingDirection = Direction.NORTHWEST;
            } else if (angle < 0 && dX < 0) {
                movingDirection = Direction.SOUTHWEST;
            }
        }
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        if (coords.getDistance(target.getCoords()) - target.getWidth() - width / 2 < weapon.getRange()) {
            hit();
        }
        updateDirection();
    }

    private void hit() {
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

package se.liu.ida.carro311rolsi701.tddd78.carlorolf.enemies;

import se.liu.ida.carro311rolsi701.tddd78.carlorolf.*;
import se.liu.ida.carro311rolsi701.tddd78.carlorolf.Character;
import se.liu.ida.carro311rolsi701.tddd78.carlorolf.friendlycharacters.Player;

import java.util.Random;

/**
 * Enemies with a simple EnemyAI wich follows the player and damages it if its in range.
 */
public abstract class Enemy extends Character {
    private EnemyAI enemyAI;

    protected Enemy(final double x, final double y, double size, double movementSpeed, int hp,
                    double attackSpeed, Weapon weapon, String imageName, CollisionHandler collisionHandler, Arena arena) {
        super(new Body(new Vector(x, y), ShapeMaker.getSquare(size), true), movementSpeed, hp, attackSpeed, true, weapon, imageName, collisionHandler, arena);
        this.enemyAI = new EnemyAI(this, collisionHandler, arena);
    }

    @Override
    protected void move(double movementSpeed) {
        enemyAI.move(movementSpeed);
    }

    private void updateDirection() {
        double dX = getX() - oldCoords.getX();
        double dY = getY() - oldCoords.getY();

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
        enemyAI.update();
        updateDirection();
    }
}

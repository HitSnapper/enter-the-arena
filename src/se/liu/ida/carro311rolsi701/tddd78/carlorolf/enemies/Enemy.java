package se.liu.ida.carro311rolsi701.tddd78.carlorolf.enemies;

import se.liu.ida.carro311rolsi701.tddd78.carlorolf.*;
import se.liu.ida.carro311rolsi701.tddd78.carlorolf.Character;
import se.liu.ida.carro311rolsi701.tddd78.carlorolf.friendlycharacters.Player;

import java.util.Random;

/**
 * Enemies with a simple EnemyAI wich follows the player and damages it if its in range.
 */
public abstract class Enemy extends Character {
    private ArenaObject target;
    private Vector nextPoint;
    private EnemyAI enemyAI;

    protected Enemy(final double x, final double y, double size, double movementSpeed, int hp,
                    double attackSpeed, String imageName, CollisionHandler collisionHandler, Arena arena) {
        super(new Body(new Vector(x, y), ShapeMaker.getOctagon(size/2), true), movementSpeed, hp, attackSpeed, true, imageName, collisionHandler, arena);
        Random rand = new Random();
        this.target = arena.getPlayer(rand.nextInt(arena.getNumberOfAlivePlayers()));
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

    private boolean targetInReach(){
        return coords.getDistance(target.getCoords()) - target.getWidth()/2 - getWidth() / 2 < weapon.getRange() && !target.isDead();
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        enemyAI.update();
        if (targetInReach()) {
            hit();
        }
        updateDirection();
    }

    private void hit() {
        if (canAttack && weapon != null) {
            startAttackDelay();
            double dX = getX() - target.getX();
            double dY = getY() - target.getY();
            double wAbs = getWidth() / 2 + weapon.getRange() / 2;
            double k = wAbs / coords.getDistance(target.getCoords());
            double wX = getX() - k * dX;
            double wY = getY() - k * dY;

            weapon.setHittingDirection(movingDirection, wX, wY);
            collisionHandler.addWeapon(weapon);
        }
    }
}

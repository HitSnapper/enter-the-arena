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
    private EnemyAI enemyAi;

    protected Enemy(final double x, final double y, double size, double movementSpeed, int hp,
                    double attackSpeed, String imageName, CollisionHandler collisionHandler, Arena arena) {
        super(new Body(new Vector(x, y), ShapeMaker.getSquare(size/2), arena), movementSpeed, hp, attackSpeed, true, imageName, collisionHandler, arena);
        Random rand = new Random();
        this.target = arena.getPlayer(rand.nextInt(arena.getNumberOfAlivePlayers()));
        this.enemyAi = new EnemyAI(this, collisionHandler, arena);
    }

    @Override
    protected void move(double movementSpeed) {
        if (coords.getDistance(target.getCoords()) > getWidth() / 2) {
            double pX = target.getX() - getX();
            double pY = target.getY() - getY();
            double absP = Math.sqrt(Math.pow(pX, 2) + Math.pow(pY, 2));
            double k = absP / movementSpeed;
            double dX = pX / k;
            double dY = pY / k;
            addX(dX);
            addY(dY);
        }
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

    private void updateTarget(){
        ArenaObject temp = arena.getPlayer(0);
        for (Player player : arena.getPlayers()) {
            if (coords.getDistance(temp.getCoords()) > coords.getDistance(player.getCoords()) && !player.isDead()){
                temp = player;
            }
            else if (temp.isDead()){
                temp = player;
            }
        }

        //Perhaps chase healers if player is dead?
        target = temp;
    }

    private boolean targetInReach(){
        return coords.getDistance(target.getCoords()) - target.getWidth()/2 - getWidth() / 2 < weapon.getRange() && !target.isDead();
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        if (targetInReach()) {
            hit();
        }
        updateTarget();
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

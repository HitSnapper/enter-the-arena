package carlorolf;

import java.awt.*;

/**
 * The character is a movable object which can attack and move in different ways depending on it's stats.
 */
public abstract class Character extends ArenaObject {
    protected Weapon weapon;
    protected double attackSpeed;
    protected double attackTimer;
    protected boolean canAttack;
    protected final Image NORTH;
    protected final Image NORTHWEST;
    protected final Image NORTHEAST;
    protected final Image SOUTH;
    protected final Image SOUTHWEST;
    protected final Image SOUTHEAST;
    protected final Image WEST;
    protected final Image EAST;
    protected final Image NONE;

    protected Character(final double x, final double y, final double width, final double height, final double movementSpeed,
                     final int hp, final double attackSpeed, final boolean movable, final String imageString,
                     final CollisionHandler collisionHandler, final Arena arena) {
        super(x, y, width, height, movementSpeed, hp, movable, Images.getImage(imageString + "_none.png"), collisionHandler, arena);
        weapon = new Weapon(0, 0, 0, 0, 0, this);
        this.attackSpeed = attackSpeed;
        canAttack = true;
        attackTimer = 0;
        NONE = Images.getImage(imageString + "_none.png");
        NORTH = Images.getImage(imageString + "_north.png");
        NORTHEAST = Images.getImage(imageString + "_northeast.png");
        NORTHWEST = Images.getImage(imageString + "_northwest.png");
        EAST = Images.getImage(imageString + "_east.png");
        WEST = Images.getImage(imageString + "_west.png");
        SOUTHEAST = Images.getImage(imageString + "_southeast.png");
        SOUTHWEST = Images.getImage(imageString + "_southwest.png");
        SOUTH = Images.getImage(imageString + "_south.png");
    }

    @Override
    protected void updateImage() {
        switch(movingDirection){
            case NORTH:
                image = NORTH;
                break;
            case SOUTH:
                image = SOUTH;
                break;
            case EAST:
                image = EAST;
                break;
            case WEST:
                image = WEST;
                break;
            case NORTHWEST:
                image = NORTHWEST;
                break;
            case NORTHEAST:
                image = NORTHEAST;
                break;
            case SOUTHWEST:
                image = SOUTHWEST;
                break;
            case SOUTHEAST:
                image = SOUTHEAST;
                break;
            case NONE:
                image = NONE;
                break;
        }
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public double getAttackSpeed() {
        return attackSpeed * weapon.getAttackSpeed();
    }

    public double getAttackTimer() {
        return attackTimer;
    }

    public void startAttackDelay() {
        attackTimer = attackSpeed * weapon.getAttackSpeed();
        canAttack = false;
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        if (attackTimer > 0) {
            attackTimer -= deltaTime;
        } else if (!canAttack) {
            canAttack = true;
        }
    }
}

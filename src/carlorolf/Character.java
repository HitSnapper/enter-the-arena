package carlorolf;

import java.awt.*;

/**
 * The character is a movable object which can attack and move in different ways depending on its stats.
 */
public abstract class Character extends ArenaObject {
    protected Weapon weapon;
    protected double attackSpeed;
    protected double attackTimer;
    protected boolean canAttack;

    protected Character(final double x, final double y, final double width, final double height, final double movementSpeed,
                     final int hp, final double attackSpeed, final ShapeEnum shapeEnum, final boolean movable, final Image image,
                     final CollisionHandler collisionHandler, final Arena arena) {
        super(x, y, width, height, movementSpeed, hp, shapeEnum, movable, image, collisionHandler, arena);
        weapon = new Weapon(0, 0, 0, 0, 0, this);
        this.attackSpeed = attackSpeed;
        canAttack = true;
        attackTimer = 0;
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

    public boolean canAttack() {
        return canAttack;
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

    protected abstract void hit();
}

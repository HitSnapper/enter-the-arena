package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

/**
 * Gives the characters something to deal damage between eachother with.
 */
public class Weapon {
    private Direction hittingDirection;
    private final int damage;
    private double x, y;
    private final double range;
    //Here to keep a reference to the player or enemy
    private Character owner;
    private final double attackSpeed;

    //Character is to keep a reference to the player or enemy
    public Weapon(int damage, double range, double attackSpeed) {
        this.hittingDirection = Direction.NONE;
        this.damage = damage;
        this.range = range;
        this.attackSpeed = attackSpeed;
    }

    public double getRange() {
        return range;
    }

    public Character getOwner() {
        return owner;
    }

    Direction getHittingDirection() {
        return hittingDirection;
    }

    public void setOwner(Character owner){
        this.owner = owner;
        x = owner.getX();
        y = owner.getY();
    }

    public void setHittingDirection(final Direction hittingDirection, double x, double y) {
        this.hittingDirection = hittingDirection;
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getDamage() {
        return damage;
    }

    public double getWidth() {
        return range;
    }

    public double getHeight() {
        return range;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }
}

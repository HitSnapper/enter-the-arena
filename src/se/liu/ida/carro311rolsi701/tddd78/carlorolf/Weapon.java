package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

/**
 * Gives the characters something to deal damage between eachother with.
 */
public class Weapon
{
    private Direction hittingDirection;
    private final int damage;
    private Shape shape;
    private double x, y;
    private final double range;
    //Here to keep a reference to the player or enemy
    private Character owner;
    private final double attackSpeed;

    //Character is to keep a reference to the player or enemy
    public Weapon(double x, double y, int damage, double range, double attackSpeed, Character owner) {
	this.owner = owner;
	this.hittingDirection = Direction.NONE;
	this.damage = damage;
	shape = new Shape(range, range);
	this.range = range;
	this.x = x;
	this.y = y;
	this.attackSpeed = attackSpeed;
    }

    public double getRange() {
	return range;
    }

    public ArenaObject getOwner() {
	return owner;
    }

    Direction getHittingDirection() {
	return hittingDirection;
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
	owner.startAttackDelay();
	return damage;
    }

    public double getWidth() {
	return shape.getWidth();
    }

    public double getHeight() {
	return shape.getHeight();
    }

    public double getAttackSpeed() {
	return attackSpeed;
    }
}

package carlorolf;

public class Weapon {
    private Direction hittingDirection;
    private int damage;
    private Shape shape;
    private double x, y;
    private double range;
    private ArenaObject owner;

    public Weapon(double x, double y, int damage, double range, ArenaObject owner) {
        this.owner = owner;
        this.hittingDirection = Direction.NONE;
        this.damage = damage;
        shape = new Shape(range, range);
	this.range = range;
        this.x = x;
        this.y = y;
    }

    public double getRange() {
	return range;
    }

    public ArenaObject getOwner() {
        return owner;
    }

    public Direction getHittingDirection() {
        return hittingDirection;
    }

    public void setHittingDirection(final Direction hittingDirection) {
        this.hittingDirection = hittingDirection;
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
        return shape.getWidth();
    }

    public double getHeight() {
        return shape.getHeight();
    }

    public void update(){
	x = owner.getX();
	y = owner.getY();
    }
}

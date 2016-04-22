package carlorolf;

public class Weapon {
    private Direction hittingDirection;
    private int damage;
    private Shape shape;
    private double x, y;
    private ArenaObject owner;

    public Weapon(Direction hittingDirection, double x, double y, int damage, double range, ArenaObject owner) {
        this.owner = owner;
        this.hittingDirection = hittingDirection;
        this.damage = damage;
        shape = new Shape(range, range);
        this.x = x;
        this.y = y;
    }

    public ArenaObject getOwner() {
        return owner;
    }

    public Direction getHittingDirection() {
        return hittingDirection;
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
}

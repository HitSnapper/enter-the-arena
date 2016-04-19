package carlorolf;

import java.awt.Image;

public abstract class ArenaObject extends VisibleObject
{
    private boolean movable;
    private Shape shape;
    protected double movementSpeed;
    protected Vector recoil;
    protected CollisionHandler collisionHandler;
    protected Vector oldCoords;

    public ArenaObject(int x, int y, double width, double height, double movementSpeed, ShapeEnum shapeEnum, boolean movable,
		       Image image, CollisionHandler collisionHandler)
    {
	super(x, y, width, height, image);
	this.collisionHandler = collisionHandler;
	this.movable = movable;
	this.movementSpeed = movementSpeed;
	recoil = new Vector(0, 0);
	if (shapeEnum == ShapeEnum.RECTANGLE) this.shape = new Shape(width, height);
	else if (shapeEnum == ShapeEnum.CIRCLE) this.shape = new Shape(width / 2);
	oldCoords = new Vector(x, y);
    }

    public Shape getShape() {
	return shape;
    }

    public boolean isMovable() {
	return movable;
    }

    public abstract void Collision(CollisionEvent e);

    public abstract void weaponCollision(Weapon weapon);

    public void addRecoil(Vector v) {
	recoil.add(v);
    }

    public void applyRecoil() {
	x += recoil.getX();
	y += recoil.getY();
	reduceRecoil();
    }

    private void reduceRecoil() {
	recoil.scale(0.6);
    }

    protected abstract void move();

    protected abstract void updateImage();

    @Override public boolean equals(Object other) {
	if (!(other instanceof ArenaObject)) {
	    return false;
	}

	ArenaObject that = (ArenaObject) other;

	// Custom equality check here.
	return this.getX() == that.getX() && this.getY() == that.getY() &&
	       this.getWidth() == that.getWidth() && this.getHeight() == that.getHeight() &&
	       this.getShape() == that.getShape();
    }

    @Override public void update(){
	oldCoords.set(x, y);
	move();
	updateImage();
	applyRecoil();
    }
}

package carlorolf;

import java.awt.Image;

public abstract class ArenaObject extends VisibleObject
{
    private boolean movable;
    private Shape shape;
    protected Direction movingDirection;
    protected double movementSpeed;
    protected Vector recoil;
    protected CollisionHandler collisionHandler;
    protected Vector oldCoords;
    protected int hp;

    public ArenaObject(int x, int y, double width, double height, double movementSpeed, ShapeEnum shapeEnum, boolean movable,
		       Image image, CollisionHandler collisionHandler)
    {
	super(x, y, width, height, image);
	hp = 50;
	this.movingDirection = Direction.NONE;
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

    public void weaponCollision(Weapon weapon){
	double temp = (double)weapon.getDamage()/10;
	switch (weapon.getHittingDirection()) {
	    case NORTH:
		addRecoil(new Vector(0, -temp));
		break;
	    case NORTHEAST:
		addRecoil(new Vector(temp, -temp));
		break;
	    case EAST:
		addRecoil(new Vector(temp, 0));
		break;
	    case SOUTHEAST:
		addRecoil(new Vector(temp, temp));
		break;
	    case SOUTH:
		addRecoil(new Vector(0, temp));
		break;
	    case SOUTHWEST:
		addRecoil(new Vector(-temp, temp));
		break;
	    case WEST:
		addRecoil(new Vector(-temp, 0));
		break;
	    case NORTHWEST:
		addRecoil(new Vector(-temp, -temp));
		break;
	}
    }

    public void addRecoil(Vector v) {
	recoil.add(v);
    }

    public void applyRecoil() {
	x += recoil.getX();
	y += recoil.getY();
	reduceRecoil();
    }

    private void reduceRecoil() {
	recoil.scale(0.8);
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

    public Vector getOldCoords() {
	return oldCoords;
    }

    public void updateDirection(){
	double dX = x - oldCoords.getX();
	double dY = y - oldCoords.getY();

	double angle = dY / dX;
	double temp = 0.5;

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

    @Override public void update(){
	oldCoords.set(x, y);
	move();
	updateDirection();
	updateImage();
	applyRecoil();
    }
}

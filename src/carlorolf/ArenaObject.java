package carlorolf;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ArenaObject extends VisibleObject
{
    private boolean movable;
    private Shape shape;
    protected Direction movingDirection;
    private double movementSpeed;
    protected Vector recoil;
    protected CollisionHandler collisionHandler;
    protected Vector oldCoords;
    protected int hp;
    protected Arena arena;
    protected List<VisibleObject> layers;

    public ArenaObject(double x, double y, double width, double height, double movementSpeed, int hp, ShapeEnum shapeEnum, boolean movable,
		       Image image, CollisionHandler collisionHandler, Arena arena)
    {
	super(x, y, width, height, image);
	this.arena = arena;
	this.hp = hp;
	this.movingDirection = Direction.NONE;
	this.collisionHandler = collisionHandler;
	this.movable = movable;
	this.movementSpeed = movementSpeed;
	recoil = new Vector(0, 0);
	if (shapeEnum == ShapeEnum.RECTANGLE) this.shape = new Shape(width, height);
	else if (shapeEnum == ShapeEnum.CIRCLE) this.shape = new Shape(width / 2);
	oldCoords = new Vector(x, y);
	layers = new ArrayList<VisibleObject>();
    }

    public Shape getShape() {
	return shape;
    }

    public boolean isMovable() {
	return movable;
    }

    public abstract void Collision(CollisionEvent e);

    public void weaponCollision(Weapon weapon){
	if (isMovable()) {
	    double temp = weapon.getDamage();
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
	if (this.hp > 0)
	    this.hp -= weapon.getDamage();
    }

    public void addRecoil(Vector v) {
	recoil.add(v);
    }

    public void applyRecoil() {
	x += recoil.getX() * DeltaTime.getdT();
	y += recoil.getY() * DeltaTime.getdT();
	reduceRecoil();
    }

    private void reduceRecoil() {
	recoil.scale(0.9);
    }

    protected abstract void move(double movementSpeed);

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

    public void death(){
	arena.addLayer(new VisibleObject(x, y, width*2, height*2, Images.getImage("blood_superlowopacity.png"))
	{
	    @Override public void update() {

	    }
	});
	collisionHandler.removeObject(this);
	arena.removeObject(this);
    }

    @Override public void update(){
	if (hp <= 0){
	    death();
	}
	oldCoords.set(x, y);
	move(movementSpeed*DeltaTime.getdT());
	for (VisibleObject layer : layers) {
	    layer.update();
	}
	updateDirection();
	updateImage();
	applyRecoil();
    }

    @Override public void draw(Graphics screen, Dimension tileSize){
	super.draw(screen, tileSize);
	for (VisibleObject layer : layers) {
	    layer.draw(screen, tileSize);
	}

	// Drawing healthbar
	
    }
}

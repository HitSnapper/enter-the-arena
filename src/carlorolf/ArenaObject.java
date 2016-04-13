package carlorolf;

import java.awt.Image;

public abstract class ArenaObject extends VisibleObject
{
    private boolean movable;
    private Shape shape;
    public ArenaObject(int x, int y, double width, double height, ShapeEnum shapeEnum, boolean movable, Image image) {
	super(x, y, width, height, image);
	this.movable = movable;
	if (shapeEnum == ShapeEnum.RECTANGLE)
	    this.shape = new Shape(width, height);
	else if (shapeEnum == ShapeEnum.CIRCLE)
	    this.shape = new Shape(width/2);
    }

    public Shape getShape(){
	return shape;
    }

    public boolean isMovable(){
	return movable;
    }

    public void update(){

    }

    public abstract void Collision(CollisionEvent e);

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ArenaObject)) {
            return false;
        }

        ArenaObject that = (ArenaObject) other;

        // Custom equality check here.
        return this.getX() == that.getX() && this.getY() == that.getY() &&
	       this.getWidth() == that.getWidth() && this.getHeight() == that.getHeight() &&
	       this.getShape() == that.getShape();
    }
}

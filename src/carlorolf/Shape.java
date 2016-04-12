package carlorolf;

public class Shape
{
    ShapeEnum shapeEnum;
    double width, height, radius;

    /**
     * Creates a rectangle shape.
     * @param width
     * @param height
     */
    public Shape(double width, double height) {
	shapeEnum = ShapeEnum.RECTANGLE;
	this.width = width;
	this.height = height;
    }

    /**
     * Creates a circle shape.
     * @param radius
     */
    public Shape(double radius){
	shapeEnum = ShapeEnum.CIRCLE;
	this.radius = radius;
    }
}

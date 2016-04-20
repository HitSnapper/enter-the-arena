package carlorolf;

public class Vector {
    private double x, y;

    public Vector(final double x, final double y){
        this.x = x;
        this.y = y;
    }

    public Vector multiplication(Vector v){
        return new Vector(x*v.getX(), y*v.getY());
    }

    public Vector addition(Vector v){
        return new Vector(x + v.getX(), y + v.getY());
    }

    public void add(Vector v){
        x += v.getX();
        y += v.getY();
    }

    public void add(double x, double y){
            this.x += x;
            this.y += y;
    }

    public void scale(double d){
        x *= d;
        y *= d;
    }

    public double getAbs(){
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void set(double x, double y){
        this.x = x;
        this.y = y;
    }
}

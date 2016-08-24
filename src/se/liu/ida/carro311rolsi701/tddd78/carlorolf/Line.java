package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

/**
 * Created by Linnea Sievert on 2016-08-24.
 */
public class Line {
    private Vector startCoord;
    private Vector endCoord;

    public Line(Vector startCoord, Vector endCoord){
        this.startCoord = startCoord;
        this.endCoord = endCoord;
    }

    public Line(Node startCoord, Node endCoord){
        this.startCoord = startCoord.getCoords();
        this.endCoord = endCoord.getCoords();
    }

    public Line(double startX, double startY, double endX, double endY){
        startCoord = new Vector(startX, startY);
        endCoord = new Vector(endX, endY);
    }

    public Vector getStartCoord() {
        return startCoord;
    }

    public Vector getEndCoord() {
        return endCoord;
    }

    public double getStartX(){
        return startCoord.getX();
    }

    public double getStartY(){
        return startCoord.getY();
    }

    public double getEndX(){
        return endCoord.getX();
    }

    public double getEndY(){
        return endCoord.getY();
    }

    public boolean intersectsLine(Line line){
        double s1_x, s1_y, s2_x, s2_y;
        s1_x = endCoord.getX() - startCoord.getX();     s1_y = endCoord.getY() - startCoord.getY();
        s2_x = line.getEndX() - line.getStartX();     s2_y = line.getEndY() - line.getStartY();

        double s, t;
        s = (-s1_y * (startCoord.getX() - line.getStartX()) + s1_x * (startCoord.getY() - line.getStartX())) / (-s2_x * s1_y + s1_x * s2_y);
        t = ( s2_x * (startCoord.getY() - line.getStartY()) - s2_y * (startCoord.getX() - line.getStartX())) / (-s2_x * s1_y + s1_x * s2_y);

        if (s > 0 && s < 1 && t > 0 && t < 1)
        {
            // Collision detected
            return true;
        }

        return false; // No collision
    }
}

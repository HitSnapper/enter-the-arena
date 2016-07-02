package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HitSnapper on 2016-06-28.
 */
public class ShapeMaker {
    public static Shape getHexagon(double radius) {
        return getPolygon(6, radius);
    }

    public static Shape getOctagon(double radius){
        return getPolygon(8, radius);
    }

    public static Shape getSquare(double radius) {
        return getPolygon(4, radius);
    }
    public static Shape getRectangle(double width, double height) {
        List<Vector> nodes = new ArrayList<>();
        nodes.add(new Vector(-width/2, -height/2));
        nodes.add(new Vector(-width/2, height/2));
        nodes.add(new Vector(width/2, height/2));
        nodes.add(new Vector(width/2, -height/2));
        return new Shape(nodes);
    }

    public static Shape getPolygon(int sides, double radius){
        List<Vector> res = new ArrayList<>();
        for(int n = 0; n < sides; n++){
            double angle = 2*Math.PI*n/sides + Math.PI/4;
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);
            res.add(new Vector(x, y));
        }
        return new Shape(res);
    }
}

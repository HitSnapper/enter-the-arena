package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HitSnapper on 2016-06-28.
 */
public class ShapeMaker {
    public static Shape getHexagon(double width) {
        List<Vector> nodes = new ArrayList<>();
        double d = width / (Math.pow(3, 1 / 2) * 2);
        for (int n = -1; n <= 1; n += 2) {
            for (int k = -1; k <= 1; k += 2) {
                nodes.add(new Vector(n * d, k * width / 2));
            }
        }
        nodes.add(new Vector(-2 * d, 0));
        nodes.add(new Vector(2 * d, 0));
        return new Shape(nodes);
    }

    public static Shape getSquare(double width) {
        List<Vector> nodes = new ArrayList<>();
        for (int n = -1; n <= 1; n += 2) {
            for (int k = -1; k <= 1; k += 2) {
                nodes.add(new Vector(n * width / 2, k * width / 2));
            }
        }
        return new Shape(nodes);
    }
    public static Shape getRectangle(double width, double height) {
        List<Vector> nodes = new ArrayList<>();
        for (int n = -1; n <= 1; n += 2) {
            for (int k = -1; k <= 1; k += 2) {
                nodes.add(new Vector(n * width / 2, k * height / 2));
            }
        }
        return new Shape(nodes);
    }
}

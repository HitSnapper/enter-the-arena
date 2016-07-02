package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import se.liu.ida.carro311rolsi701.tddd78.carlorolf.Arena;
import se.liu.ida.carro311rolsi701.tddd78.carlorolf.VisibleObject;

import java.awt.*;

/**
 * Created by HitSnapper on 2016-05-02.
 */
public class Layer extends VisibleObject{

    public Layer(double x, double y, double width, double height, Image image, Arena arena) {
        super(new Vector(x, y), width, height, image, arena);
    }

    @Override
    public void update(double deltaTime) {

    }
}

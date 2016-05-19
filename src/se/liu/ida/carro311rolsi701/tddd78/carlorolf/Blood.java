package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

/**
 * Created by HitSnapper on 2016-05-19.
 */
public class Blood extends Layer {
    private double lifeLength;
    private double age;

    public Blood(double x, double y, double width, double height, Arena arena) {
        super(x, y, width, height, Images.getImage("blood"), arena);
        lifeLength = height*width;
    }

    @Override public void update(double deltaTime){
        age += deltaTime;
        if (age > lifeLength){
            arena.removeBackgroundLayer(this);
        }
    }
}

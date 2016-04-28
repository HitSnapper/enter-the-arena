package carlorolf;

//ta bort?



public class Grass extends VisibleObject {

    public Grass(double x, double y, Arena arena) {
        super(x, y, 1, 1, Images.getImage("grass0.png"), arena);
    }

    @Override
    public void update(double deltaTime) {
    }
}

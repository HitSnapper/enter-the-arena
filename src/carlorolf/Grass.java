package carlorolf;

public class Grass extends VisibleObject {

    public Grass(double x, double y) {
        super(x, y, 1, 1, Images.getImage("grass0.png"));
    }

    @Override
    public void update() {
        /*
        if (random.nextInt(1000) == 42){
            image = Images.getImage("grass" + Integer.toString(random.nextInt(5)) + ".png");
        }
	*/
    }
}

package carlorolf;

import java.util.Random;

public class Grass extends VisibleObject
{
    private Random random;
    public Grass(int x,int y){
	super(x, y, 1, 1, Images.getImage("grass0.png"));
	random = new Random();
    }

    @Override public void update() {
	if (random.nextInt(1000) == 42){
	    image = Images.getImage("grass" + Integer.toString(random.nextInt(5)) + ".png");
	}
    }
}

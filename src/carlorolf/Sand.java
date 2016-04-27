package carlorolf;

public class Sand extends VisibleObject {
    public Sand(final double x, final double y, final Arena arena) {
        super(x, y, 1, 1, Images.getImage("sand.png"), arena);
    }

    @Override
    public void update(final double deltaTime) {

    }
}

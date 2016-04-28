package carlorolf;

import java.awt.*;

public class StandardEnemy extends Enemy {

    public StandardEnemy(final double x, final double y, final CollisionHandler collisionHandler, final Arena arena) {
        super(x, y, 1, 1, 3, 50, 1, Images.getImage("enemy_none.png"), collisionHandler, arena);
    }

    @Override
    protected void updateImage() {
        image = Images.getImage("enemy_" + Direction.toString(movingDirection) + ".png");
    }

}

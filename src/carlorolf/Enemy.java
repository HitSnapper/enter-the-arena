package carlorolf;

public class Enemy extends ArenaObject
{
    Player player;

    public Enemy(final double x, final double y, Player player, CollisionHandler collisionHandler, Arena arena)
    {
	super(x, y, 1, 1, 3, 100, ShapeEnum.RECTANGLE, true, Images.getImage("enemy_none.png"), collisionHandler, arena);
	this.player = player;
	movingDirection = Direction.NONE;
    }

    @Override protected void move(double movementSpeed) {
	double pX = player.getX() - x;
	double pY = player.getY() - y;
	double absP = Math.sqrt(Math.pow(pX, 2) + Math.pow(pY, 2));
	double konstant = absP / movementSpeed;
	double dX = pX / konstant;
	double dY = pY / konstant;
	x += dX;
	y += dY;
    }

    @Override protected void updateImage() {
	image = Images.getImage("enemy_" + Direction.toString(movingDirection) + ".png");
    }

    @Override public void update() {
	super.update();
    }

    @Override public void Collision(final CollisionEvent e) {
    }
}

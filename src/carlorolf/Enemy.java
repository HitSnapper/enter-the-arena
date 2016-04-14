package carlorolf;

public class Enemy extends ArenaObject
{
    Player player;
    Direction facingDirection;

    public Enemy(final int x, final int y, Player player)
    {
	super(x, y, 1, 1, 0.04, ShapeEnum.RECTANGLE, true, Images.getImage("enemy.png"));
	this.player = player;
	facingDirection = Direction.NONE;
    }

    public void move(){
	double pX = player.getX() - x;
	double pY = player.getY() - y;
	double absP = Math.sqrt(Math.pow(pX, 2) + Math.pow(pY, 2));
	double konstant = absP/movementSpeed;
	double dX = pX/konstant;
	double dY = pY/konstant;
	x += dX;
	y += dY;

	double angle = dY/dX;
	double bla = 0.5;

	// Calculating what direction the enemy is looking in
	if (Math.abs(dY/dX) < bla || Math.abs(dX/dY) < bla) {
	    if (Math.abs(dX) > Math.abs(dY) && dX > 0) {
		facingDirection = Direction.EAST;
	    } else if (Math.abs(dX) < Math.abs(dY) && dY > 0) {
		facingDirection = Direction.SOUTH;
	    } else if (Math.abs(dX) > Math.abs(dY) && dX < 0) {
		facingDirection = Direction.WEST;
	    } else if (Math.abs(dX) < Math.abs(dY) && dY < 0) {
		facingDirection = Direction.NORTH;
	    }
	}
	else {
	    if (angle > 0 && dX > 0) {
		facingDirection = Direction.SOUTHEAST;
	    } else if (angle < 0 && dX > 0) {
		facingDirection = Direction.NORTHEAST;
	    } else if (angle > 0 && dX < 0) {
		facingDirection = Direction.NORTHWEST;
	    } else if (angle < 0 && dX < 0) {
		facingDirection = Direction.SOUTHWEST;
	    }
	}
    }

    private void updateImage(){
	switch(facingDirection){
	    case NORTH:
		image = Images.getImage("enemy_up.png");
		break;
	    case EAST:
		image = Images.getImage("enemy_right.png");
		break;
	    case SOUTH:
		image = Images.getImage("enemy_down.png");
		break;
	    case WEST:
		image = Images.getImage("enemy_left.png");
		break;
	    case NORTHEAST:
		image = Images.getImage("enemy_northeast.png");
		break;
	    case NORTHWEST:
		image = Images.getImage("enemy_northwest.png");
		break;
	    case SOUTHEAST:
		image = Images.getImage("enemy_southeast.png");
		break;
	    case SOUTHWEST:
		image = Images.getImage("enemy_southwest.png");
		break;
	    default:
		if (image != Images.getImage("enemy.png"))
		    image = Images.getImage("enemy.png");
	}
    }

    @Override public void update() {
	move();
	updateImage();
    }

    @Override public void Collision(final CollisionEvent e) {

    }
}

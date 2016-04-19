package carlorolf;

public class Player extends ArenaObject
{
    private Direction movementDirection;

    public Player(final int x, final int y, CollisionHandler collisionHandler) {
	super(x, y, 1, 1, 0.1, ShapeEnum.RECTANGLE, true, Images.getImage("object.png"), collisionHandler);
	movementDirection = Direction.NONE;
    }

    public void movePlayer(Direction direction) {
	if (movementDirection == movementDirection.NONE || movementDirection == direction) movementDirection = direction;
	else {
	    switch (movementDirection) {
		case WEST:
		    if (direction == Direction.NORTH) movementDirection = Direction.NORTHWEST;
		    else if (direction == Direction.SOUTH) movementDirection = Direction.SOUTHWEST;
		    else movementDirection = direction;
		    break;
		case EAST:
		    if (direction == Direction.NORTH) movementDirection = Direction.NORTHEAST;
		    else if (direction == Direction.SOUTH) movementDirection = Direction.SOUTHEAST;
		    else movementDirection = direction;
		    break;
		case NORTH:
		    if (direction == Direction.EAST) movementDirection = Direction.NORTHEAST;
		    else if (direction == Direction.WEST) movementDirection = Direction.NORTHWEST;
		    else movementDirection = direction;
		    break;
		case SOUTH:
		    if (direction == Direction.WEST) movementDirection = Direction.SOUTHWEST;
		    else if (direction == Direction.EAST) movementDirection = Direction.SOUTHEAST;
		    else movementDirection = direction;
		    break;
		default:
		    break;
	    }
	}
    }

    public void stopMovingInDirection(Direction dir) {
	switch (movementDirection) {
	    case NORTHEAST:
		if (dir == Direction.EAST) movementDirection = Direction.NORTH;
		else if (dir == Direction.NORTH) movementDirection = Direction.EAST;
		break;
	    case NORTHWEST:
		if (dir == Direction.WEST) movementDirection = Direction.NORTH;
		else if (dir == Direction.NORTH) movementDirection = Direction.WEST;
		break;
	    case SOUTHEAST:
		if (dir == Direction.EAST) movementDirection = Direction.SOUTH;
		else if (dir == Direction.SOUTH) movementDirection = Direction.EAST;
		break;
	    case SOUTHWEST:
		if (dir == Direction.SOUTH) movementDirection = Direction.WEST;
		else if (dir == Direction.WEST) movementDirection = Direction.SOUTH;
		break;
	    default:
		if (dir == movementDirection) movementDirection = Direction.NONE;
	}
    }

    @Override protected void move() {
	switch (movementDirection) {
	    case NORTH:
		this.y -= movementSpeed;
		break;
	    case SOUTH:
		this.y += movementSpeed;
		break;
	    case WEST:
		this.x -= movementSpeed;
		break;
	    case EAST:
		this.x += movementSpeed;
		break;
	    case NORTHEAST:
		this.x += Math.sqrt(Math.pow(movementSpeed, 2) / 2);
		this.y -= Math.sqrt(Math.pow(movementSpeed, 2) / 2);
		break;
	    case NORTHWEST:
		this.x -= Math.sqrt(Math.pow(movementSpeed, 2) / 2);
		this.y -= Math.sqrt(Math.pow(movementSpeed, 2) / 2);
		break;
	    case SOUTHEAST:
		this.x += Math.sqrt(Math.pow(movementSpeed, 2) / 2);
		this.y += Math.sqrt(Math.pow(movementSpeed, 2) / 2);
		break;
	    case SOUTHWEST:
		this.x -= Math.sqrt(Math.pow(movementSpeed, 2) / 2);
		this.y += Math.sqrt(Math.pow(movementSpeed, 2) / 2);
		break;
	    default:
		break;
	}
    }

    @Override protected void updateImage() {
	switch (movementDirection) {
	    case NORTH:
		image = Images.getImage("object_up.png");
		break;
	    case EAST:
		image = Images.getImage("object_right.png");
		break;
	    case SOUTH:
		image = Images.getImage("object_down.png");
		break;
	    case WEST:
		image = Images.getImage("object_left.png");
		break;
	    case NORTHEAST:
		image = Images.getImage("object_northeast.png");
		break;
	    case NORTHWEST:
		image = Images.getImage("object_northwest.png");
		break;
	    case SOUTHEAST:
		image = Images.getImage("object_southeast.png");
		break;
	    case SOUTHWEST:
		image = Images.getImage("object_southwest.png");
		break;
	    default:
		if (image != Images.getImage("object.png")) image = Images.getImage("object.png");
	}
    }

    @Override public void update() {
	super.update();
    }

    @Override public void Collision(final CollisionEvent e) {
    }

    @Override public void weaponCollision(final Weapon weapon) {

    }

    public void hit(){
	if (movementDirection != Direction.NONE) {
	    double range = 2 * width / 3;
	    int damage = 5;
	    double w_x = 0;
	    double w_y = 0;
	    switch (movementDirection) {
		case NORTH:
		    w_y = y - range;
		    w_x = x + width / 2 - range / 2;
		    break;
		case NORTHEAST:
		    w_y = y - range;
		    w_x = x + width;
		    break;
		case EAST:
		    w_y = y + height / 2 - range / 2;
		    w_x = x + width;
		    break;
		case SOUTHEAST:
		    w_y = y + height;
		    w_x = x + width;
		    break;
		case SOUTH:
		    w_y = y + height;
		    w_x = x + width / 2 - range / 2;
		    break;
		case SOUTHWEST:
		    w_y = y + height;
		    w_x = x - range;
		    break;
		case WEST:
		    w_y = y + height / 2 - range / 2;
		    w_x = x - range;
		    break;
		case NORTHWEST:
		    w_y = y - range;
		    w_x = x - range;
		    break;
	    }
	    collisionHandler.addWeapon(new Weapon(movementDirection, w_x, w_y, damage, range));
	}
    }
}

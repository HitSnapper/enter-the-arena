package carlorolf;

public class Player extends ArenaObject
{
    private double weaponRange;
    private int weaponDamage;
    public Player(final double x, final double y, CollisionHandler collisionHandler, Arena arena) {
	super(x, y, 1.3, 1.3, 5, 100, ShapeEnum.RECTANGLE, true, Images.getImage("object_none.png"), collisionHandler, arena);
	weaponRange = 4 * width / 5;
	weaponDamage = 20;
	armor = 100;
	maximumArmor = 100;
    }

    public void movePlayer(Direction direction) {
	if (movingDirection == movingDirection.NONE || movingDirection == direction) movingDirection = direction;
	else {
	    switch (movingDirection) {
		case WEST:
		    if (direction == Direction.NORTH) movingDirection = Direction.NORTHWEST;
		    else if (direction == Direction.SOUTH) movingDirection = Direction.SOUTHWEST;
		    else movingDirection = direction;
		    break;
		case EAST:
		    if (direction == Direction.NORTH) movingDirection = Direction.NORTHEAST;
		    else if (direction == Direction.SOUTH) movingDirection = Direction.SOUTHEAST;
		    else movingDirection = direction;
		    break;
		case NORTH:
		    if (direction == Direction.EAST) movingDirection = Direction.NORTHEAST;
		    else if (direction == Direction.WEST) movingDirection = Direction.NORTHWEST;
		    else movingDirection = direction;
		    break;
		case SOUTH:
		    if (direction == Direction.WEST) movingDirection = Direction.SOUTHWEST;
		    else if (direction == Direction.EAST) movingDirection = Direction.SOUTHEAST;
		    else movingDirection = direction;
		    break;
		default:
		    break;
	    }
	}
    }

    public void stopMovingInDirection(Direction dir) {
	switch (movingDirection) {
	    case NORTHEAST:
		if (dir == Direction.EAST) movingDirection = Direction.NORTH;
		else if (dir == Direction.NORTH) movingDirection = Direction.EAST;
		break;
	    case NORTHWEST:
		if (dir == Direction.WEST) movingDirection = Direction.NORTH;
		else if (dir == Direction.NORTH) movingDirection = Direction.WEST;
		break;
	    case SOUTHEAST:
		if (dir == Direction.EAST) movingDirection = Direction.SOUTH;
		else if (dir == Direction.SOUTH) movingDirection = Direction.EAST;
		break;
	    case SOUTHWEST:
		if (dir == Direction.SOUTH) movingDirection = Direction.WEST;
		else if (dir == Direction.WEST) movingDirection = Direction.SOUTH;
		break;
	    default:
		if (dir == movingDirection) movingDirection = Direction.NONE;
	}
    }

    @Override protected void move(double movementSpeed) {

	switch (movingDirection) {
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
	image = Images.getImage("object_" + Direction.toString(movingDirection) + ".png");
    }

    @Override public void update() {
	super.update();
    }

    @Override public void Collision(final CollisionEvent e) {
    }

    public void hit() {
	if (movingDirection != Direction.NONE) {
	    double w_x = 0;
	    double w_y = 0;
	    switch (movingDirection) {
		case NORTH:
		    w_y = y - height / 2 - weaponRange / 2;
		    w_x = x;
		    break;
		case NORTHEAST:
		    w_y = y - (Math.sqrt(Math.pow(weaponRange, 2) / 2) + height / 3) / 2;
		    w_x = x + (Math.sqrt(Math.pow(weaponRange, 2) / 2) + width / 3) / 2;
		    break;
		case EAST:
		    w_y = y;
		    w_x = x + width / 2 + weaponRange / 2;
		    break;
		case SOUTHEAST:
		    w_y = y + (Math.sqrt(Math.pow(weaponRange, 2) / 2) + height / 3) / 2;
		    w_x = x + (Math.sqrt(Math.pow(weaponRange, 2) / 2) + width / 3) / 2;
		    break;
		case SOUTH:
		    w_y = y + height / 2 + weaponRange / 2;
		    w_x = x;
		    break;
		case SOUTHWEST:
		    w_y = y + (Math.sqrt(Math.pow(weaponRange, 2) / 2) + height / 3) / 2;
		    w_x = x - (Math.sqrt(Math.pow(weaponRange, 2) / 2) + width / 3) / 2;
		    break;
		case WEST:
		    w_y = y;
		    w_x = x - width / 2 - weaponRange / 2;
		    break;
		case NORTHWEST:
		    w_y = y - (Math.sqrt(Math.pow(weaponRange, 2) / 2) + height / 3) / 2;
		    w_x = x - (Math.sqrt(Math.pow(weaponRange, 2) / 2) + width / 3) / 2;
		    break;
	    }
	    collisionHandler.addWeapon(new Weapon(movingDirection, w_x, w_y, weaponDamage, weaponRange, this));
	}
    }
}

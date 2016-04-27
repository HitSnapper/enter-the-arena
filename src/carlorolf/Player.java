package carlorolf;

public class Player extends Character {

    public Player(final double x, final double y, CollisionHandler collisionHandler, Arena arena) {
        super(x, y, 1, 1, 5, 100, ShapeEnum.RECTANGLE, true, Images.getImage("object_none.png"), collisionHandler, arena);
	weapon = new Weapon(x, y, 20, 4 * width / 5, 0.5, this);
	armor = new Armor(100, this, arena, Images.getImage("helmet.png"));
        this.attackSpeed = 0.8;
    }

    public void movePlayer(Direction direction) {
        if (movingDirection == Direction.NONE || movingDirection == direction) movingDirection = direction;
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

    @Override
    protected void move(double movementSpeed) {
        System.out.println(coords);
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

    @Override
    protected void updateImage() {
        image = Images.getImage("object_" + Direction.toString(movingDirection) + ".png");
    }

    @Override
    public void collision(final CollisionEvent e) {
    }

    public void addHealth(final int hp){
        this.hp += hp;
        if (this.hp > maximumHp)
            this.hp = maximumHp;
    }

    public void hit() {
        if (movingDirection != Direction.NONE && canAttack) {
            double wX = 0;
            double wY = 0;
	    double weaponRange = weapon.getRange();
            switch (movingDirection) {
                case NORTH:
                    wY = y - height / 2 - weaponRange / 2;
                    wX = x;
                    break;
                case NORTHEAST:
                    wY = y - (Math.sqrt(Math.pow(weaponRange, 2) / 2) + height / 3) / 2;
                    wX = x + (Math.sqrt(Math.pow(weaponRange, 2) / 2) + width / 3) / 2;
                    break;
                case EAST:
                    wY = y;
                    wX = x + width / 2 + weaponRange / 2;
                    break;
                case SOUTHEAST:
                    wY = y + (Math.sqrt(Math.pow(weaponRange, 2) / 2) + height / 3) / 2;
                    wX = x + (Math.sqrt(Math.pow(weaponRange, 2) / 2) + width / 3) / 2;
                    break;
                case SOUTH:
                    wY = y + height / 2 + weaponRange / 2;
                    wX = x;
                    break;
                case SOUTHWEST:
                    wY = y + (Math.sqrt(Math.pow(weaponRange, 2) / 2) + height / 3) / 2;
                    wX = x - (Math.sqrt(Math.pow(weaponRange, 2) / 2) + width / 3) / 2;
                    break;
                case WEST:
                    wY = y;
                    wX = x - width / 2 - weaponRange / 2;
                    break;
                case NORTHWEST:
                    wY = y - (Math.sqrt(Math.pow(weaponRange, 2) / 2) + height / 3) / 2;
                    wX = x - (Math.sqrt(Math.pow(weaponRange, 2) / 2) + width / 3) / 2;
                    break;
            }
	    weapon.setHittingDirection(movingDirection, wX, wY);
            collisionHandler.addWeapon(weapon);
        }
    }
}

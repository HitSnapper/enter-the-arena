package carlorolf.friendlycharacters;

import carlorolf.Arena;
import carlorolf.Armor;
import carlorolf.Character;
import carlorolf.CollisionHandler;
import carlorolf.Direction;
import carlorolf.Images;
import carlorolf.Weapon;

/**
 * The player that you play as in the game.
 */
public class Player extends Character
{

    //These are static so it can be accessed in the super constructor
    final static int WIDTH = 20;
    final static double ATTACKSPEED = 0.5;
    static final double PLAYERATTACKSPEED = 0.8;

    public Player(final double x, final double y, CollisionHandler collisionHandler, Arena arena) {
        super(x, y, 1, 1, 5, 100, 1, true, "object", collisionHandler, arena);
        //A unique weapon for Player
        //noinspection AssignmentToSuperclassField
        weapon = new Weapon(x, y, WIDTH, 4 * width / 5, ATTACKSPEED, this);
        //A unique armor for player
        //noinspection AssignmentToSuperclassField
        armor = new Armor(100, this, arena, Images.getImage("helmet"));
        //Increasing players attack speed compared to standard Character
        //noinspection AssignmentToSuperclassField
        this.attackSpeed = PLAYERATTACKSPEED;
    }

    public void movePlayer(Direction direction) {
        if (movingDirection == Direction.NONE || movingDirection == direction) movingDirection = direction;
        else {
            if (movingDirection == Direction.WEST) {
                if (direction == Direction.NORTH) movingDirection = Direction.NORTHWEST;
                else if (direction == Direction.SOUTH) movingDirection = Direction.SOUTHWEST;
                else movingDirection = direction;

            } else if (movingDirection == Direction.EAST) {
                if (direction == Direction.NORTH) movingDirection = Direction.NORTHEAST;
                else if (direction == Direction.SOUTH) movingDirection = Direction.SOUTHEAST;
                else movingDirection = direction;

            } else if (movingDirection == Direction.NORTH) {
                if (direction == Direction.EAST) movingDirection = Direction.NORTHEAST;
                else if (direction == Direction.WEST) movingDirection = Direction.NORTHWEST;
                else movingDirection = direction;

            } else if (movingDirection == Direction.SOUTH) {
                if (direction == Direction.WEST) movingDirection = Direction.SOUTHWEST;
                else if (direction == Direction.EAST) movingDirection = Direction.SOUTHEAST;
                else movingDirection = direction;

            }
        }
    }

    public void stopMovingInDirection(Direction dir) {
        if (movingDirection == Direction.NORTHEAST) {
            if (dir == Direction.EAST) movingDirection = Direction.NORTH;
            else if (dir == Direction.NORTH) movingDirection = Direction.EAST;

        } else if (movingDirection == Direction.NORTHWEST) {
            if (dir == Direction.WEST) movingDirection = Direction.NORTH;
            else if (dir == Direction.NORTH) movingDirection = Direction.WEST;

        } else if (movingDirection == Direction.SOUTHEAST) {
            if (dir == Direction.EAST) movingDirection = Direction.SOUTH;
            else if (dir == Direction.SOUTH) movingDirection = Direction.EAST;

        } else if (movingDirection == Direction.SOUTHWEST) {
            if (dir == Direction.SOUTH) movingDirection = Direction.WEST;
            else if (dir == Direction.WEST) movingDirection = Direction.SOUTH;

        } else {
            if (dir == movingDirection) movingDirection = Direction.NONE;
        }
    }

    @Override
    protected void move(double movementSpeed) {
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
            case NONE:
                break;
            default:
                break;
        }
    }

    public void addHealth(final int hp) {
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
                case NONE:
                    break;
            }
            weapon.setHittingDirection(movingDirection, wX, wY);
            collisionHandler.addWeapon(weapon);
        }
    }
}

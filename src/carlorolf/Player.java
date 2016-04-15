package carlorolf;

public class Player extends ArenaObject
{
    private Direction movementDirection;

    public Player(final int x, final int y) {
	super(x, y, 1, 1, 0.1, ShapeEnum.RECTANGLE, true, Images.getImage("object.png"));
	movementDirection = Direction.NONE;
    }

    public void movePlayer(Direction direction){
	if (movementDirection == movementDirection.NONE || movementDirection == direction)
		movementDirection = direction;
	else{
	    switch (movementDirection){
		case WEST:
		    if (direction == Direction.NORTH)
			movementDirection = Direction.NORTHWEST;
		    else if (direction == Direction.SOUTH)
			movementDirection = Direction.SOUTHWEST;
		    else
		    	movementDirection = direction;
		    break;
		case EAST:
		    if (direction == Direction.NORTH)
			movementDirection = Direction.NORTHEAST;
		    else if(direction == Direction.SOUTH)
			movementDirection = Direction.SOUTHEAST;
		    else
		    	movementDirection = direction;
		    break;
		case NORTH:
		    if(direction == Direction.EAST)
			movementDirection = Direction.NORTHEAST;
		    else if(direction == Direction.WEST)
			movementDirection = Direction.NORTHWEST;
		    else
		    	movementDirection = direction;
		    break;
		case SOUTH:
		    if(direction == Direction.WEST)
			movementDirection = Direction.SOUTHWEST;
		    else if(direction == Direction.EAST)
			movementDirection = Direction.SOUTHEAST;
		    else
			movementDirection = direction;
		    break;
		default:
		    break;
	    }
	}
    }

    public void stopMovingInDirection(Direction dir){
	switch(movementDirection){
	    case NORTHEAST:
		if (dir == Direction.EAST)
		    movementDirection = Direction.NORTH;
		else if (dir == Direction.NORTH)
		    movementDirection = Direction.EAST;
		break;
	    case NORTHWEST:
		if (dir == Direction.WEST)
		    movementDirection = Direction.NORTH;
		else if (dir == Direction.NORTH)
		    movementDirection = Direction.WEST;
		break;
	    case SOUTHEAST:
		if (dir == Direction.EAST)
		    movementDirection = Direction.SOUTH;
		else if (dir == Direction.SOUTH)
		    movementDirection = Direction.EAST;
		break;
	    case SOUTHWEST:
		if (dir == Direction.SOUTH)
		    movementDirection = Direction.WEST;
		else if (dir == Direction.WEST)
		    movementDirection = Direction.SOUTH;
		break;
	    default:
		if (dir == movementDirection)
		    movementDirection = Direction.NONE;
	}
    }

    private void move(){
	switch(movementDirection){
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
		this.x += Math.sqrt(Math.pow(movementSpeed, 2)/2);
		this.y -= Math.sqrt(Math.pow(movementSpeed, 2)/2);
		break;
	    case NORTHWEST:
		this.x -= Math.sqrt(Math.pow(movementSpeed, 2)/2);
		this.y -= Math.sqrt(Math.pow(movementSpeed, 2)/2);
		break;
	    case SOUTHEAST:
		this.x += Math.sqrt(Math.pow(movementSpeed, 2)/2);
		this.y += Math.sqrt(Math.pow(movementSpeed, 2)/2);
		break;
	    case SOUTHWEST:
		this.x -= Math.sqrt(Math.pow(movementSpeed, 2)/2);
		this.y += Math.sqrt(Math.pow(movementSpeed, 2)/2);
		break;
	    default:
		break;
	}
    }

	private void updateImageRotation(){
		switch(movementDirection){
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
				if (image != Images.getImage("object.png"))
					image = Images.getImage("object.png");
		}
	}

    @Override public void update(){
		move();
		updateImageRotation();
		handleRecoil();
    }

    @Override public void Collision(final CollisionEvent e) {
        if (e.getObject() instanceof Enemy){
            double moveConst = 0.4;
            switch(e.getDirection()){
                case NORTH:
                    addRecoil(new Vector(0, -moveConst));
                    break;
                case NORTHEAST:
                    addRecoil(new Vector(moveConst, -moveConst));
                    break;
                case EAST:
                    addRecoil(new Vector(moveConst, 0));
                    break;
                case SOUTHEAST:
                    addRecoil(new Vector(moveConst, moveConst));
                    break;
                case SOUTH:
                    addRecoil(new Vector(0, moveConst));
                    break;
                case SOUTHWEST:
                    addRecoil(new Vector(-moveConst, moveConst));
                    break;
                case WEST:
                    addRecoil(new Vector(-moveConst, 0));
                    break;
                case NORTHWEST:
                    addRecoil(new Vector(-moveConst, -moveConst));
                    break;
            }
        }
    }
}

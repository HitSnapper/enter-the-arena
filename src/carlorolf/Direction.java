package carlorolf;

/**
 * Direction describes a direction. Has method for getting a vector in the equivalent direction and a toString method.
 */
public enum Direction {
    NORTH(new Vector(0, -1)), SOUTH(new Vector(0, 1)), EAST(new Vector(1, 0)), WEST(new Vector(-1, 0)),
    NORTHWEST(new Vector(-1, -1)), NORTHEAST(new Vector(1, -1)), SOUTHWEST(new Vector(-1, 1)), SOUTHEAST(new Vector(1, 1)),
    NONE(new Vector(0, 0));

    private final Vector vector;

    private Direction(final Vector vector) {
        this.vector = vector;
    }

    //This is static because the method always works the same
    public static String toString(Direction dir) {
        switch (dir) {
            case NORTH:
                return "north";
            case SOUTH:
                return "south";
            case EAST:
                return "east";
            case WEST:
                return "west";
            case NORTHWEST:
                return "northwest";
            case SOUTHWEST:
                return "southwest";
            case NORTHEAST:
                return "northeast";
            case SOUTHEAST:
                return "southeast";
            case NONE:
                return "none";
        }
        return "";
    }

    /**
     * Returns a copy of the direction (non reference)
     * @return
     */
    public static Direction copy(Direction dir){
        switch (dir){
            case NORTH:
                return Direction.NORTH;
            case SOUTH:
                return Direction.SOUTH;
            case EAST:
                return Direction.EAST;
            case WEST:
                return Direction.WEST;
            case NORTHWEST:
                return Direction.NORTHWEST;
            case NORTHEAST:
                return Direction.NORTHEAST;
            case SOUTHWEST:
                return Direction.SOUTHWEST;
            case SOUTHEAST:
                return Direction.SOUTHEAST;
        }
        return Direction.NONE;
    }

    public Vector getVector() {
        return vector;
    }
}

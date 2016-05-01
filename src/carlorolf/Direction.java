package carlorolf;

/**
 * Direction describes a direction. Has method for getting a vector in the equivalent direction and a toString method.
 */
public enum Direction {
    // These enums doesn't need a javadoc since it's pretty clear what they stand for and it's described in the Directions javadoc.
    @SuppressWarnings("JavaDoc")NORTH(new Vector(0, -1)), @SuppressWarnings("JavaDoc")SOUTH(new Vector(0, 1)), @SuppressWarnings("JavaDoc")EAST(new Vector(1, 0)), @SuppressWarnings("JavaDoc")WEST(new Vector(-1, 0)),
    @SuppressWarnings("JavaDoc")NORTHWEST(new Vector(-1, -1)), @SuppressWarnings("JavaDoc")NORTHEAST(new Vector(1, -1)), @SuppressWarnings("JavaDoc")SOUTHWEST(new Vector(-1, 1)), @SuppressWarnings("JavaDoc")SOUTHEAST(new Vector(1, 1)),
    @SuppressWarnings("JavaDoc")NONE(new Vector(0, 0));

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

    public Vector getVector() {
        return vector;
    }
}

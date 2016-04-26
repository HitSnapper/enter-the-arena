package carlorolf;

enum Direction {
    NORTH(new Vector(0, -1)), SOUTH(new Vector(0, 1)), EAST(new Vector(1, 0)), WEST(new Vector(-1, 0)),
    NORTHWEST(new Vector(-1, -1)), NORTHEAST(new Vector(1, -1)), SOUTHWEST(new Vector(-1, 1)), SOUTHEAST(new Vector(1, 1)),
    NONE(new Vector(0, 0));

    private Vector vector;

    Direction(final Vector vector) {
        this.vector = vector;
    }

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

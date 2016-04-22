package carlorolf;

public enum Direction {
    NORTH, SOUTH, EAST, WEST, NORTHWEST, NORTHEAST, SOUTHWEST, SOUTHEAST, NONE;

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
}

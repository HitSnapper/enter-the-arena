package se.liu.ida.carro311rolsi701.tddd78.carlorolf;


import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Direction describes a direction. Has method for getting a vector in the equivalent direction and a toString method.
 */
public enum Direction {
    NONE("none"), NORTH("north"), WEST("west"), EAST("east"), SOUTH("south"), NORTHWEST("northwest"), NORTHEAST("northeast"), SOUTHEAST("southeast"), SOUTHWEST("southwest");
    private final Logger LOGGER = Logger.getLogger(ArenaObject.class.getName());
    private String name;
    private Vector vector;

    Direction(String name) {
        this.name = name;
        switch(name){
            case "none":
                vector = new Vector(0, 0);
                break;
            case "north":
                vector = new Vector(0, -1);
                break;
            case "east":
                vector = new Vector(1, 0);
                break;
            case "south":
                vector = new Vector(0, 1);
                break;
            case "west":
                vector = new Vector(-1, 0);
                break;
            case "northeast":
                vector = new Vector(1, -1);
                break;
            case "northwest":
                vector = new Vector(-1, -1);
                break;
            case "southeast":
                vector = new Vector(1, 1);
                break;
            case "southwest":
                vector = new Vector(-1, 1);
                break;
            default:
                LOGGER.log(Level.SEVERE, "Non-valid Direction name: {0}" + name);
        }
    }

    //This is static because the method always works the same
    public String getName() {
        return name;
    }

    public boolean isEqualTo(Direction direction){
        return direction.toString() == this.toString() && direction.getX() == this.getX() && direction.getY() == this.getY();
    }

    public Vector getVector() {
        return vector;
    }

    public double getX(){
        return vector.getX();
    }
    public double getY(){
        return vector.getY();
    }
    public Direction getCopy(){
        return values()[ordinal()];
    }
}

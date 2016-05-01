package carlorolf;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

/**
 * This class is used to load images from the recourse directory.
 */
public final class Images
{
    private static Image object_none = loadImage("object_none.png");
    private static Image object_north = loadImage("object_north.png");
    private static Image object_northeast = loadImage("object_northeast.png");
    private static Image object_northwest = loadImage("object_northwest.png");
    private static Image object_west = loadImage("object_west.png");
    private static Image object_east = loadImage("object_east.png");
    private static Image object_south = loadImage("object_south.png");
    private static Image object_southwest = loadImage("object_southwest.png");
    private static Image object_southeast = loadImage("object_southeast.png");

    private static Image enemy_none = loadImage("enemy_none.png");
    private static Image enemy_north = loadImage("enemy_north.png");
    private static Image enemy_northeast = loadImage("enemy_northeast.png");
    private static Image enemy_northwest = loadImage("enemy_northwest.png");
    private static Image enemy_west = loadImage("enemy_west.png");
    private static Image enemy_east = loadImage("enemy_east.png");
    private static Image enemy_south = loadImage("enemy_south.png");
    private static Image enemy_southwest = loadImage("enemy_southwest.png");
    private static Image enemy_southeast = loadImage("enemy_southeast.png");

    private static Image dragon_none = loadImage("dragon_none.png");
    private static Image dragon_north = loadImage("dragon_north.png");
    private static Image dragon_northeast = loadImage("dragon_northeast.png");
    private static Image dragon_northwest = loadImage("dragon_northwest.png");
    private static Image dragon_west = loadImage("dragon_west.png");
    private static Image dragon_east = loadImage("dragon_east.png");
    private static Image dragon_south = loadImage("dragon_south.png");
    private static Image dragon_southwest = loadImage("dragon_southwest.png");
    private static Image dragon_southeast = loadImage("dragon_southeast.png");

    private static Image stoneBrick = loadImage("stonebrick.png");
    private static Image blueBrick = loadImage("bluebrick.png");
    private static Image grass = loadImage("grass.png");
    private static Image sand = loadImage("sand.png");
    private static Image object_sad = loadImage("object_sad.png");
    private static Image stone = loadImage("stone.png");
    private static Image tree = loadImage("tree.png");
    private static Image leaves = loadImage("leaves.png");
    private static Image helmet = loadImage("helmet.png");
    private static Image blood = loadImage("blood.png");

    private Images() {}

    //This method always works the same, that's why it's static
    private static Image loadImage(String url) {
	try {
	    Image image;
	    URL u = ClassLoader.getSystemResource(url);
	    image = ImageIO.read(u);
	    return image;
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return null;
    }

    public static Image getImage(String name) {
	switch (name) {
	    case "stonebrick":
		return stoneBrick;
	    case "bluebrick":
		return blueBrick;
	    case "grass":
		return grass;
	    case "sand":
		return sand;
	    case "object_sad":
		return object_sad;
	    case "stone":
		return stone;
	    case "leaves":
		return leaves;
	    case "tree":
		return tree;
	    case "helmet":
		return helmet;
	    case "blood":
		return blood;

	    case "object_none":
		return object_none;
	    case "object_north":
		return object_north;
	    case "object_northwest":
		return object_northwest;
	    case "object_northeast":
		return object_northeast;
	    case "object_west":
		return object_west;
	    case "object_south":
		return object_south;
	    case "object_east":
		return object_east;
	    case "object_southeast":
		return object_southeast;
	    case "object_southwest":
		return object_southwest;

	    case "enemy_none":
		return enemy_none;
	    case "enemy_north":
		return enemy_north;
	    case "enemy_northwest":
		return enemy_northwest;
	    case "enemy_northeast":
		return enemy_northeast;
	    case "enemy_west":
		return enemy_west;
	    case "enemy_south":
		return enemy_south;
	    case "enemy_east":
		return enemy_east;
	    case "enemy_southeast":
		return enemy_southeast;
	    case "enemy_southwest":
		return enemy_southwest;

	    case "dragon_none":
		return dragon_none;
	    case "dragon_north":
		return dragon_north;
	    case "dragon_northwest":
		return dragon_northwest;
	    case "dragon_northeast":
		return dragon_northeast;
	    case "dragon_west":
		return dragon_west;
	    case "dragon_south":
		return dragon_south;
	    case "dragon_east":
		return dragon_east;
	    case "dragon_southeast":
		return dragon_southeast;
	    case "dragon_southwest":
		return dragon_southwest;

	    default:
		return null;
	}
    }
}

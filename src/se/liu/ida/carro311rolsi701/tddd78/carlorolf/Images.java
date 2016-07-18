package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is used to load images from the recourse directory.
 */
public final class Images {
    private static final Logger LOGGER = Logger.getLogger(Images.class.getName());
    /*
    These images are static so that the program doesn't need to initialize images during gameplay
    since it would take to much time to read the images from the resource directory each tick.
     */
    private static Image objectNone = loadImage("object_none.png");
    private static Image objectNorth = loadImage("object_north.png");
    private static Image objectNortheast = loadImage("object_northeast.png");
    private static Image objectNorthwest = loadImage("object_northwest.png");
    private static Image objectWest = loadImage("object_west.png");
    private static Image objectEast = loadImage("object_east.png");
    private static Image objectSouth = loadImage("object_south.png");
    private static Image objectSouthwest = loadImage("object_southwest.png");
    private static Image objectSoutheast = loadImage("object_southeast.png");

    private static Image enemyNone = loadImage("enemy_none.png");
    private static Image enemyNorth = loadImage("enemy_north.png");
    private static Image enemyNortheast = loadImage("enemy_northeast.png");
    private static Image enemyNorthwest = loadImage("enemy_northwest.png");
    private static Image enemyWest = loadImage("enemy_west.png");
    private static Image enemyEast = loadImage("enemy_east.png");
    private static Image enemySouth = loadImage("enemy_south.png");
    private static Image enemySouthwest = loadImage("enemy_southwest.png");
    private static Image enemySoutheast = loadImage("enemy_southeast.png");

    private static Image dustNone = loadImage("dust_none.png");
    private static Image dustNorth = loadImage("dust_north.png");
    private static Image dustNortheast = loadImage("dust_northeast.png");
    private static Image dustNorthwest = loadImage("dust_northwest.png");
    private static Image dustWest = loadImage("dust_west.png");
    private static Image dustEast = loadImage("dust_east.png");
    private static Image dustSouth = loadImage("dust_south.png");
    private static Image dustSouthwest = loadImage("dust_southwest.png");
    private static Image dustSoutheast = loadImage("dust_southeast.png");

    private static Image dragonNone = loadImage("dragon_none.png");
    private static Image dragonNorth = loadImage("dragon_north.png");
    private static Image dragonNortheast = loadImage("dragon_northeast.png");
    private static Image dragonNorthwest = loadImage("dragon_northwest.png");
    private static Image dragonWest = loadImage("dragon_west.png");
    private static Image dragonEast = loadImage("dragon_east.png");
    private static Image dragonSouth = loadImage("dragon_south.png");
    private static Image dragonSouthwest = loadImage("dragon_southwest.png");
    private static Image dragonSoutheast = loadImage("dragon_southeast.png");

    private static Image stoneBrick = loadImage("stonebrick.png");
    private static Image blueBrick = loadImage("bluebrick.png");
    private static Image grass = loadImage("grass.png");
    private static Image sand = loadImage("sand.png");
    private static Image objectSad = loadImage("object_sad.png");
    private static Image stone = loadImage("stone.png");
    private static Image tree = loadImage("tree.png");
    private static Image leaves = loadImage("leaves.png");
    private static Image helmet = loadImage("helmet.png");
    private static Image blood = loadImage("blood.png");
    private static Image dragonLayer = loadImage("dragon_layer.png");
    private static Image leavesDamaged0 = loadImage("leaves_damaged0.png");
    private static Image leavesDamaged1 = loadImage("leaves_damaged1.png");
    private static Image leavesDamaged2 = loadImage("leaves_damaged2.png");
    private static Image leavesDamaged3 = loadImage("leaves_damaged3.png");

    private Images() {
    }

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

    // Method wouldn't get much simpler if it was split up, it's pretty clear what it does.
    //This method always works the same, that's why it's static
    @SuppressWarnings({"OverlyComplexMethod", "OverlyLongMethod"})
    public static Image getImage(String name) {
        switch (name) {
            case "dragon_layer":
                return dragonLayer;
            case "stonebrick":
                return stoneBrick;
            case "bluebrick":
                return blueBrick;
            case "grass":
                return grass;
            case "sand":
                return sand;
            case "object_sad":
                return objectSad;
            case "stone":
                return stone;
            case "leaves":
                return leaves;
            case "leaves_damaged0":
                return leavesDamaged0;
            case "leaves_damaged1":
                return leavesDamaged1;
            case "leaves_damaged2":
                return leavesDamaged2;
            case "leaves_damaged3":
                return leavesDamaged3;
            case "tree":
                return tree;
            case "helmet":
                return helmet;
            case "blood":
                return blood;

            case "object_none":
                return objectNone;
            case "object_north":
                return objectNorth;
            case "object_northwest":
                return objectNorthwest;
            case "object_northeast":
                return objectNortheast;
            case "object_west":
                return objectWest;
            case "object_south":
                return objectSouth;
            case "object_east":
                return objectEast;
            case "object_southeast":
                return objectSoutheast;
            case "object_southwest":
                return objectSouthwest;

            case "enemy_none":
                return enemyNone;
            case "enemy_north":
                return enemyNorth;
            case "enemy_northwest":
                return enemyNorthwest;
            case "enemy_northeast":
                return enemyNortheast;
            case "enemy_west":
                return enemyWest;
            case "enemy_south":
                return enemySouth;
            case "enemy_east":
                return enemyEast;
            case "enemy_southeast":
                return enemySoutheast;
            case "enemy_southwest":
                return enemySouthwest;

            case "dust_none":
                return dustNone;
            case "dust_north":
                return dustNorth;
            case "dust_northwest":
                return dustNorthwest;
            case "dust_northeast":
                return dustNortheast;
            case "dust_west":
                return dustWest;
            case "dust_south":
                return dustSouth;
            case "dust_east":
                return dustEast;
            case "dust_southeast":
                return dustSoutheast;
            case "dust_southwest":
                return dustSouthwest;

            case "dragon_none":
                return dragonNone;
            case "dragon_north":
                return dragonNorth;
            case "dragon_northwest":
                return dragonNorthwest;
            case "dragon_northeast":
                return dragonNortheast;
            case "dragon_west":
                return dragonWest;
            case "dragon_south":
                return dragonSouth;
            case "dragon_east":
                return dragonEast;
            case "dragon_southeast":
                return dragonSoutheast;
            case "dragon_southwest":
                return dragonSouthwest;

            default:
                LOGGER.log(Level.SEVERE, "No image named {0}", name);
                return null;
        }
    }
}

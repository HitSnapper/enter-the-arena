package carlorolf.enemies;

import carlorolf.Arena;
import carlorolf.Armor;
import carlorolf.CollisionHandler;
import carlorolf.Direction;
import carlorolf.Images;
import carlorolf.VisibleObject;
import carlorolf.Weapon;

import java.awt.*;


/**
 * The Boss of the arena which spawns ones in a while to give the player some difficulties.
 */
public class DragonBoss extends Enemy {

    //These are static so it can be accessed in the super constructor
    final static int DAMAGE = 30;
    final static double ATTACKSPEED = 0.5;
    public DragonBoss(final double x, final double y, final CollisionHandler collisionHandler, final Arena arena) {
        super(x, y, 2, 2, 1, 100, 2, Images.getImage("dragon_none.png"), collisionHandler, arena);
        //Assigning a unique weapon for DragonBoss
        //noinspection AssignmentToSuperclassField
        weapon = new Weapon(x, y, DAMAGE, 1 * width / 6, ATTACKSPEED, this);
        //Assigning a unique armor for DragonBoss
        //noinspection AssignmentToSuperclassField
        armor = new Armor(100, this, arena, null);
        final double armorSizeX = width * 1.5;
        final double armorSizeY = height* 1.5;
        layers.add(new VisibleObject(x, y, armorSizeX, armorSizeY, Images.getImage("dragon_armor.png"), arena)
        {
            @Override public void update(final double deltaTime) {

            }
        });
    }

    @Override
    protected void updateImage() {
        image = Images.getImage("dragon_" + Direction.toString(movingDirection) + ".png");
    }

    @Override public void update(double deltaTime){
        super.update(deltaTime);
        for (VisibleObject layer : layers) {
            layer.setX(x);
            layer.setY(y);
        }
    }

    @Override public void draw(Graphics screen, Dimension tileSize){
        super.draw(screen, tileSize);
        for (VisibleObject layer : layers) {
            layer.draw(screen, tileSize);
        }
    }

    @Override public void death(){
        super.death();
        final int temp = 80;
        arena.getPlayer().addHealth(temp);
        arena.getPlayer().getArmor().repairArmor(temp);
    }
}

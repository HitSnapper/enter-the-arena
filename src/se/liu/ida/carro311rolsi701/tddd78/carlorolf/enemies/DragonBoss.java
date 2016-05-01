package se.liu.ida.carro311rolsi701.tddd78.carlorolf.enemies;

import se.liu.ida.carro311rolsi701.tddd78.carlorolf.Arena;
import se.liu.ida.carro311rolsi701.tddd78.carlorolf.Armor;
import se.liu.ida.carro311rolsi701.tddd78.carlorolf.CollisionHandler;
import se.liu.ida.carro311rolsi701.tddd78.carlorolf.Images;
import se.liu.ida.carro311rolsi701.tddd78.carlorolf.VisibleObject;
import se.liu.ida.carro311rolsi701.tddd78.carlorolf.Weapon;

import java.awt.*;

/**
 * The Boss of the arena which spawns ones in a while to give the player some difficulties.
 */
public class DragonBoss extends Enemy
{

    //These are static so it can be accessed in the super constructor
    final static int DAMAGE = 30;
    final static double ATTACKSPEED = 0.5;

    public DragonBoss(final double x, final double y, final CollisionHandler collisionHandler, final Arena arena) {
	super(x, y, 2, 2, 1, 100, 2, "dragon", collisionHandler, arena);
	//Assigning a unique weapon for DragonBoss
	//noinspection AssignmentToSuperclassField
	weapon = new Weapon(x, y, DAMAGE, 1 * width / 6, ATTACKSPEED, this);
	//Assigning a unique armor for DragonBoss
	//noinspection AssignmentToSuperclassField
	armor = new Armor(100, this, arena, null);
	final double armorSizeX = width * 1.5;
	final double armorSizeY = height * 1.5;
	layers.add(new VisibleObject(x, y, armorSizeX, armorSizeY, Images.getImage("dragon_layer"), arena)
	{
	    @Override public void update(final double deltaTime) {

	    }
	});
    }

    @Override public void update(double deltaTime) {
	super.update(deltaTime);
	for (VisibleObject layer : layers) {
	    layer.setX(x);
	    layer.setY(y);
	}
    }

    @Override public void draw(Graphics screen, Dimension tileSize, int screenWidth, int screenHeight) {
	super.draw(screen, tileSize, screenWidth, screenHeight);
	for (VisibleObject layer : layers) {
	    layer.draw(screen, tileSize, screenWidth, screenHeight);
	}
    }

    @Override public void death() {
	super.death();
	final int temp = 80;
	assert (arena.getPlayer() != null);
	arena.getPlayer().addHealth(temp);
	arena.getPlayer().getArmor().repairArmor(temp);
    }
}

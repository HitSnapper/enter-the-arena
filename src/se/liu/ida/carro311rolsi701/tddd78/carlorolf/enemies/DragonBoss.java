package se.liu.ida.carro311rolsi701.tddd78.carlorolf.enemies;

import se.liu.ida.carro311rolsi701.tddd78.carlorolf.*;
import se.liu.ida.carro311rolsi701.tddd78.carlorolf.friendlycharacters.Player;

import java.awt.*;

/**
 * The Boss of the arena which spawns ones in a while to give the player some difficulties.
 */
public class DragonBoss extends Enemy {

    //These are static so it can be accessed in the super constructor
    final static int DAMAGE = 30;
    final static double ATTACKSPEED = 0.5;
    final static double SIZE = 2;

    public DragonBoss(final double x, final double y, final CollisionHandler collisionHandler, final Arena arena) {
        super(x, y, SIZE, 1, 100, 2, new Weapon(DAMAGE, 1 * SIZE / 6, ATTACKSPEED), "dragon", collisionHandler, arena);
        //Assigning a unique armor for DragonBoss
        //noinspection AssignmentToSuperclassField
        armor = new Armor(100, this, arena, null);
        final double armorSizeX = width * 1.5;
        final double armorSizeY = height * 1.5;
        layers.add(new Layer(x, y, armorSizeX, armorSizeY, Images.getImage("dragon_layer"), arena));
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        for (VisibleObject layer : layers) {
            layer.setX(getX());
            layer.setY(getY());
        }
    }

    @Override
    public void draw(Graphics2D screen, Vector target, Dimension tileSize, int screenWidth, int screenHeight) {
        super.draw(screen, target, tileSize, screenWidth, screenHeight);
        for (VisibleObject layer : layers) {
            layer.draw(screen, target, tileSize, screenWidth, screenHeight);
        }
    }

    @Override
    public void death() {
        super.death();
        final int temp = 80;
        assert (arena.getPlayer(0) != null);
        for (Player player : arena.getAlivePlayers()) {
            player.heal(temp);
            player.getArmor().repairArmor(temp);
        }
        for (Player player : arena.getPlayers()) {
            player.revive();
        }
    }
}

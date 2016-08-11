package se.liu.ida.carro311rolsi701.tddd78.carlorolf.enemies;

import se.liu.ida.carro311rolsi701.tddd78.carlorolf.*;
import se.liu.ida.carro311rolsi701.tddd78.carlorolf.friendlycharacters.Player;

/**
 * The standard enemy for the player with the only goal to kill him.
 */
public class Dust extends Enemy {

    //These are static so it can be accessed in the super constructor
    private final static int MOVEMENTSPEED = 3;
    private final static int ATTACKSPEED = 1;
    private final static int HEALTH = 50;

    public Dust(final double x, final double y, final CollisionHandler collisionHandler, final Arena arena) {
        super(x, y, 0.8, MOVEMENTSPEED, HEALTH, ATTACKSPEED, new Weapon(700, 2 * 1.5 / 6, 0.5), "enemy", collisionHandler, arena);
        final int armorToughness = 20;
        armor = new Armor(armorToughness, this, arena, Images.getImage("helmet"));
    }

    @Override
    public void death() {
        super.death();
        final int repair = 15;
        final int heal = 7;
        for (Player player : arena.getAlivePlayers()) {
            player.getArmor().repairArmor(repair);
            player.heal(heal);
        }
    }
}
